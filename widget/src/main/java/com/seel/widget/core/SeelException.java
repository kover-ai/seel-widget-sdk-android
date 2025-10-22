package com.seel.widget.core;

/**
 * Custom exception class for Seel Widget SDK
 * Provides structured error handling with error codes and messages
 */
public class SeelException extends Exception {
    
    private final SeelErrorCode errorCode;
    private final String userMessage;
    
    /**
     * Constructor with error code and message
     * @param errorCode Error code
     * @param message Error message
     */
    public SeelException(SeelErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = message;
    }
    
    /**
     * Constructor with error code, message and cause
     * @param errorCode Error code
     * @param message Error message
     * @param cause Original exception
     */
    public SeelException(SeelErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.userMessage = message;
    }
    
    /**
     * Get error code
     * @return Error code
     */
    public SeelErrorCode getErrorCode() {
        return errorCode;
    }
    
    /**
     * Get user-friendly error message
     * @return User message
     */
    public String getUserMessage() {
        return userMessage;
    }
    
    /**
     * Check if error is recoverable
     * @return true if error is recoverable
     */
    public boolean isRecoverable() {
        return errorCode.isRecoverable();
    }
    
    /**
     * Get error severity
     * @return Error severity
     */
    public SeelErrorSeverity getSeverity() {
        return errorCode.getSeverity();
    }
    
    @Override
    public String toString() {
        return "SeelException{" +
                "errorCode=" + errorCode +
                ", message='" + userMessage + '\'' +
                '}';
    }
}
