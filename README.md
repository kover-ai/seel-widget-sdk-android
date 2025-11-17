# Seel Widget SDK - Android

## Requirements

- Android API 24 (Android 7.0)+
- Java 17+
- Gradle 8.2+

## Installation

### Gradle (Recommended)

Add the following to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.seel.widget:seel-widget-sdk:1.0.1'
}
```

### Manual Installation

1. Download the SeelWidget SDK
2. Add the AAR file to your project's libs folder
3. Add the following to your `build.gradle`:

```gradle
dependencies {
    implementation files('libs/seel-widget-sdk-*.aar')
}
```

## Quick Start

### 1. Configure SDK

Initialize the SDK in your Application class or Activity:

```java
import com.seel.widget.core.SeelWidgetSDK;
import com.seel.widget.core.SeelEnvironment;

// Configure SDK
SeelWidgetSDK.getInstance().configure(
    this, 
    "your_api_key_here", 
    SeelEnvironment.PRODUCTION
);
```

### 2. Create Quote Component

```java
import com.seel.widget.ui.SeelWFPView;
import com.seel.widget.models.QuotesRequest;

// Create view
SeelWFPView seelWFPView = new SeelWFPView(this);

// Set callbacks
seelWFPView.setOptedInCallback((optedIn, quote) -> {
    if (optedIn) {
        // User selected protection
        System.out.println("User opted in for price: " + quote.getPrice());
    } else {
        // User declined protection
        System.out.println("User opted out");
    }
});

// Create request
QuotesRequest request = new QuotesRequest();
request.setType("seel-wfp");
request.setCartID("cart_123");
// ... set other parameters

// Setup data
seelWFPView.setup(request, callback);
```

### 3. Set/Update Quote Information

```java
// Create quote information request
QuotesRequest quotesRequest = new QuotesRequest();
quotesRequest.setType("seel-wfp");
quotesRequest.setCartID("your_cart_id");
quotesRequest.setSessionID("your_session_id");
quotesRequest.setMerchantID("your_merchant_id");
quotesRequest.setDeviceID("your_device_id");
quotesRequest.setDeviceCategory("mobile");
quotesRequest.setDevicePlatform("android");
quotesRequest.setIsDefaultOn(true);

// Add line items
List<QuotesRequest.LineItem> lineItems = new ArrayList<>();
QuotesRequest.LineItem lineItem = new QuotesRequest.LineItem();
lineItem.setLineItemID("item_1");
lineItem.setProductID("product_123");
lineItem.setProductTitle("Samsung Galaxy S24");
lineItem.setPrice(999.0);
lineItem.setQuantity(1);
lineItem.setCurrency("USD");
lineItems.add(lineItem);
quotesRequest.setLineItems(lineItems);

// Set shipping address
QuotesRequest.ShippingAddress shippingAddress = new QuotesRequest.ShippingAddress();
shippingAddress.setAddress1("123 Main St");
shippingAddress.setCity("San Francisco");
shippingAddress.setState("CA");
shippingAddress.setZipcode("94102");
shippingAddress.setCountry("US");
quotesRequest.setShippingAddress(shippingAddress);

// Set customer info
QuotesRequest.Customer customer = new QuotesRequest.Customer();
customer.setCustomerID("customer_123");
customer.setFirstName("John");
customer.setLastName("Doe");
customer.setEmail("john@example.com");
customer.setPhone("+1234567890");
quotesRequest.setCustomer(customer);

// Initial setup of quote component
seelWFPView.setup(quotesRequest, new SeelApiCallback() {
    @Override
    public void onSuccess(QuotesResponse response) {
        System.out.println("Setup successful: " + response);
    }
    
    @Override
    public void onError(NetworkError error) {
        System.out.println("Setup failed: " + error);
    }
});
```

### 4. Event Tracking

Send events to track user behavior:

```java
import com.seel.widget.models.EventsRequest;

// Create event request
EventsRequest eventRequest = new EventsRequest();
eventRequest.setSessionID("your_session_id");
eventRequest.setCustomerID("customer_123");
eventRequest.setEventSource("android_app");
eventRequest.setEventType("product_page_enter");

