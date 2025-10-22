package com.seel.widget.core;

/**
 * Environment enumeration
 */
public enum SeelEnvironment {
    DEVELOPMENT("development"),
    PRODUCTION("production");
    
    private final String value;
    
    SeelEnvironment(String value) {
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

