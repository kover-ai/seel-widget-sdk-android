# Seel Widget SDK - Android

## Requirements

- Android API 24 (Android 7.0)+
- Java 17+
- Gradle 8.13+

## Installation

### Gradle (Recommended)

Add the following to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.seel.widgetsdk:seel-widget-sdk:1.0.18'
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
import com.seel.widget.SeelWidgetSDK;
import com.seel.widget.core.SeelEnvironment;

SeelWidgetSDK.getInstance().configure(
    this,
    "your_api_key_here",
    SeelEnvironment.PRODUCTION
);
```

### 2. Add WFP Widget

```java
import com.seel.widget.ui.SeelWFPView;
import com.seel.widget.models.QuotesRequest;

SeelWFPView seelWFPView = new SeelWFPView(this);

seelWFPView.setOptedInCallback((optedIn, quote) -> {
    if (optedIn) {
        Log.d("Seel", "User opted in, price: " + quote.getPrice());
    } else {
        Log.d("Seel", "User opted out");
    }
});

QuotesRequest request = new QuotesRequest();
request.setType("seel-wfp");
request.setCartID("cart_123");
// ... set other required fields

seelWFPView.setup(request, new SeelApiCallback<QuotesResponse>() {
    @Override
    public void onSuccess(QuotesResponse response) { }

    @Override
    public void onError(NetworkError error, String message) { }
});
```

### 3. Add PDP Banner (Optional)

```java
import com.seel.widget.ui.SeelPDPBannerView;

SeelPDPBannerView pdpBanner = new SeelPDPBannerView(this);

// With custom style
SeelPDPBannerView.PDPBannerStyle style = new SeelPDPBannerView.PDPBannerStyle();
style.paddingTop = dp(12);
style.paddingLeft = dp(12);
style.paddingBottom = dp(12);
style.paddingRight = dp(12);
style.cornerRadius = dp(6);

pdpBanner.setup("ebth-wfp", style);
```

### 4. Brand-Specific Configuration

The SDK supports brand-specific UI layouts via the Provider + Factory pattern. The layout is automatically selected based on the `type` field in `QuotesResponse`:

| Brand Type      | Widget Style | Toggle Style | Info Modal Style       |
|-----------------|-------------|--------------|------------------------|
| `ebth-wfp`      | EBTH        | Checkbox     | Bottom sheet with hero |
| `poshmark-wfp`  | Default     | Switch       | Standard info page     |
| `seel-wfp`      | Default     | Switch       | Standard info page     |

To configure the toggle style before loading data:

```java
import com.seel.widget.ui.layout.ToggleStyle;

// For EBTH brand
SeelWFPView.toggleStyle = ToggleStyle.CHECKBOX_STYLE;

// For other brands (default)
SeelWFPView.toggleStyle = ToggleStyle.SWITCH_STYLE;
```

### 5. Widget Style Customization

```java
// Background colors
seelWFPView.setNormalBackgroundColor(0xFFF5F5F5);
seelWFPView.setSelectedBackgroundColor(0xFFE8E8E8);
seelWFPView.setDisabledBackgroundColor(0xFFF0EFEF);

// Corner radius (in pixels, use dp() helper for density-independent values)
seelWFPView.setCornerRadius(dp(8));

// Show or hide disclaimer text
seelWFPView.setShowDisclaimer(false);
```

### 6. Update Quote When Cart Changes

```java
seelWFPView.updateWidgetWhenChanged(updatedRequest, new SeelApiCallback<QuotesResponse>() {
    @Override
    public void onSuccess(QuotesResponse response) { }

    @Override
    public void onError(NetworkError error, String message) { }
});
```

### 7. Event Tracking

```java
import com.seel.widget.models.EventsRequest;

EventsRequest event = new EventsRequest();
event.setSessionID("your_session_id");
event.setCustomerID("customer_123");
event.setEventSource("android");
event.setEventType("product_page_enter");

