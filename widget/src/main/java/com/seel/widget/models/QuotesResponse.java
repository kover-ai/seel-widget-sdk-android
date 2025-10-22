package com.seel.widget.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * Quote response model
 */
public class QuotesResponse implements Serializable {
    
    @SerializedName("quote_id")
    private String quoteID;
    
    @SerializedName("cart_id")
    private String cartID;
    
    @SerializedName("merchant_id")
    private String merchantID;
    
    @SerializedName("session_id")
    private String sessionID;
    
    @SerializedName("device_id")
    private String deviceID;
    
    @SerializedName("device_category")
    private String deviceCategory;
    
    @SerializedName("device_platform")
    private String devicePlatform;
    
    @SerializedName("client_ip")
    private String clientIP;
    
    @SerializedName("type")
    private String type;
    
    @SerializedName("price")
    private Double price;
    
    @SerializedName("sales_tax")
    private Double salesTax;
    
    @SerializedName("currency")
    private String currency;
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("is_default_on")
    private Boolean isDefaultOn;
    
    @SerializedName("created_ts")
    private String createdTS;
    
    @SerializedName("line_items")
    private List<LineItem> lineItems;
    
    @SerializedName("eligible_items")
    private List<EligibleItem> eligibleItems;
    
    @SerializedName("shipping_address")
    private ShippingAddress shippingAddress;
    
    @SerializedName("customer")
    private Customer customer;
    
    @SerializedName("extra_info")
    private ExtraInfo extraInfo;


    // Getters and Setters
    public String getQuoteID() { return quoteID; }
    public void setQuoteID(String quoteID) { this.quoteID = quoteID; }
    
    public String getCartID() { return cartID; }
    public void setCartID(String cartID) { this.cartID = cartID; }
    
    public String getMerchantID() { return merchantID; }
    public void setMerchantID(String merchantID) { this.merchantID = merchantID; }
    
    public String getSessionID() { return sessionID; }
    public void setSessionID(String sessionID) { this.sessionID = sessionID; }
    
    public String getDeviceID() { return deviceID; }
    public void setDeviceID(String deviceID) { this.deviceID = deviceID; }
    
    public String getDeviceCategory() { return deviceCategory; }
    public void setDeviceCategory(String deviceCategory) { this.deviceCategory = deviceCategory; }
    
    public String getDevicePlatform() { return devicePlatform; }
    public void setDevicePlatform(String devicePlatform) { this.devicePlatform = devicePlatform; }
    
