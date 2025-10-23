package com.seel.widget.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Event request model
 */
public class EventsRequest {

    /**
     * Session ID
     */
    @SerializedName("session_id")
    private String sessionID;

    /**
     * Event created timestamp in milliseconds
     */
    @SerializedName("event_ts")
    private String eventTs;

    /**
     * Customer ID
     */
    @SerializedName("customer_id")
    private String customerID;

    /**
     * Device ID
     */
    @SerializedName("device_id")
    private String deviceID;

    /**
     * Browser IP address
     */
    @SerializedName("client_ip")
    private String clientIp;

    /**
     * Event source
     */
    @SerializedName("event_source")
    private String eventSource;

    /**
     * Event type
     * product_page_enter / product_page_exit / product_share / favorite_add / favorite_remove / cart_add / cart_remove / ra_checked / ra_unchecked / checkout_begin / checkout_complete
     */
    @SerializedName("event_type")
    private String eventType;

    /**
     * Event information object as key-value pairs
     * Each event_type has its own unique schema. For specific details, please refer to the custom pixel guide.
     */
    @SerializedName("event_info")
    private Map<String, Object> eventInfo;

    public EventsRequest(String sessionID, String customerID, String eventSource, String eventType) {
        this.sessionID = sessionID;
        this.customerID = customerID;
        this.eventSource = eventSource;
        this.eventType = eventType;
    }

    public EventsRequest() {
        this.sessionID = null;
        this.customerID = null;
        this.eventSource = null;
        this.eventType = null;
    }

    // Getters and setters
    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getEventTs() {
        return eventTs;
    }

    public void setEventTs(String eventTs) {
        this.eventTs = eventTs;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, Object> getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(Map<String, Object> eventInfo) {
        this.eventInfo = eventInfo;
    }

}