SeelWidgetSDK.getInstance().createEvents(event, new SeelApiCallback<EventsResponse>() {
    @Override
    public void onSuccess(EventsResponse response) { }

    @Override
    public void onError(NetworkError error, String message) { }
});
```

## API Reference

### SeelWidgetSDK

| Method | Description |
|--------|-------------|
| `configure(Context, String apiKey, SeelEnvironment)` | Initialize SDK |
| `getApiKey()` | Get current API key |
| `getEnvironment()` | Get current environment |
| `isConfigured()` | Check if SDK is configured |
| `createEvents(EventsRequest, SeelApiCallback)` | Send tracking event |

### SeelWFPView

| Method | Description |
|--------|-------------|
| `setup(QuotesRequest, SeelApiCallback)` | Initial setup with quote request |
| `updateWidgetWhenChanged(QuotesRequest, SeelApiCallback)` | Update when cart changes |
| `setToggleState(boolean)` | Set toggle on/off state |
| `setOptedInCallback(WFPOptedInCallback)` | Set opt-in/out callback |
| `cleanLocalOpted(Context)` | Clear local opted-in cache (static) |
| `setNormalBackgroundColor(int)` | Background color for default state |
| `setSelectedBackgroundColor(int)` | Background color when opted in |
| `setDisabledBackgroundColor(int)` | Background color when rejected |
| `setCornerRadius(float)` | Corner radius in pixels |
| `setShowDisclaimer(boolean)` | Show/hide disclaimer text |

### SeelPDPBannerView

| Method | Description |
|--------|-------------|
| `setup(String type)` | Setup with default style |
| `setup(String type, PDPBannerStyle style)` | Setup with custom style |

### Data Models

#### QuotesRequest

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `type` | String | Yes | Brand type (e.g. `"ebth-wfp"`) |
| `cartID` | String | Yes | Cart identifier |
| `sessionID` | String | Yes | Session identifier |
| `merchantID` | String | Yes | Merchant identifier |
| `deviceID` | String | No | Device identifier |
| `deviceCategory` | String | No | `"mobile"` / `"desktop"` |
| `devicePlatform` | String | No | `"android"` |
| `isDefaultOn` | Boolean | No | Default toggle state |
| `lineItems` | List | Yes | Product line items |
| `shippingAddress` | Object | No | Shipping address |
| `customer` | Object | No | Customer information |
| `extraInfo` | Map | No | Additional metadata |

#### QuotesResponse

| Field | Type | Description |
|-------|------|-------------|
| `quoteID` | String | Quote identifier |
| `price` | Double | Protection price |
| `status` | String | `"accepted"` / `"rejected"` |
| `type` | String | Brand type (drives UI layout) |
| `isDefaultOn` | Boolean | Server-suggested default state |
| `extraInfo` | Object | Display texts, URLs, coverage details |

## Environment Configuration

```java
SeelEnvironment.DEVELOPMENT  // Development / staging
SeelEnvironment.PRODUCTION   // Production
```

## Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Project Structure

```
seel-widget-sdk-android/
├── widget/                          # SDK library module
│   ├── src/main/java/com/seel/widget/
│   │   ├── core/                    # SDK configuration & constants
│   │   ├── models/                  # QuotesRequest, QuotesResponse, EventsRequest
│   │   ├── network/                 # Retrofit API client & error handling
│   │   ├── ui/                      # UI components
│   │   │   ├── SeelWFPView.java     # Main widget view
│   │   │   ├── SeelWFPInfoActivity  # Info modal activity
│   │   │   ├── SeelPDPBannerView    # PDP banner component
│   │   │   ├── SeelCheckbox.java    # Custom checkbox component
│   │   │   ├── SeelTooltipView.java # Disabled-state tooltip
│   │   │   └── layout/             # Brand-specific layout system
│   │   │       ├── *LayoutProvider  # Layout interfaces
│   │   │       ├── *LayoutFactory   # Factory classes
│   │   │       ├── Default*Layout   # Default brand implementations
│   │   │       └── EBTH*Layout      # EBTH brand implementations
│   │   └── utils/                   # Utility classes
│   └── src/main/res/
│       ├── drawable/                # Vector drawables
│       └── mipmap/                  # Image assets (PNG)
├── example/                         # Example application
│   └── src/main/java/.../MainActivity.java
└── build.gradle
```

## Architecture: Brand Layout System

The SDK uses a **Provider + Factory** pattern to support brand-specific UIs:

```
QuotesResponse.type
        │
        ▼
  LayoutFactory.provider(type)
        │
        ├── "ebth-wfp"  → EBTHWFPWidgetLayout
        ├── "poshmark-wfp" → DefaultWFPWidgetLayout
        └── (default)    → DefaultWFPWidgetLayout
```

Each brand can customize three UI surfaces independently:
- **Widget** (`WFPWidgetLayoutProvider`) — the inline cart widget
- **Info Modal** (`WFPInfoLayoutProvider`) — the detail/opt-in modal
- **PDP Banner** (`PDPBannerLayoutProvider`) — the product page banner

## Dependencies

- Retrofit 2.9.0
- OkHttp 4.12.0
- Gson 2.9.0
- Glide 4.16.0
- AndroidX Core 1.12.0
- Material Design Components 1.11.0

## Example

See the `example` module for a complete implementation. The example demonstrates:

- SDK initialization and configuration
- EBTH brand: simplified debug controls (WFP on/off, rejected, free return)
- Default brand: full test controls (error toggle, accepted toggle, product count, etc.)
- PDP Banner with custom padding and corner radius
- Opted-in state caching and display

## Changelog

### Version 1.0.18
- Add locale-aware currency formatting (`FormatMoney`) aligned with iOS SDK
- Add widget style customization APIs: `setNormalBackgroundColor`, `setSelectedBackgroundColor`, `setDisabledBackgroundColor`, `setCornerRadius`, `setShowDisclaimer`
- Refactor brand-specific defaults with Provider Defaults pattern (OCP compliant)
- Fix disclaimer text duplication and background color not applying correctly
- Fix checkbox toggle not updating background color
- Harden error handling and extract UI strings to resources
- Update README with widget customization documentation

### Version 1.0.3
- Add brand-specific layout system (Provider + Factory pattern)
- Add EBTH brand layouts (widget, info modal, PDP banner)
- Add SeelCheckbox, SeelTooltipView, SeelPDPBannerView components
- Refactor SeelWFPView and SeelWFPInfoActivity to use layout providers
- Align required fields and UI with iOS SDK
- Add image assets to mipmap/

### Version 1.0.1
- Update configuration API

### Version 1.0.0
- Initial release

## License

Please refer to the LICENSE file for detailed information.

## Support

For questions or suggestions, please contact the Seel technical support team.
