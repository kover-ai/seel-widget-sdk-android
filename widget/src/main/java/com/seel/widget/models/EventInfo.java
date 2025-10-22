package com.seel.widget.models;


import com.google.gson.annotations.SerializedName;

public class EventInfo {

    /**
     * User email address
     */
    @SerializedName("user_email")
    private String userEmail;

    /**
     * User phone number
     */
    @SerializedName("user_phone_number")
    private String userPhoneNumber;

    /**
     * Shipping address information
     */
    @SerializedName("shipping_address")
    private EventShippingAddress shippingAddress;

    /**
     * Default constructor
     */
    public EventInfo() {
        this.userEmail = null;
        this.userPhoneNumber = null;
        this.shippingAddress = null;
    }

    /**
     * Parameterized constructor
     */
    public EventInfo(String userEmail, String userPhoneNumber, EventShippingAddress shippingAddress) {
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.shippingAddress = shippingAddress;
    }

    // Getters and setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public EventShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(EventShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public static class EventShippingAddress {

        @SerializedName("shipping_address_country")
        private String country;

        @SerializedName("shipping_address_state")
        private String state;

        @SerializedName("shipping_address_city")
        private String city;

        @SerializedName("shipping_address_zipcode")
        private String zipcode;

        /**
         * Default constructor
         */
        public EventShippingAddress() {
            this.country = null;
            this.state = null;
            this.city = null;
            this.zipcode = null;
        }

        /**
         * Parameterized constructor
         */
        public EventShippingAddress(String country, String state, String city, String zipcode) {
            this.country = country;
            this.state = state;
            this.city = city;
            this.zipcode = zipcode;
        }

        // Getters and setters
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
    }
}
