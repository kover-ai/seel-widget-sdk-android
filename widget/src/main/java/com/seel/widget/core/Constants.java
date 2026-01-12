package com.seel.widget.core;

/**
 * Constants definition for Seel Widget SDK
 * Contains all SDK-related constants for configuration, networking, and UI
 */
public class Constants {
    
    // SDK Information
    public static final String PRODUCT_NAME = "seel-widget-sdk";
    public static final String VERSION = "2.6.0";
    public static final String USER_AGENT = "seel-widget-sdk-android/" + VERSION;
    
    // API Headers
    public static final String API_VERSION_HEADER = "X-Seel-Api-Version";
    public static final String API_KEY_HEADER = "X-Seel-Api-Key";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String ACCEPT_HEADER = "Accept";
    public static final String JSON_CONTENT_TYPE = "application/json";
    
    // Network Configuration
    public static final int REQUEST_TIMEOUT = 5000; // 5 seconds
    public static final int READ_TIMEOUT = 5000; // 5 seconds
    public static final int WRITE_TIMEOUT = 5000; // 5 seconds
    public static final int MAX_RETRY_ATTEMPTS = 3;

    // Local Cache
    public static final String SEEL_SHARED_PREFERENCES_NAME = "Seel.SharedPreferences";
    public static final String OPTED_VALUE_KEY = "Seel.OptedValueKey";
    public static final String OPTED_OPERATION_TIME_KEY = "Seel.OptedOperationTimeKey";
    public static final String CART_ID_KEY = "Seel.CartIdKey";
    
    // API Endpoints
    public static final String QUOTES_ENDPOINT = "quotes";
    public static final String EVENTS_ENDPOINT = "events";
    
    // Request Types
    public static final String WFP_TYPE = "seel-wfp";
    public static final String JJ_WFP_TYPE = "jj-wfp";
    
    // Device Information
    public static final String DEVICE_CATEGORY_WEB = "web";
    public static final String DEVICE_CATEGORY_MOBILE = "mobile";
    public static final String DEVICE_PLATFORM_ANDROID = "android";
    public static final String DEVICE_PLATFORM_IOS = "ios";
    
    
    // Colors (ARGB format)
    public static final int PRIMARY_COLOR = 0xFF2121C4;
    public static final int TRACK_COLOR_OFF = 0xFFE5E5EA;
    public static final int THUMB_COLOR = 0xFFFFFFFF;
    public static final int BACKGROUND_COLOR = 0xFFFFFFFF;
    
    // SharedPreferences Keys
    public static final String PREFS_NAME = "seel_widget_prefs";
    public static final String KEY_API_KEY = "api_key";
    public static final String KEY_ENVIRONMENT = "environment";
    public static final String KEY_BASE_URL = "base_url";
    
    // Error Messages
    public static final String ERROR_CONTEXT_NULL = "Context cannot be null";
    public static final String ERROR_API_KEY_NULL = "API key cannot be null or empty";
    public static final String ERROR_ENDPOINT_NULL = "Endpoint cannot be null or empty";
    public static final String ERROR_REQUEST_NULL = "Request cannot be null";
    public static final String ERROR_NOT_CONFIGURED = "SDK not configured";
    public static final String ERROR_CLIENT_NOT_INITIALIZED = "API client not initialized";
    
    // Log Tags
    public static final String TAG_CLIENT = "SeelClient";
    public static final String TAG_API_CLIENT = "SeelApiClient";
    public static final String TAG_WFP_VIEW = "SeelWFPView";
    public static final String TAG_SDK = "SeelWidgetSDK";
}

