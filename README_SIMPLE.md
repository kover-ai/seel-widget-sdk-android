# Seel Widget SDK - Android

[![CI Status](https://img.shields.io/travis/seel/SeelWidget.svg?style=flat)](https://travis-ci.org/seel/SeelWidget)
[![Version](https://img.shields.io/cocoapods/v/SeelWidget.svg?style=flat)](https://cocoapods.org/pods/SeelWidget)
[![License](https://img.shields.io/cocoapods/l/SeelWidget.svg?style=flat)](https://cocoapods.org/pods/SeelWidget)
[![Platform](https://img.shields.io/cocoapods/p/SeelWidget.svg?style=flat)](https://cocoapods.org/pods/SeelWidget)

The Seel Widget SDK for Android provides a seamless integration of Seel's Worry-Free Purchase™ protection service into your Android applications.

## Features

- 🛡️ **Warranty Service Integration** - Provide warranty and insurance services for products
- 📱 **Native Android Components** - Fully native Android user interface
- 🎨 **Customizable Styling** - Support for custom appearance and themes
- 📊 **Real-time Pricing** - Calculate warranty prices based on product information in real-time
- 🔄 **Event Tracking** - Complete user behavior tracking and analytics
- 📋 **Detailed Information Display** - Detailed warranty terms and coverage explanations

## Requirements

- Android API 24 (Android 7.0)+
- Java 11+
- Gradle 7.0+

## Installation

### Gradle (Recommended)

Add the following to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.seel.widget:seel-widget-sdk:2.6.0'
}
```

### Manual Installation

1. Download the SeelWidget SDK
2. Add the AAR file to your project's libs folder
3. Add the following to your `build.gradle`:

```gradle
dependencies {
    implementation files('libs/seel-widget-sdk-2.6.0.aar')
}
```

## Quick Start

### 1. Configure SDK

Initialize the SDK in your Application class or Activity:

```java
import com.seel.widget.core.SeelClient;
import com.seel.widget.core.SeelEnvironment;

// Configure SDK
SeelClient.getInstance().configure(
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

### 3. Event Tracking

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
SeelClient.getInstance().createEvents(eventRequest, new SeelApiCallback() {
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

### SeelClient

#### Methods

```java
// Configure SDK
void configure(Context context, String apiKey, SeelEnvironment environment)

// Send events
void createEvents(EventsRequest event, SeelApiCallback callback)
```

#### Properties

```java
// Singleton instance
static SeelClient getInstance()

// API Key
String getApiKey()

// Current environment
SeelEnvironment getEnvironment()

// Whether configured
boolean isConfigured()
```

### SeelWFPView

#### Methods

```java
// Set quote information
void setup(QuotesRequest request, SeelApiCallback callback)

// Update component when quote information changes
void updateWidgetWhenChanged(QuotesRequest request, SeelApiCallback callback)
```

## Changelog

### Version 2.6.0
- Initial release
- Basic quote component functionality
- Event tracking support

**Note**: Please ensure you use the correct API Key and environment settings in production.
