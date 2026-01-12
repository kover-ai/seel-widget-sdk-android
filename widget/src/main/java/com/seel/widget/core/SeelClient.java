package com.seel.widget.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.concurrent.locks.ReentrantLock;

/**
 * SeelClient Singleton Class
 * Responsible for managing API configuration and network requests
 * Thread-safe implementation with proper error handling
 */
public class SeelClient {
    
    private static final String TAG = Constants.TAG_CLIENT;
    
    private static volatile SeelClient instance;
    private static final ReentrantLock lock = new ReentrantLock();
    
    private String apiKey;
    private SeelEnvironment environment;
    private String baseURL;
    private Context context;
    private boolean isConfigured = false;
    
    private SeelClient() {
        this.environment = SeelEnvironment.PRODUCTION;
    }
    
    /**
     * Get singleton instance with double-checked locking
     */
    public static SeelClient getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new SeelClient();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
    
    /**
     * Configure SeelClient with validation
     * @param context Application context
     * @param apiKey API key
     * @param environment Environment (optional, defaults to production)
     * @param baseURL Custom base URL (optional)
     * @throws IllegalArgumentException if context or apiKey is null/empty
     */
    public void configure(@NonNull Context context, @NonNull String apiKey, 
                         @Nullable SeelEnvironment environment, @Nullable String baseURL) {
        if (context == null) {
            throw new IllegalArgumentException(Constants.ERROR_CONTEXT_NULL);
        }
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_API_KEY_NULL);
        }
        this.context = context.getApplicationContext();
        this.apiKey = apiKey.trim();
        this.environment = environment != null ? environment : SeelEnvironment.PRODUCTION;
        this.baseURL = baseURL;
        this.isConfigured = true;
        
        SeelLogger.info(TAG, "SeelClient configured for environment: %s", this.environment);
        
        // Save configuration to SharedPreferences
        saveConfiguration();
    }
    
    /**
     * Configure SeelClient (using default environment)
     * @param context Application context
     * @param apiKey API key
     * @throws IllegalArgumentException if context or apiKey is null/empty
     */
    public void configure(@NonNull Context context, @NonNull String apiKey) {
        configure(context, apiKey, SeelEnvironment.PRODUCTION, null);
    }
    
    /**
     * Get current API Key
     * @return API key or null if not configured
     */
    @Nullable
    public String getApiKey() {
        return apiKey;
    }
    
    /**
     * Get current environment
     * @return current environment
     */
    @NonNull
    public SeelEnvironment getEnvironment() {
        return environment;
    }
    
    /**
     * Get current context
     * @return current context or null if not configured
     */
    @Nullable
    public Context getContext() {
        return context;
    }
    
    /**
     * Get base URL with validation
     * @return base URL for API requests
     */
    @NonNull
    public String getBaseURL() {
        if (baseURL != null && !baseURL.trim().isEmpty()) {
            return baseURL.trim();
        }
        
        switch (environment) {
            case DEVELOPMENT:
                return "https://api-test.seel.com";
            case PRODUCTION:
            default:
                return "https://api.seel.com";
        }
    }
    
    /**
     * Get log base URL for events
     * @return log domain per environment
     */
    @NonNull
    public String getLogBaseURL() {
        switch (environment) {
            case DEVELOPMENT:
                return "https://log-test.seel.com";
            case PRODUCTION:
            default:
                return "https://log.seel.com";
        }
    }
    
    /**
     * Check if configured properly
     * @return true if properly configured
     */
    public boolean isConfigured() {
        return isConfigured && apiKey != null && !apiKey.trim().isEmpty() && context != null;
    }
    
    /**
     * Build complete URL with validation
     * @param endpoint API endpoint
     * @return complete URL
     * @throws IllegalArgumentException if endpoint is null/empty
     */
    @NonNull
    public String buildURL(@NonNull String endpoint) {
        if (endpoint == null || endpoint.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_ENDPOINT_NULL);
        }
        
        String base = getBaseURL();
        String cleanEndpoint = endpoint.trim();
        if (cleanEndpoint.startsWith("/")) {
            cleanEndpoint = cleanEndpoint.substring(1);
        }
        
        String url = base + "/" + cleanEndpoint;
        Log.d(TAG, "Built URL: " + url);
        return url;
    }
    
    /**
     * Save configuration to SharedPreferences with error handling
     */
    private void saveConfiguration() {
        if (context == null) {
            Log.w(TAG, "Cannot save configuration: context is null");
            return;
        }
        
        try {
            SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constants.KEY_API_KEY, apiKey);
            editor.putString(Constants.KEY_ENVIRONMENT, environment.name());
            editor.putString(Constants.KEY_BASE_URL, baseURL);
            editor.apply();
            SeelLogger.debug(TAG, "Configuration saved successfully");
        } catch (Exception e) {
            SeelLogger.error(TAG, "Failed to save configuration", e);
        }
    }
    
    /**
     * Load configuration from SharedPreferences with error handling
     * @param context Application context
     */
    public void loadConfiguration(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException(Constants.ERROR_CONTEXT_NULL);
        }
        
        this.context = context.getApplicationContext();
        
        try {
            SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
            this.apiKey = prefs.getString(Constants.KEY_API_KEY, null);
            String envName = prefs.getString(Constants.KEY_ENVIRONMENT, SeelEnvironment.PRODUCTION.name());
            this.environment = SeelEnvironment.valueOf(envName);
            this.baseURL = prefs.getString(Constants.KEY_BASE_URL, null);
            this.isConfigured = isConfigured();
            
            SeelLogger.debug(TAG, "Configuration loaded: configured=%s", this.isConfigured);
        } catch (Exception e) {
            SeelLogger.error(TAG, "Failed to load configuration", e);
            this.isConfigured = false;
        }
    }
    
    /**
     * Reset configuration
     */
    public void reset() {
        this.apiKey = null;
        this.environment = SeelEnvironment.PRODUCTION;
        this.baseURL = null;
        this.isConfigured = false;
        SeelLogger.info(TAG, "Configuration reset");
    }
}

