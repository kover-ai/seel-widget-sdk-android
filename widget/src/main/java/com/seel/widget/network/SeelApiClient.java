package com.seel.widget.network;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seel.widget.core.Constants;
import com.seel.widget.core.SeelClient;
import com.seel.widget.models.QuotesRequest;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.models.EventsRequest;
import com.seel.widget.models.EventsResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Seel API Client with improved error handling and retry mechanism
 * Thread-safe implementation with proper resource management
 */
public class SeelApiClient {
    
    private static final String TAG = Constants.TAG_API_CLIENT;
    private static volatile SeelApiClient instance;
    private static final ReentrantLock lock = new ReentrantLock();
    
    private SeelApiService apiService;
    private SeelApiService logApiService;
    private Context context;
    private OkHttpClient okHttpClient;
    private boolean isInitialized = false;
    
    private SeelApiClient(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context.getApplicationContext();
        initializeRetrofit();
    }
    
    /**
     * Get singleton instance with double-checked locking
     * @param context Application context
     * @return SeelApiClient instance
     */
    public static SeelApiClient getInstance(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new SeelApiClient(context);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
    
    /**
     * Initialize Retrofit with improved error handling
     */
    private void initializeRetrofit() {
        try {
            // Create logging interceptor with conditional logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
                if (SeelClient.getInstance().getEnvironment() == com.seel.widget.core.SeelEnvironment.DEVELOPMENT) {
                    Log.d(TAG, message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            // Create authentication interceptor with retry logic
            Interceptor authInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    
                    // Add authentication headers
                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .addHeader(Constants.CONTENT_TYPE_HEADER, Constants.JSON_CONTENT_TYPE)
                            .addHeader(Constants.ACCEPT_HEADER, Constants.JSON_CONTENT_TYPE)
                            .addHeader(Constants.API_VERSION_HEADER, Constants.VERSION);
                    
                    // Add API Key with validation
                    String apiKey = SeelClient.getInstance().getApiKey();
                    if (apiKey != null && !apiKey.trim().isEmpty()) {
                        requestBuilder.addHeader(Constants.API_KEY_HEADER, apiKey);
                    } else {
                        Log.w(TAG, "API key is missing or empty");
                    }
                    
                    Request newRequest = requestBuilder.build();
                    Log.d(TAG, "Making request to: " + newRequest.url());
                    
                    return chain.proceed(newRequest);
                }
            };
            
            // Create retry interceptor
            Interceptor retryInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = null;
                    IOException exception = null;
                    
                    // Retry up to MAX_RETRY_ATTEMPTS times
                    for (int i = 0; i < Constants.MAX_RETRY_ATTEMPTS; i++) {
                        try {
                            response = chain.proceed(request);
                            if (response.isSuccessful()) {
                                return response;
                            }
                        } catch (IOException e) {
                            exception = e;
                            if (i == Constants.MAX_RETRY_ATTEMPTS - 1) throw e; // Last attempt
                        }
                        
                        if (response != null && !response.isSuccessful()) {
                            Log.w(TAG, "Request failed with code: " + response.code() + ", retry: " + (i + 1));
                        }
                    }
                    
                    if (exception != null) {
                        throw exception;
                    }
                    return response;
                }
            };
            
            // Create OkHttpClient with improved configuration
            this.okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(retryInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(Constants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            
            // Create Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SeelClient.getInstance().getBaseURL() + "/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Retrofit logRetrofit = new Retrofit.Builder()
                    .baseUrl(SeelClient.getInstance().getLogBaseURL() + "/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            
            apiService = retrofit.create(SeelApiService.class);
            logApiService = logRetrofit.create(SeelApiService.class);
            isInitialized = true;
            
            Log.i(TAG, "Retrofit initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize Retrofit", e);
            isInitialized = false;
        }
    }
    
    /**
     * Get quote with improved error handling
     * @param quote Quote request
     * @param callback API callback
     */
    public void getQuotes(@NonNull QuotesRequest quote, @NonNull SeelApiCallback<QuotesResponse> callback) {
        if (!isInitialized) {
            Log.e(TAG, Constants.ERROR_CLIENT_NOT_INITIALIZED);
            callback.onError(NetworkError.UNKNOWN, Constants.ERROR_CLIENT_NOT_INITIALIZED);
            return;
        }
        
        if (!SeelClient.getInstance().isConfigured()) {
            Log.e(TAG, Constants.ERROR_NOT_CONFIGURED);
            callback.onError(NetworkError.UNKNOWN, Constants.ERROR_NOT_CONFIGURED);
            return;
        }
        
        if (quote == null) {
            Log.e(TAG, Constants.ERROR_REQUEST_NULL);
            callback.onError(NetworkError.UNKNOWN, Constants.ERROR_REQUEST_NULL);
            return;
        }
        
        Log.d(TAG, "Getting quotes for cart: " + quote.getCartID());
        
        Call<QuotesResponse> call = apiService.getQuotes(quote);
        call.enqueue(new Callback<QuotesResponse>() {
            @Override
            public void onResponse(Call<QuotesResponse> call, retrofit2.Response<QuotesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Quote request successful");
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Server error: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            errorMsg += " - " + response.errorBody().string();
                        } catch (IOException e) {
                            Log.w(TAG, "Failed to read error body", e);
                        }
                    }
                    Log.e(TAG, errorMsg);
                    callback.onError(NetworkError.SERVER_ERROR, errorMsg);
                }
            }
            
            @Override
            public void onFailure(Call<QuotesResponse> call, Throwable t) {
                String errorMsg = t != null ? t.getMessage() : "Unknown error";
                Log.e(TAG, "Quote request failed: " + errorMsg, t);
                callback.onError(NetworkError.NETWORK_ERROR, errorMsg);
            }
        });
    }
    
    /**
     * Create event with improved error handling
     * @param eventsRequest Event request
     * @param callback API callback
     */
    public void createEvents(@NonNull EventsRequest eventsRequest, @NonNull SeelApiCallback<EventsResponse> callback) {
        if (!isInitialized) {
            Log.e(TAG, Constants.ERROR_CLIENT_NOT_INITIALIZED);
            callback.onError(NetworkError.UNKNOWN, Constants.ERROR_CLIENT_NOT_INITIALIZED);
            return;
        }
        
        if (!SeelClient.getInstance().isConfigured()) {
            Log.e(TAG, Constants.ERROR_NOT_CONFIGURED);
            callback.onError(NetworkError.UNKNOWN, Constants.ERROR_NOT_CONFIGURED);
            return;
        }
        
        if (eventsRequest == null) {
            Log.e(TAG, Constants.ERROR_REQUEST_NULL);
            callback.onError(NetworkError.UNKNOWN, Constants.ERROR_REQUEST_NULL);
            return;
        }
        
        Log.d(TAG, "Creating events");
        
        Call<EventsResponse> call = logApiService.createEvents(eventsRequest);
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, retrofit2.Response<EventsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Event creation successful");
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Server error: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            errorMsg += " - " + response.errorBody().string();
                        } catch (IOException e) {
                            Log.w(TAG, "Failed to read error body", e);
                        }
                    }
                    Log.e(TAG, errorMsg);
                    callback.onError(NetworkError.SERVER_ERROR, errorMsg);
                }
            }
            
            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                String errorMsg = t != null ? t.getMessage() : "Unknown error";
                Log.e(TAG, "Event creation failed: " + errorMsg, t);
                callback.onError(NetworkError.NETWORK_ERROR, errorMsg);
            }
        });
    }
    
    /**
     * Check if client is initialized
     * @return true if initialized
     */
    public boolean isInitialized() {
        return isInitialized;
    }
    
    /**
     * Reinitialize client (useful when configuration changes)
     */
    public void reinitialize() {
        Log.i(TAG, "Reinitializing API client");
        initializeRetrofit();
    }
    
    /**
     * API callback interface with improved error handling
     * @param <T> Response type
     */
    public interface SeelApiCallback<T> {
        /**
         * Called when request is successful
         * @param response Response object
         */
        void onSuccess(T response);
        
        /**
         * Called when request fails
         * @param error Network error type
         * @param message Error message
         */
        void onError(NetworkError error, String message);
    }
}





