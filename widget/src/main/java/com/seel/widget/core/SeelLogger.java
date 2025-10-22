package com.seel.widget.core;

import android.util.Log;

/**
 * Centralized logging utility for Seel Widget SDK
 * Provides structured logging with different levels and categories
 */
public class SeelLogger {
    
    private static final String LOG_PREFIX = "[SeelWidget] ";
    private static boolean isDebugEnabled = true;
    private static boolean isVerboseEnabled = false;
    
    /**
     * Enable or disable debug logging
     * @param enabled true to enable debug logging
     */
    public static void setDebugEnabled(boolean enabled) {
        isDebugEnabled = enabled;
    }
    
    /**
     * Enable or disable verbose logging
     * @param enabled true to enable verbose logging
     */
    public static void setVerboseEnabled(boolean enabled) {
        isVerboseEnabled = enabled;
    }
    
    /**
     * Log debug message
     * @param tag Log tag
     * @param message Log message
     */
    public static void debug(String tag, String message) {
        if (isDebugEnabled) {
            Log.d(LOG_PREFIX + tag, message);
        }
    }
    
    /**
     * Log debug message with format
     * @param tag Log tag
     * @param format Message format
     * @param args Format arguments
     */
    public static void debug(String tag, String format, Object... args) {
        if (isDebugEnabled) {
            Log.d(LOG_PREFIX + tag, String.format(format, args));
        }
    }
    
    /**
     * Log info message
     * @param tag Log tag
     * @param message Log message
     */
    public static void info(String tag, String message) {
        Log.i(LOG_PREFIX + tag, message);
    }
    
    /**
     * Log info message with format
     * @param tag Log tag
     * @param format Message format
     * @param args Format arguments
     */
    public static void info(String tag, String format, Object... args) {
        Log.i(LOG_PREFIX + tag, String.format(format, args));
    }
    
    /**
     * Log warning message
     * @param tag Log tag
     * @param message Log message
     */
    public static void warning(String tag, String message) {
        Log.w(LOG_PREFIX + tag, message);
    }
    
    /**
     * Log warning message with format
     * @param tag Log tag
     * @param format Message format
     * @param args Format arguments
     */
    public static void warning(String tag, String format, Object... args) {
        Log.w(LOG_PREFIX + tag, String.format(format, args));
    }
    
    /**
     * Log error message
     * @param tag Log tag
     * @param message Log message
     */
    public static void error(String tag, String message) {
        Log.e(LOG_PREFIX + tag, message);
    }
    
    /**
     * Log error message with format
     * @param tag Log tag
     * @param format Message format
     * @param args Format arguments
     */
    public static void error(String tag, String format, Object... args) {
        Log.e(LOG_PREFIX + tag, String.format(format, args));
    }
    
    /**
     * Log error message with exception
     * @param tag Log tag
     * @param message Log message
     * @param throwable Exception
     */
    public static void error(String tag, String message, Throwable throwable) {
        Log.e(LOG_PREFIX + tag, message, throwable);
    }
    
    /**
     * Log verbose message
     * @param tag Log tag
     * @param message Log message
     */
    public static void verbose(String tag, String message) {
        if (isVerboseEnabled) {
            Log.v(LOG_PREFIX + tag, message);
        }
    }
    
    /**
     * Log verbose message with format
     * @param tag Log tag
     * @param format Message format
     * @param args Format arguments
     */
    public static void verbose(String tag, String format, Object... args) {
        if (isVerboseEnabled) {
            Log.v(LOG_PREFIX + tag, String.format(format, args));
        }
    }
    
    /**
     * Log API request
     * @param method HTTP method
     * @param url Request URL
     * @param headers Request headers
     */
    public static void logApiRequest(String method, String url, String headers) {
        if (isDebugEnabled) {
            verbose(Constants.TAG_API_CLIENT, "API Request: %s %s", method, url);
            if (headers != null && !headers.isEmpty()) {
                verbose(Constants.TAG_API_CLIENT, "Headers: %s", headers);
            }
        }
    }
    
    /**
     * Log API response
     * @param statusCode Response status code
     * @param responseBody Response body
     */
    public static void logApiResponse(int statusCode, String responseBody) {
        if (isDebugEnabled) {
            if (statusCode >= 200 && statusCode < 300) {
                debug(Constants.TAG_API_CLIENT, "API Response: %d", statusCode);
            } else {
                warning(Constants.TAG_API_CLIENT, "API Error: %d", statusCode);
            }
            if (responseBody != null && !responseBody.isEmpty()) {
                verbose(Constants.TAG_API_CLIENT, "Response Body: %s", responseBody);
            }
        }
    }
    
    /**
     * Log performance metrics
     * @param operation Operation name
     * @param duration Duration in milliseconds
     */
    public static void logPerformance(String operation, long duration) {
        if (isDebugEnabled) {
            debug(Constants.TAG_SDK, "Performance: %s took %dms", operation, duration);
        }
    }
}
