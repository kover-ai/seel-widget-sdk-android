package com.seel.widget.core;

/**
 * Error codes for Seel Widget SDK
 * Provides structured error categorization
 */
public enum SeelErrorCode {
    
    // Configuration errors
    CONFIGURATION_MISSING("CONFIG_001", "SDK configuration is missing", false, SeelErrorSeverity.HIGH),
    API_KEY_INVALID("CONFIG_002", "Invalid API key", false, SeelErrorSeverity.HIGH),
    CONTEXT_NULL("CONFIG_003", "Application context is null", false, SeelErrorSeverity.HIGH),
    
    // Network errors
    NETWORK_UNAVAILABLE("NET_001", "Network is unavailable", true, SeelErrorSeverity.MEDIUM),
    REQUEST_TIMEOUT("NET_002", "Request timeout", true, SeelErrorSeverity.MEDIUM),
    SERVER_ERROR("NET_003", "Server error", true, SeelErrorSeverity.MEDIUM),
    INVALID_RESPONSE("NET_004", "Invalid server response", false, SeelErrorSeverity.MEDIUM),
    
    // API errors
    API_CLIENT_NOT_INITIALIZED("API_001", "API client not initialized", false, SeelErrorSeverity.HIGH),
    REQUEST_VALIDATION_FAILED("API_002", "Request validation failed", false, SeelErrorSeverity.MEDIUM),
    QUOTE_REQUEST_FAILED("API_003", "Quote request failed", true, SeelErrorSeverity.MEDIUM),
    EVENT_TRACKING_FAILED("API_004", "Event tracking failed", false, SeelErrorSeverity.LOW),
    
    // UI errors
    VIEW_INITIALIZATION_FAILED("UI_001", "View initialization failed", false, SeelErrorSeverity.MEDIUM),
    INVALID_QUOTE_RESPONSE("UI_002", "Invalid quote response", false, SeelErrorSeverity.MEDIUM),
    
    // Unknown errors
    UNKNOWN_ERROR("UNKNOWN_001", "Unknown error occurred", false, SeelErrorSeverity.HIGH);
    
    private final String code;
    private final String message;
    private final boolean recoverable;
    private final SeelErrorSeverity severity;
    
    SeelErrorCode(String code, String message, boolean recoverable, SeelErrorSeverity severity) {
        this.code = code;
        this.message = message;
        this.recoverable = recoverable;
        this.severity = severity;
    }
    
    /**
     * Get error code
     * @return Error code string
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Get default error message
     * @return Error message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Check if error is recoverable
     * @return true if recoverable
     */
    public boolean isRecoverable() {
        return recoverable;
    }
    
    /**
     * Get error severity
     * @return Error severity
     */
    public SeelErrorSeverity getSeverity() {
        return severity;
    }
    
    @Override
    public String toString() {
        return code + ": " + message;
    }
}
