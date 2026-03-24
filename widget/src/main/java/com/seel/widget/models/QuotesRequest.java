package com.seel.widget.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Quote request model
 */
public class QuotesRequest {
    
    /** [Required] The type of the quote, e.g. "seel-wfp" */
    @SerializedName("type")
    private String type;
    
    /** The ID of a cart */
    @SerializedName("cart_id")
    private String cartID;
    
    /** [Required] The ID of the shopping session */
    @SerializedName("session_id")
    private String sessionID;
    
    /** The unique identifier for the merchant within Seel's system */
    @SerializedName("merchant_id")
    private String merchantID;
    
    /** The ID of the client device */
    @SerializedName("device_id")
    private String deviceID;
    
    /** [Required] The type of device from which user activity originated: desktop, mobile or tablet */
    @SerializedName("device_category")
    private String deviceCategory;
    
    /** [Required] The method by which users accessed your website or application: Web, iOS or Android */
    @SerializedName("device_platform")
    private String devicePlatform;
    
    /** The IP address of the client */
    @SerializedName("client_ip")
    private String clientIp;
    
    /** [Required] The default opt-in setting for the quote */
    @SerializedName("is_default_on")
    private Boolean isDefaultOn;
    
    /** [Required] The list of items included in the quote */
    @SerializedName("line_items")
    private List<LineItem> lineItems;
    
    /** [Required] Shipping address information */
    @SerializedName("shipping_address")
    private ShippingAddress shippingAddress;
    
    /** [Required] Customer information */
    @SerializedName("customer")
    private Customer customer;
    
    /** Additional information for the quote (open structure, accepts any key-value pairs) */
    @SerializedName("extra_info")
    private Map<String, Object> extraInfo;
    
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
    
    public String getClientIp() { return clientIp; }
    public void setClientIp(String clientIp) { this.clientIp = clientIp; }
    
    public Boolean getIsDefaultOn() { return isDefaultOn; }
    public void setIsDefaultOn(Boolean isDefaultOn) { this.isDefaultOn = isDefaultOn; }
    