    public String getClientIP() { return clientIP; }
    public void setClientIP(String clientIP) { this.clientIP = clientIP; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Double getSalesTax() { return salesTax; }
    public void setSalesTax(Double salesTax) { this.salesTax = salesTax; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Boolean getIsDefaultOn() { return isDefaultOn; }
    public void setIsDefaultOn(Boolean isDefaultOn) { this.isDefaultOn = isDefaultOn; }
    
    public String getCreatedTS() { return createdTS; }
    public void setCreatedTS(String createdTS) { this.createdTS = createdTS; }
    
    public List<LineItem> getLineItems() { return lineItems; }
    public void setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems; }
    
    public List<EligibleItem> getEligibleItems() { return eligibleItems; }
    public void setEligibleItems(List<EligibleItem> eligibleItems) { this.eligibleItems = eligibleItems; }
    
    public ShippingAddress getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddress shippingAddress) { this.shippingAddress = shippingAddress; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public ExtraInfo getExtraInfo() { return extraInfo; }
    public void setExtraInfo(ExtraInfo extraInfo) { this.extraInfo = extraInfo; }
    
    /**
     * Inner class - Line item
     */
    public static class LineItem implements Serializable {
        @SerializedName("line_item_id")
        private String lineItemID;
        
        @SerializedName("product_id")
        private String productID;
        
        @SerializedName("product_title")
        private String productTitle;
        
        @SerializedName("product_attributes")
        private String productAttributes;
        
        @SerializedName("product_description")
        private String productDescription;
        
        @SerializedName("variant_id")
        private String variantID;
        
        @SerializedName("variant_title")
        private String variantTitle;
        
        @SerializedName("sku")
        private String sku;
        
        @SerializedName("seller_id")
        private String sellerID;
        
        @SerializedName("seller_name")
        private String sellerName;
        
        @SerializedName("brand_name")
        private String brandName;
        
        @SerializedName("quantity")
        private Integer quantity;
        
        @SerializedName("price")
        private Double price;
        
        @SerializedName("allocated_discounts")
        private Double allocatedDiscounts;
        
        @SerializedName("sales_tax")
        private Double salesTax;
        
        @SerializedName("final_price")
        private Double finalPrice;
        
        @SerializedName("retail_price")
        private Double retailPrice;
        
        @SerializedName("shipping_fee")
        private Double shippingFee;
        
        @SerializedName("currency")
        private String currency;
        
        @SerializedName("requires_shipping")
        private Boolean requiresShipping;
        
        @SerializedName("image_url")
        private String imageURL;
        
        @SerializedName("product_url")
        private String productURL;
        
        @SerializedName("is_final_sale")
        private Boolean isFinalSale;
        
        @SerializedName("shipping_origin")
        private ShippingOrigin shippingOrigin;
        
        @SerializedName("category_1")
        private String category1;
        
        @SerializedName("category_2")
        private String category2;
        
        @SerializedName("category_3")
        private String category3;
        
        @SerializedName("category_4")
        private String category4;
        
        @SerializedName("condition")
        private String condition;
        
        @SerializedName("extra_info")
        private String extraInfo;
        
        // Getters and Setters (simplified version, full getter/setter needed in actual project)
        public String getLineItemID() { return lineItemID; }
        public void setLineItemID(String lineItemID) { this.lineItemID = lineItemID; }
        
        public String getProductID() { return productID; }
        public void setProductID(String productID) { this.productID = productID; }
        
        public String getProductTitle() { return productTitle; }
        public void setProductTitle(String productTitle) { this.productTitle = productTitle; }
        
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public String getImageURL() { return imageURL; }
        public void setImageURL(String imageURL) { this.imageURL = imageURL; }
    }
    
    /**
     * Inner class - Eligible item
     */
    public static class EligibleItem implements Serializable {
        @SerializedName("line_item_id")
        private String lineItemID;
        
        @SerializedName("product_id")
        private String productID;
        
        @SerializedName("variant_id")
        private String variantID;
        
        @SerializedName("coverages")
        private List<Coverage> coverages;
        
        // Getters and Setters
        public String getLineItemID() { return lineItemID; }
        public void setLineItemID(String lineItemID) { this.lineItemID = lineItemID; }
        
        public String getProductID() { return productID; }
        public void setProductID(String productID) { this.productID = productID; }
        
        public String getVariantID() { return variantID; }
        public void setVariantID(String variantID) { this.variantID = variantID; }
        
        public List<Coverage> getCoverages() { return coverages; }
        public void setCoverages(List<Coverage> coverages) { this.coverages = coverages; }
    }
    
    /**
     * Inner class - Coverage
     */
    public static class Coverage implements Serializable {
        @SerializedName("coverage_type")
        private String coverageType;
        
        @SerializedName("description")
        private String description;
        
        // Getters and Setters
        public String getCoverageType() { return coverageType; }
        public void setCoverageType(String coverageType) { this.coverageType = coverageType; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    /**
     * Inner class - Shipping address
     */
    public static class ShippingAddress implements Serializable {
        @SerializedName("address_1")
        private String address1;
        
        @SerializedName("address_2")
        private String address2;
        
        @SerializedName("city")
        private String city;
        
        @SerializedName("state")
        private String state;
        
        @SerializedName("zipcode")
        private String zipcode;
        
        @SerializedName("country")
        private String country;
        
        // Getters and Setters
        public String getAddress1() { return address1; }
        public void setAddress1(String address1) { this.address1 = address1; }
        
        public String getAddress2() { return address2; }
        public void setAddress2(String address2) { this.address2 = address2; }
        
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        
        public String getZipcode() { return zipcode; }
        public void setZipcode(String zipcode) { this.zipcode = zipcode; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }
    
    /**
     * Inner class - Customer information
     */
    public static class Customer implements Serializable {
        @SerializedName("customer_id")
        private String customerID;
        
        @SerializedName("first_name")
        private String firstName;
        
        @SerializedName("last_name")
        private String lastName;
        
        @SerializedName("email")
        private String email;
        
        @SerializedName("phone")
        private String phone;
        
        @SerializedName("extra_info")
        private String extraInfo;
        
        // Getters and Setters
        public String getCustomerID() { return customerID; }
        public void setCustomerID(String customerID) { this.customerID = customerID; }
        
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        
        public String getExtraInfo() { return extraInfo; }
        public void setExtraInfo(String extraInfo) { this.extraInfo = extraInfo; }
    }
    
    /**
     * Inner class - Extra information
     */
    public static class ExtraInfo implements Serializable {
        @SerializedName("shipping_fee")
        private Double shippingFee;
        
        @SerializedName("terms_url")
        private String termsURL;
        
        @SerializedName("privacy_policy_url")
        private String privacyPolicyURL;
        
        @SerializedName("display_widget_text")
        private List<String> displayWidgetText;
        
        @SerializedName("coverage_details_text")
        private List<String> coverageDetailsText;
        
        @SerializedName("opt_out_warning_text")
        private String optOutWarningText;

        @SerializedName("widget_title")
        private String widgetTitle;
        
        // Getters and Setters
        public Double getShippingFee() { return shippingFee; }
        public void setShippingFee(Double shippingFee) { this.shippingFee = shippingFee; }
        
        public String getTermsURL() { return termsURL; }
        public void setTermsURL(String termsURL) { this.termsURL = termsURL; }
        
        public String getPrivacyPolicyURL() { return privacyPolicyURL; }
        public void setPrivacyPolicyURL(String privacyPolicyURL) { this.privacyPolicyURL = privacyPolicyURL; }
        
        public List<String> getDisplayWidgetText() { return displayWidgetText; }
        public void setDisplayWidgetText(List<String> displayWidgetText) { this.displayWidgetText = displayWidgetText; }
        
        public List<String> getCoverageDetailsText() { return coverageDetailsText; }
        public void setCoverageDetailsText(List<String> coverageDetailsText) { this.coverageDetailsText = coverageDetailsText; }
        
        public String getOptOutWarningText() { return optOutWarningText; }
        public void setOptOutWarningText(String optOutWarningText) { this.optOutWarningText = optOutWarningText; }

        public String getWidgetTitle() { return widgetTitle; }
        public void setWidgetTitle(String widgetTitle) { this.widgetTitle = widgetTitle; }
    }
    
    /**
     * Inner class - Shipping origin
     */
    public static class ShippingOrigin implements Serializable {
        @SerializedName("address_1")
        private String address1;
        
        @SerializedName("address_2")
        private String address2;
        
        @SerializedName("city")
        private String city;
        
        @SerializedName("state")
        private String state;
        
        @SerializedName("zipcode")
        private String zipcode;
        
        @SerializedName("country")
        private String country;
        
        // Getters and Setters
        public String getAddress1() { return address1; }
        public void setAddress1(String address1) { this.address1 = address1; }
        
        public String getAddress2() { return address2; }
        public void setAddress2(String address2) { this.address2 = address2; }
        
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        
        public String getZipcode() { return zipcode; }
        public void setZipcode(String zipcode) { this.zipcode = zipcode; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }

    public boolean isRejected() {
        return QuoteStatus.REJECTED.getValue().equals(this.status);
    }
}

