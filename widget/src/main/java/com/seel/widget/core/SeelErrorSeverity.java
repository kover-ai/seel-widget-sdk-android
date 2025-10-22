package com.seel.widget.core;

/**
 * Error severity levels for Seel Widget SDK
 * Helps prioritize error handling and logging
 */
public enum SeelErrorSeverity {
    
    /**
     * Low severity - minor issues that don't affect core functionality
     */
    LOW(1, "Low"),
    
    /**
     * Medium severity - issues that may affect some functionality
     */
    MEDIUM(2, "Medium"),
    
    /**
     * High severity - critical issues that affect core functionality
     */
    HIGH(3, "High"),
    
    /**
     * Critical severity - issues that prevent SDK from functioning
     */
    CRITICAL(4, "Critical");
    
    private final int level;
    private final String name;
    
    SeelErrorSeverity(int level, String name) {
        this.level = level;
        this.name = name;
    }
    
    /**
     * Get severity level
     * @return Severity level (1-4)
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Get severity name
     * @return Severity name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Check if this severity is higher than another
     * @param other Other severity level
     * @return true if this severity is higher
     */
    public boolean isHigherThan(SeelErrorSeverity other) {
        return this.level > other.level;
    }
    
    /**
     * Check if this severity is lower than another
     * @param other Other severity level
     * @return true if this severity is lower
     */
    public boolean isLowerThan(SeelErrorSeverity other) {
        return this.level < other.level;
    }
    
    @Override
    public String toString() {
        return name + " (" + level + ")";
    }
}
