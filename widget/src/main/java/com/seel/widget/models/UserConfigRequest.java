package com.seel.widget.models;

import com.google.gson.annotations.SerializedName;

public class UserConfigRequest {

    @SerializedName("merchant_id")
    private String merchantID;

    @SerializedName("opted_out")
    private boolean optedOut;

    public UserConfigRequest(String merchantID, boolean optedOut) {
        this.merchantID = merchantID;
        this.optedOut = optedOut;
    }

    public String getMerchantID() { return merchantID; }
    public void setMerchantID(String merchantID) { this.merchantID = merchantID; }

    public boolean isOptedOut() { return optedOut; }
    public void setOptedOut(boolean optedOut) { this.optedOut = optedOut; }
}
