package com.seel.widget.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Quote request model
 */
public class QuotesRequest {
    
    @SerializedName("type")
    private String type;
    
    @SerializedName("cart_id")
    private String cartID;
    
    @SerializedName("session_id")
    private String sessionID;
    
    @SerializedName("merchant_id")
    private String merchantID;
    
    @SerializedName("device_id")
    private String deviceID;
    
    @SerializedName("device_category")
    private String deviceCategory;
    
    @SerializedName("device_platform")
    private String devicePlatform;
    
    @SerializedName("is_default_on")
    private Boolean isDefaultOn;
    
    @SerializedName("line_items")
    private List<LineItem> lineItems;
    
    @SerializedName("shipping_address")
    private ShippingAddress shippingAddress;
    
    @SerializedName("customer")
    private Customer customer;
    
    @SerializedName("extra_info")
    private ExtraInfo extraInfo;
    
    // Constructor
    public QuotesRequest() {}
    
    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getCartID() { return cartID; }
    public void setCartID(String cartID) { this.cartID = cartID; }
    
    public String getSessionID() { return sessionID; }
    public void setSessionID(String sessionID) { this.sessionID = sessionID; }
    
    public String getMerchantID() { return merchantID; }
    public void setMerchantID(String merchantID) { this.merchantID = merchantID; }
    
    public String getDeviceID() { return deviceID; }
    public void setDeviceID(String deviceID) { this.deviceID = deviceID; }
    
    public String getDeviceCategory() { return deviceCategory; }
    public void setDeviceCategory(String deviceCategory) { this.deviceCategory = deviceCategory; }
    
    public String getDevicePlatform() { return devicePlatform; }
    public void setDevicePlatform(String devicePlatform) { this.devicePlatform = devicePlatform; }
    
    public Boolean getIsDefaultOn() { return isDefaultOn; }
    public void setIsDefaultOn(Boolean isDefaultOn) { this.isDefaultOn = isDefaultOn; }
    
    public List<LineItem> getLineItems() { return lineItems; }
    public void setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems; }
    
    public ShippingAddress getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddress shippingAddress) { this.shippingAddress = shippingAddress; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public ExtraInfo getExtraInfo() { return extraInfo; }
    public void setExtraInfo(ExtraInfo extraInfo) { this.extraInfo = extraInfo; }
    
    /**
     * Inner class - Line item
     */
    public static class LineItem {
        @SerializedName("line_item_id")
        private String lineItemID;
        
        @SerializedName("product_id")
        private String productID;
        
        @SerializedName("variant_id")
        private String variantID;
        
        @SerializedName("product_title")
        private String productTitle;
        
        @SerializedName("variant_title")
        private String variantTitle;
        
        @SerializedName("price")
        private Double price;
        
        @SerializedName("quantity")
        private Integer quantity;
        
        @SerializedName("currency")
        private String currency;
        
        @SerializedName("sales_tax")
        private Double salesTax;
        
        @SerializedName("requires_shipping")
        private Boolean requiresShipping;
        
        @SerializedName("final_price")
        private String finalPrice;
        
        @SerializedName("is_final_sale")
        private Boolean isFinalSale;
        
        @SerializedName("allocated_discounts")
        private Double allocatedDiscounts;
        
        @SerializedName("category_1")
        private String category1;
        
        @SerializedName("category_2")
        private String category2;
        
        @SerializedName("image_urls")
        private List<String> imageURLs;
        
        @SerializedName("brand_name")
        private String brandName;
        
        @SerializedName("shipping_origin")
        private ShippingOrigin shippingOrigin;
        
        // Getters and Setters
        public String getLineItemID() { return lineItemID; }
        public void setLineItemID(String lineItemID) { this.lineItemID = lineItemID; }
        
        public String getProductID() { return productID; }
        public void setProductID(String productID) { this.productID = productID; }
        
        public String getVariantID() { return variantID; }
        public void setVariantID(String variantID) { this.variantID = variantID; }
        
        public String getProductTitle() { return productTitle; }
        public void setProductTitle(String productTitle) { this.productTitle = productTitle; }
        
        public String getVariantTitle() { return variantTitle; }
        public void setVariantTitle(String variantTitle) { this.variantTitle = variantTitle; }
        
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public Double getSalesTax() { return salesTax; }
        public void setSalesTax(Double salesTax) { this.salesTax = salesTax; }
        
        public Boolean getRequiresShipping() { return requiresShipping; }
        public void setRequiresShipping(Boolean requiresShipping) { this.requiresShipping = requiresShipping; }
        
        public String getFinalPrice() { return finalPrice; }
        public void setFinalPrice(String finalPrice) { this.finalPrice = finalPrice; }
        
        public Boolean getIsFinalSale() { return isFinalSale; }
        public void setIsFinalSale(Boolean isFinalSale) { this.isFinalSale = isFinalSale; }
        
        public Double getAllocatedDiscounts() { return allocatedDiscounts; }
        public void setAllocatedDiscounts(Double allocatedDiscounts) { this.allocatedDiscounts = allocatedDiscounts; }
        
        public String getCategory1() { return category1; }
        public void setCategory1(String category1) { this.category1 = category1; }
        
        public String getCategory2() { return category2; }
        public void setCategory2(String category2) { this.category2 = category2; }
        
        public List<String> getImageURLs() { return imageURLs; }
        public void setImageURLs(List<String> imageURLs) { this.imageURLs = imageURLs; }
        
        public String getBrandName() { return brandName; }
        public void setBrandName(String brandName) { this.brandName = brandName; }
        
        public ShippingOrigin getShippingOrigin() { return shippingOrigin; }
        public void setShippingOrigin(ShippingOrigin shippingOrigin) { this.shippingOrigin = shippingOrigin; }
    }
    
    /**
     * Inner class - Shipping address
     */
    public static class ShippingAddress {
        @SerializedName("address_1")
        private String address1;
        
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
    public static class Customer {
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
    }
    
    /**
     * Inner class - Extra information
     */
    public static class ExtraInfo {
        @SerializedName("shipping_fee")
        private Double shippingFee;
        
        // Getters and Setters
        public Double getShippingFee() { return shippingFee; }
        public void setShippingFee(Double shippingFee) { this.shippingFee = shippingFee; }
    }
    
    /**
     * Inner class - Shipping origin
     */
    public static class ShippingOrigin {
        @SerializedName("country")
        private String country;
        
        // Getters and Setters
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }
}

