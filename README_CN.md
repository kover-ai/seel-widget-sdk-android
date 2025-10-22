# Seel Widget SDK - Android

[![CI Status](https://img.shields.io/travis/seel/SeelWidget.svg?style=flat)](https://travis-ci.org/seel/SeelWidget)
[![Version](https://img.shields.io/cocoapods/v/SeelWidget.svg?style=flat)](https://cocoapods.org/pods/SeelWidget)
[![License](https://img.shields.io/cocoapods/l/SeelWidget.svg?style=flat)](https://cocoapods.org/pods/SeelWidget)
[![Platform](https://img.shields.io/cocoapods/p/SeelWidget.svg?style=flat)](https://cocoapods.org/pods/SeelWidget)

Seel Widget SDK的Android Java版本，为Android应用提供Seel的Worry-Free Purchase™保障服务的无缝集成。

## 功能特性

- 🛡️ **保修服务集成** - 为商品提供保修和保险服务
- 📱 **原生Android组件** - 完全原生的Android用户界面
- 🎨 **可定制样式** - 支持自定义外观和主题
- 📊 **实时报价** - 基于商品信息实时计算保修价格
- 🔄 **事件追踪** - 完整的用户行为追踪和分析
- 📋 **详细信息展示** - 详细的保修条款和覆盖范围说明

## 系统要求

- Android API 24 (Android 7.0)+
- Java 11+
- Gradle 7.0+

## 安装

### Gradle (推荐)

在你的 `build.gradle` 文件中添加：

```gradle
dependencies {
    implementation 'com.seel.widget:seel-widget-sdk:2.6.0'
}
```

### 手动安装

1. 下载 SeelWidget SDK
2. 将 AAR 文件添加到项目的 libs 文件夹
3. 在你的 `build.gradle` 中添加：

```gradle
dependencies {
    implementation files('libs/seel-widget-sdk-2.6.0.aar')
}
```

## 快速开始

### 1. 配置SDK

在你的Application类或Activity中初始化SDK：

```java
import com.seel.widget.core.SeelClient;
import com.seel.widget.core.SeelEnvironment;

// 配置SDK
SeelClient.getInstance().configure(
    this, 
    "your_api_key_here", 
    SeelEnvironment.PRODUCTION
);
```

### 2. 创建报价组件

```java
import com.seel.widget.ui.SeelWFPView;
import com.seel.widget.models.QuotesRequest;

// 创建视图
SeelWFPView seelWFPView = new SeelWFPView(this);

// 设置回调
seelWFPView.setOptedInCallback((optedIn, quote) -> {
    if (optedIn) {
        // 用户选择了保障
        System.out.println("User opted in for price: " + quote.getPrice());
    } else {
        // 用户拒绝了保障
        System.out.println("User opted out");
    }
});

// 创建请求
QuotesRequest request = new QuotesRequest();
request.setType("seel-wfp");
request.setCartID("cart_123");
// ... 设置其他参数

// 设置数据
seelWFPView.setup(request, callback);
```

### 3. 设置/更新报价信息

```java
// 创建报价信息请求
QuotesRequest quotesRequest = new QuotesRequest();
quotesRequest.setType("seel-wfp");
quotesRequest.setCartID("your_cart_id");
quotesRequest.setSessionID("your_session_id");
quotesRequest.setMerchantID("your_merchant_id");
quotesRequest.setDeviceID("your_device_id");
quotesRequest.setDeviceCategory("mobile");
quotesRequest.setDevicePlatform("android");
quotesRequest.setIsDefaultOn(true);

// 添加商品条目
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

// 设置配送地址
QuotesRequest.ShippingAddress shippingAddress = new QuotesRequest.ShippingAddress();
shippingAddress.setAddress1("123 Main St");
shippingAddress.setCity("San Francisco");
shippingAddress.setState("CA");
shippingAddress.setZipcode("94102");
shippingAddress.setCountry("US");
quotesRequest.setShippingAddress(shippingAddress);

// 设置客户信息
QuotesRequest.Customer customer = new QuotesRequest.Customer();
customer.setCustomerID("customer_123");
customer.setFirstName("John");
customer.setLastName("Doe");
customer.setEmail("john@example.com");
customer.setPhone("+1234567890");
quotesRequest.setCustomer(customer);

// 首次设置报价组件
seelWFPView.setup(quotesRequest, new SeelApiCallback() {
    @Override
    public void onSuccess(QuotesResponse response) {
        System.out.println("设置成功: " + response);
    }
    
    @Override
    public void onError(NetworkError error) {
        System.out.println("设置失败: " + error);
    }
});
```

### 4. 事件追踪

发送事件来追踪用户行为：

```java
import com.seel.widget.models.EventsRequest;

// 创建事件请求
EventsRequest eventRequest = new EventsRequest();
eventRequest.setSessionID("your_session_id");
eventRequest.setCustomerID("customer_123");
eventRequest.setEventSource("android_app");
eventRequest.setEventType("product_page_enter");

// 发送事件
SeelClient.getInstance().createEvents(eventRequest, new SeelApiCallback() {
    @Override
    public void onSuccess(EventsResponse response) {
        System.out.println("事件发送成功: " + response);
    }
    
    @Override
    public void onError(NetworkError error) {
        System.out.println("事件发送失败: " + error);
    }
});
```

## API 参考

### SeelClient

主要的SDK客户端类，负责配置和网络请求。

#### 方法

- `configure(Context context, String apiKey)` - 使用默认生产环境配置
- `configure(Context context, String apiKey, SeelEnvironment environment, String baseURL)` - 完整配置
- `getApiKey()` - 获取当前API密钥
- `getEnvironment()` - 获取当前环境
- `getBaseURL()` - 获取基础URL
- `isConfigured()` - 检查是否已配置

### SeelWFPView

主要的用户界面组件，显示保障选项。

#### 方法

- `setup(QuotesRequest request, SeelApiCallback callback)` - 设置数据并获取报价
- `turnOn(boolean on)` - 设置开关状态
- `setOptedInCallback(WFPOptedInCallback callback)` - 设置选择回调
- `setOpenUrlCallback(WFPOpenUrlCallback callback)` - 设置URL打开回调

#### 回调接口

```java
// 用户选择回调
public interface WFPOptedInCallback {
    void onOptedIn(boolean optedIn, QuotesResponse quote);
}

// URL打开回调
public interface WFPOpenUrlCallback {
    void onOpenUrl(String url);
}
```

### 数据模型

#### QuotesRequest

报价请求模型，包含以下主要字段：

- `type` - 请求类型
- `cartID` - 购物车ID
- `sessionID` - 会话ID
- `merchantID` - 商户ID
- `lineItems` - 商品列表
- `shippingAddress` - 发货地址
- `customer` - 客户信息

#### QuotesResponse

报价响应模型，包含以下主要字段：

- `quoteID` - 报价ID
- `price` - 保障价格
- `status` - 报价状态（ACCEPTED/REJECTED）
- `isDefaultOn` - 是否默认开启
- `extraInfo` - 额外信息

## 环境配置

SDK支持两种环境：

- `SeelEnvironment.DEVELOPMENT` - 开发环境
- `SeelEnvironment.PRODUCTION` - 生产环境

## 权限要求

SDK需要以下权限：

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 最低要求

- **Android API 24 (Android 7.0)**
- **Java 11+**

## 依赖库

- Retrofit 2.9.0
- OkHttp 4.12.0
- Gson 2.9.0
- Glide 4.16.0
- AndroidX Core 1.12.0
- Material Design Components 1.11.0

## 使用示例

查看 `example` 模块获取完整的实现示例。示例演示了：

1. SDK初始化
2. 创建示例请求
3. 处理用户交互
4. 管理回调

## 项目结构

```
seel-widget-sdk-android/
├── widget/                    # SDK库模块
│   ├── src/main/java/com/seel/widget/
│   │   ├── core/              # 核心功能
│   │   ├── models/            # 数据模型
│   │   ├── network/           # 网络层
│   │   └── ui/                # UI组件
│   └── build.gradle           # 库配置
├── example/                   # 示例应用
│   ├── src/main/java/com/seel/widget/example/
│   │   └── MainActivity.java  # 示例实现
│   └── build.gradle           # 示例配置
└── README.md                  # 本文档
```

## 核心组件

### 1. 核心功能 (core/)
- **SeelClient**: 主要的SDK客户端，负责配置和网络请求
- **SeelEnvironment**: 环境枚举（开发/生产）
- **Constants**: 常量定义

### 2. 数据模型 (models/)
- **QuotesRequest**: 报价请求模型，包含商品、客户、地址信息
- **QuotesResponse**: 报价响应模型，包含价格、状态、保障详情
- **QuoteStatus**: 报价状态枚举（接受/拒绝）

### 3. 网络层 (network/)
- **SeelApiService**: Retrofit API服务接口
- **SeelApiClient**: API客户端，处理网络请求和响应
- **NetworkError**: 网络错误类型枚举

### 4. UI组件 (ui/)
- **SeelWFPView**: 主要的用户界面组件
- **SeelWFPTitleView**: 标题视图，显示价格和品牌信息
- **SeelWFPInfoActivity**: 信息页面Activity
- **CoverageDetailsView**: 保障详情视图
- **CoverageTipsView**: 保障提示视图
- **CoverageInfoFooter**: 保障信息页脚
- **LineView**: 可重用的行视图组件

## 主要特性

1. **Android兼容性**: 提供一致的API接口和功能
2. **模块化设计**: 清晰的代码组织结构
3. **网络抽象**: 使用Retrofit进行网络请求
4. **组件化UI**: 可重用的UI组件
5. **配置管理**: 多环境配置支持
6. **错误处理**: 完善的错误处理机制

## 集成步骤

1. 在你的Application类中初始化SeelClient
2. 创建SeelWFPView并设置回调
3. 构建QuotesRequest并调用setup方法
4. 处理用户选择和URL打开回调

## 许可证

请参考LICENSE文件了解详细信息。

## 支持

如有问题或建议，请联系Seel技术支持团队。

## 更新日志

### 版本 2.6.0
- 初始发布
- 完整的Android兼容性
- 完整的UI组件集
- 网络层实现
- 包含示例应用






