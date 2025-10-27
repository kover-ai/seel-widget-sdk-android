package com.seel.widget;

import android.content.Context;
import java.util.UUID;

import com.seel.widget.core.SeelClient;
import com.seel.widget.core.SeelEnvironment;
import com.seel.widget.models.EventsRequest;
import com.seel.widget.models.EventsResponse;
import com.seel.widget.network.NetworkError;
import com.seel.widget.network.SeelApiClient;

/**
 * SeelWidget SDK Main Class
 * Provides unified SDK configuration and API call interface
 */
public class SeelWidgetSDK {
    
    private static SeelWidgetSDK instance;
    private SeelClient seelClient;
    
    private SeelWidgetSDK() {
        seelClient = SeelClient.getInstance();
    }
    
    /**
     * Get singleton instance
     */
    public static SeelWidgetSDK getInstance() {
        if (instance == null) {
            synchronized (SeelWidgetSDK.class) {
                if (instance == null) {
                    instance = new SeelWidgetSDK();
                }
            }
        }
        return instance;
    }
    
    /**
     * Configure SDK
     * @param context Application context
     * @param apiKey API key
     * @param environment Environment (optional, defaults to production)
     */
    public void configure(Context context, String apiKey, SeelEnvironment environment) {
        seelClient.configure(context, apiKey, environment, null);
    }
    
    /**
     * Configure SDK (using default production environment)
     * @param context Application context
     * @param apiKey API key
     */
    public void configure(Context context, String apiKey) {
        seelClient.configure(context, apiKey);
    }
    
    /**
     * Get current API Key
     */
    public String getApiKey() {
        return seelClient.getApiKey();
    }
    
    /**
     * Get current environment
     */
    public SeelEnvironment getEnvironment() {
        return seelClient.getEnvironment();
    }
    
    /**
     * Check if configured
     */
    public boolean isConfigured() {
        return seelClient.isConfigured();
    }
    
    /**
     * Send event
     * @param event Event request
     * @param callback Callback interface
     */
    public void createEvents(EventsRequest event, SeelApiClient.SeelApiCallback<EventsResponse> callback) {
        if (!isConfigured()) {
            callback.onError(NetworkError.CONFIGURATION_ERROR, "SDK not configured");
            return;
        }
        
        // Use the context from SeelClient instead of passing null
        Context context = seelClient.getContext();
        if (context == null) {
            callback.onError(NetworkError.CONFIGURATION_ERROR, "Context not available");
            return;
        }

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        event.setEventID(uuidString);
        SeelApiClient.getInstance(context).createEvents(event, callback);
    }
}
