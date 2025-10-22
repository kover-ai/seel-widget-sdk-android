package com.seel.widget.models;

/**
 * Quote status enumeration
 */
public enum QuoteStatus {
    ACCEPTED("accepted"),
    REJECTED("rejected");
    
    private final String value;
    
    QuoteStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}