    public List<LineItem> getLineItems() { return lineItems; }
    public void setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems; }
    
    public ShippingAddress getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddress shippingAddress) { this.shippingAddress = shippingAddress; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public Map<String, Object> getExtraInfo() { return extraInfo; }
    public void setExtraInfo(Map<String, Object> extraInfo) { this.extraInfo = extraInfo; }
    
    /**
     * Line item
     */
    public static class LineItem {
        /** [Required] The ID of the item */
        @SerializedName("line_item_id")
        private String lineItemID;
        
        /** [Required] The ID of the product */
        @SerializedName("product_id")
        private String productID;
        
        /** The ID of the product variant */
        @SerializedName("variant_id")
        private String variantID;
        
        /** [Required] The title of the product */
        @SerializedName("product_title")
        private String productTitle;
        
        /** The description of the product */
        @SerializedName("product_description")
        private String productDescription;
        
        /** The title of the product variant */
        @SerializedName("variant_title")
        private String variantTitle;
        
        /** The sku of the product variant */
        @SerializedName("sku")
        private String sku;
        
        /** The ID of the seller */
        @SerializedName("seller_id")
        private String sellerID;
        
        /** The name of the seller */
        @SerializedName("seller_name")
        private String sellerName;
        
        /** The brand name of the product */
        @SerializedName("brand_name")
        private String brandName;
        
        /** [Required] The quantity of the product */
        @SerializedName("quantity")
        private Integer quantity;
        
        /** [Required] The price of the product */
        @SerializedName("price")
        private Double price;
        
        /** [Required] The allocated discounts of the product */
        @SerializedName("allocated_discounts")
        private Double allocatedDiscounts;
        
        /** [Required] The sales tax of the product */
        @SerializedName("sales_tax")
        private Double salesTax;
        
        /** The retail price of the product */
        @SerializedName("retail_price")
        private Double retailPrice;
        
        /** [Required] The final price of the product */
        @SerializedName("final_price")
        private Double finalPrice;
        
        /** [Required] The currency of the price (ISO 4217), e.g. "USD" */
        @SerializedName("currency")
        private String currency;
        
        /** [Required] Whether the item requires shipping or not */
        @SerializedName("requires_shipping")
        private Boolean requiresShipping;
        
        /** The URL of the product */
        @SerializedName("product_url")
        private String productURL;
        
        /** The URLs of the product images */
        @SerializedName("image_urls")
        private List<String> imageURLs;
        
        /** [Required] The main category of the product */
        @SerializedName("category_1")
        private String category1;
        
        /** [Required] The sub category of the product */
        @SerializedName("category_2")
        private String category2;
        
        /** The sub category 3 of the product */
        @SerializedName("category_3")
        private String category3;
        
        /** The sub category 4 of the product */
        @SerializedName("category_4")
        private String category4;
        
        /** [Required] Whether the item is final sale or not */
        @SerializedName("is_final_sale")
        private Boolean isFinalSale;
        
        /** [Required] The physical condition of the item: "new", "used", or "refurbished" */
        @SerializedName("condition")
        private String condition;
        
        /** Product attributes (e.g. color, size) */
        @SerializedName("product_attributes")
        private ProductAttributes productAttributes;
        
        /** Shipping origin information */
        @SerializedName("shipping_origin")
        private ShippingOrigin shippingOrigin;
        
        /** Extra information about the item (open structure, accepts any key-value pairs) */
        @SerializedName("extra_info")
        private Map<String, Object> extraInfo;
        
        // Getters and Setters
        public String getLineItemID() { return lineItemID; }
        public void setLineItemID(String lineItemID) { this.lineItemID = lineItemID; }
        
        public String getProductID() { return productID; }
        public void setProductID(String productID) { this.productID = productID; }
        
        public String getVariantID() { return variantID; }
        public void setVariantID(String variantID) { this.variantID = variantID; }
        
        public String getProductTitle() { return productTitle; }
        public void setProductTitle(String productTitle) { this.productTitle = productTitle; }
        
        public String getProductDescription() { return productDescription; }
        public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
        
        public String getVariantTitle() { return variantTitle; }
        public void setVariantTitle(String variantTitle) { this.variantTitle = variantTitle; }
        
        public String getSku() { return sku; }
        public void setSku(String sku) { this.sku = sku; }
        
        public String getSellerID() { return sellerID; }
        public void setSellerID(String sellerID) { this.sellerID = sellerID; }
        
        public String getSellerName() { return sellerName; }
        public void setSellerName(String sellerName) { this.sellerName = sellerName; }
        
        public String getBrandName() { return brandName; }
        public void setBrandName(String brandName) { this.brandName = brandName; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        
        public Double getAllocatedDiscounts() { return allocatedDiscounts; }
        public void setAllocatedDiscounts(Double allocatedDiscounts) { this.allocatedDiscounts = allocatedDiscounts; }
        
        public Double getSalesTax() { return salesTax; }
        public void setSalesTax(Double salesTax) { this.salesTax = salesTax; }
        
        public Double getRetailPrice() { return retailPrice; }
        public void setRetailPrice(Double retailPrice) { this.retailPrice = retailPrice; }
        
        public Double getFinalPrice() { return finalPrice; }
        public void setFinalPrice(Double finalPrice) { this.finalPrice = finalPrice; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public Boolean getRequiresShipping() { return requiresShipping; }
        public void setRequiresShipping(Boolean requiresShipping) { this.requiresShipping = requiresShipping; }
        
        public String getProductURL() { return productURL; }
        public void setProductURL(String productURL) { this.productURL = productURL; }
        
        public List<String> getImageURLs() { return imageURLs; }
        public void setImageURLs(List<String> imageURLs) { this.imageURLs = imageURLs; }
        
        public String getCategory1() { return category1; }
        public void setCategory1(String category1) { this.category1 = category1; }
        
        public String getCategory2() { return category2; }
        public void setCategory2(String category2) { this.category2 = category2; }
        
        public String getCategory3() { return category3; }
        public void setCategory3(String category3) { this.category3 = category3; }
        
        public String getCategory4() { return category4; }
        public void setCategory4(String category4) { this.category4 = category4; }
        
        public Boolean getIsFinalSale() { return isFinalSale; }
        public void setIsFinalSale(Boolean isFinalSale) { this.isFinalSale = isFinalSale; }
        
        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }
        
        public ProductAttributes getProductAttributes() { return productAttributes; }
        public void setProductAttributes(ProductAttributes productAttributes) { this.productAttributes = productAttributes; }
        
        public ShippingOrigin getShippingOrigin() { return shippingOrigin; }
        public void setShippingOrigin(ShippingOrigin shippingOrigin) { this.shippingOrigin = shippingOrigin; }
        
        public Map<String, Object> getExtraInfo() { return extraInfo; }
        public void setExtraInfo(Map<String, Object> extraInfo) { this.extraInfo = extraInfo; }
    }
    
    /**
     * Shipping address
     */
    public static class ShippingAddress {
        /** [Required] The first line of the shipping address */
        @SerializedName("address_1")
        private String address1;
        
        /** The second line of the shipping address */
        @SerializedName("address_2")
        private String address2;
        
        /** [Required] The city of the shipping address */
        @SerializedName("city")
        private String city;
        
        /** [Required] The state or province code of the shipping address */
        @SerializedName("state")
        private String state;
        
        /** [Required] The zipcode of the shipping address */
        @SerializedName("zipcode")
        private String zipcode;
        
        /** [Required] ISO 3166-1 alpha-2 country code */
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
     * Customer information
     */
    public static class Customer {
        /** [Required] The unique identifier for the customer */
        @SerializedName("customer_id")
        private String customerID;
        
        /** The first name of the customer */
        @SerializedName("first_name")
        private String firstName;
        
        /** The last name of the customer */
        @SerializedName("last_name")
        private String lastName;
        
        /** [Required] The email address of the customer */
        @SerializedName("email")
        private String email;
        
        /** The phone number of the customer */
        @SerializedName("phone")
        private String phone;
        
        /** Extra information about the customer (open structure, accepts any key-value pairs) */
        @SerializedName("extra_info")
        private Map<String, Object> extraInfo;
        
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
        
        public Map<String, Object> getExtraInfo() { return extraInfo; }
        public void setExtraInfo(Map<String, Object> extraInfo) { this.extraInfo = extraInfo; }
    }
    
    /**
     * Product attributes
     */
    public static class ProductAttributes {
        /** The color of the product */
        @SerializedName("color")
        private String color;
        
        /** The size of the product */
        @SerializedName("size")
        private String size;
        
        // Getters and Setters
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        
        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }
    }
    
    /**
     * Shipping origin
     */
    public static class ShippingOrigin {
        /** [Required] ISO 3166-1 alpha-2 country code */
        @SerializedName("country")
        private String country;
        
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
        
        // Getters and Setters
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        
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
    }
}