// Send event
SeelWidgetSDK.getInstance().createEvents(eventRequest, new SeelApiCallback() {
    @Override
    public void onSuccess(EventsResponse response) {
        System.out.println("Event sent successfully: " + response);
    }
    
    @Override
    public void onError(NetworkError error) {
        System.out.println("Event sending failed: " + error);
    }
});
```

## API Reference

### SeelWidgetSDK

The main SDK client class responsible for configuration and network requests.

#### Methods

- `configure(Context context, String apiKey)` - Configure with default production environment
- `configure(Context context, String apiKey, SeelEnvironment environment)` - Full configuration
- `getApiKey()` - Get current API key
- `getEnvironment()` - Get current environment
- `isConfigured()` - Check if configured

### SeelWFPView

The main user interface component that displays protection options.

#### Methods

- `setup(QuotesRequest request, SeelApiCallback callback)` - Set data and get quote
- `turnOn(boolean on)` - Set switch state
- `setOptedInCallback(WFPOptedInCallback callback)` - Set selection callback
- `setOpenUrlCallback(WFPOpenUrlCallback callback)` - Set URL opening callback

#### Callbacks

```java
// User selection callback
public interface WFPOptedInCallback {
    void onOptedIn(boolean optedIn, QuotesResponse quote);
}

// URL opening callback
public interface WFPOpenUrlCallback {
    void onOpenUrl(String url);
}
```

### Data Models

#### QuotesRequest

Quote request model containing the following main fields:

- `type` - Request type
- `cartID` - Cart ID
- `sessionID` - Session ID
- `merchantID` - Merchant ID
- `lineItems` - Product list
- `shippingAddress` - Shipping address
- `customer` - Customer information

#### QuotesResponse

Quote response model containing the following main fields:

- `quoteID` - Quote ID
- `price` - Protection price
- `status` - Quote status (ACCEPTED/REJECTED)
- `isDefaultOn` - Whether default enabled
- `extraInfo` - Additional information

## Environment Configuration

The SDK supports two environments:

- `SeelEnvironment.DEVELOPMENT` - Development environment
- `SeelEnvironment.PRODUCTION` - Production environment

## Permissions

The SDK requires the following permissions:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Dependencies

- Retrofit 2.9.0
- OkHttp 4.12.0
- Gson 2.9.0
- Glide 4.16.0
- AndroidX Core 1.12.0
- Material Design Components 1.11.0

## Example Usage

See the `example` module for a complete implementation example. The example demonstrates:

1. SDK initialization
2. Creating a sample request
3. Handling user interactions
4. Managing callbacks

## Project Structure

```
seel-widget-sdk-android/
├── widget/                    # SDK library module
│   ├── src/main/java/com/seel/widget/
│   │   ├── core/              # Core functionality
│   │   ├── models/            # Data models
│   │   ├── network/           # Network layer
│   │   └── ui/                # UI components
│   └── build.gradle           # Library configuration
├── example/                   # Example application
│   ├── src/main/java/com/seel/widget/example/
│   │   └── MainActivity.java  # Example implementation
│   └── build.gradle           # Example configuration
└── README_EN.md              # This file
```

## Core Components

### 1. Core Functionality (core/)
- **SeelWidgetSDK**: Main SDK for configuration
- **SeelEnvironment**: Environment enumeration (development/production)
- **Constants**: Constant definitions

### 2. Data Models (models/)
- **QuotesRequest**: Quote request model with product, customer, address information
- **QuotesResponse**: Quote response model with price, status, protection details
- **QuoteStatus**: Quote status enumeration (accepted/rejected)

### 3. Network Layer (network/)
- **SeelApiService**: Retrofit API service interface
- **SeelApiClient**: API client handling network requests and responses
- **NetworkError**: Network error type enumeration

### 4. UI Components (ui/)
- **SeelWFPView**: Main user interface component
- **SeelWFPTitleView**: Title view displaying price and brand information
- **SeelWFPInfoActivity**: Information page activity
- **CoverageDetailsView**: Protection details view
- **CoverageTipsView**: Protection tips view
- **CoverageInfoFooter**: Protection information footer
- **LineView**: Reusable line view component

## Key Features

1. **Android Compatibility**: Provides consistent API interface and functionality as android platforms
2. **Modular Design**: Clear code organization structure
3. **Network Abstraction**: Uses Retrofit for network requests
4. **Component-based UI**: Reusable UI components
5. **Configuration Management**: Multi-environment configuration support
6. **Error Handling**: Comprehensive error handling mechanism

## Integration Steps

1. Initialize SeelWidgetSDK in your Application class
2. Create SeelWFPView and set callbacks
3. Build QuotesRequest and call setup method
4. Handle user selection and URL opening callbacks

## License

Please refer to the LICENSE file for detailed information.

## Support

For questions or suggestions, please contact the Seel technical support team.

## Changelog

### Version 1.0.0
- Initial release
- Full Android compatibility
- Complete UI component set
- Network layer implementation
- Example application included

### Version 1.0.1
- Update configuration API
