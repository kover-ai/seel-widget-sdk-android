package com.seel.widget.example;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.seel.widget.SeelWidgetSDK;
import com.seel.widget.core.Constants;
import com.seel.widget.core.SeelEnvironment;
import com.seel.widget.models.EventsRequest;
import com.seel.widget.models.EventsResponse;
import com.seel.widget.models.QuotesRequest;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.network.SeelApiClient;
import com.seel.widget.ui.SeelPDPBannerView;
import com.seel.widget.ui.SeelWFPView;
import com.seel.widget.ui.layout.ToggleStyle;

/**
 * Example Activity demonstrating how to use Seel Widget SDK.
 * EBTH brand uses a simplified debug layout matching iOS ViewController.
 * Other brands use the full test controls layout.
 */
public class MainActivity extends Activity {

    private static final String TAG = "SeelWidgetExample";
    private static final String TYPE = "ebth-wfp";

    private SeelWFPView seelWFPView;
    private SeelPDPBannerView pdpBannerView;
    private ProgressBar loadingIndicator;

    // Default mode controls
    private Button setupButton;
    private Button eventButton;
    private Button updateButton;
    private Button cleanButton;
    private Switch errorSwitch;
    private Switch acceptedSwitch;
    private Switch defaultSwitch;
    private SeekBar countSeekBar;
    private TextView countValueText;
    private SeekBar optedValidTimeSeekBar;
    private TextView optedValidTimeValueText;
    private TextView localCacheText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeelWidgetSDK.getInstance().configure(this, TestDataHelper.apiKey, SeelEnvironment.DEVELOPMENT);
        SeelWFPView.optedValidTime = optedValidTime() * 60;

        if ("ebth-wfp".equals(TYPE)) {
            SeelWFPView.toggleStyle = ToggleStyle.CHECKBOX_STYLE;
            buildEBTHLayout();
        } else {
            SeelWFPView.toggleStyle = ToggleStyle.SWITCH_STYLE;
            buildDefaultLayout();
        }
    }

    // ===== EBTH Layout (matches iOS ViewController when type == "ebth-wfp") =====

    private void buildEBTHLayout() {
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        int pad16 = dp(16);
        mainLayout.setPadding(pad16, pad16, pad16, pad16);
        mainLayout.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // PDP Banner
        pdpBannerView = new SeelPDPBannerView(this);
        LinearLayout.LayoutParams bannerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mainLayout.addView(pdpBannerView, bannerParams);

        // WFP View
        seelWFPView = new SeelWFPView(this);
        GradientDrawable wfpBg = new GradientDrawable();
        wfpBg.setColor(0xFFFFFFFF);
        wfpBg.setCornerRadius(dp(8));
        seelWFPView.setBackground(wfpBg);
        LinearLayout.LayoutParams wfpParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wfpParams.topMargin = dp(12);
        mainLayout.addView(seelWFPView, wfpParams);

        // Debug Container (white card with rounded corners + shadow)
        LinearLayout debugContainer = new LinearLayout(this);
        debugContainer.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable debugBg = new GradientDrawable();
        debugBg.setColor(Color.WHITE);
        debugBg.setCornerRadius(dp(12));
        debugContainer.setBackground(debugBg);
        debugContainer.setElevation(dp(4));
        int debugPad = dp(16);
        debugContainer.setPadding(debugPad, debugPad, debugPad, debugPad);
        LinearLayout.LayoutParams debugParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        debugParams.topMargin = dp(24);
        mainLayout.addView(debugContainer, debugParams);

        // "DEBUG" title
        TextView debugTitle = new TextView(this);
        debugTitle.setText("DEBUG");
        debugTitle.setTextSize(12);
        debugTitle.setTypeface(null, Typeface.BOLD);
        debugTitle.setTextColor(Color.GRAY);
        debugContainer.addView(debugTitle);

        // Row 1: WFP on + WFP off
        LinearLayout row1 = new LinearLayout(this);
        row1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams row1Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        row1Params.topMargin = dp(12);

        Button wfpOnBtn = buildDebugButton("WFP on");
        wfpOnBtn.setOnClickListener(v -> simulateWFPOn());
        Button wfpOffBtn = buildDebugButton("WFP off");
        wfpOffBtn.setOnClickListener(v -> simulateWFPOff());

        LinearLayout.LayoutParams btnParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams2.leftMargin = dp(12);
        row1.addView(wfpOnBtn, btnParams1);
        row1.addView(wfpOffBtn, btnParams2);
        debugContainer.addView(row1, row1Params);

        // Row 2: Simulate rejected quote
        Button rejectedBtn = buildDebugButton("Simulate rejected quote");
        rejectedBtn.setOnClickListener(v -> simulateRejected());
        LinearLayout.LayoutParams rejectedParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rejectedParams.topMargin = dp(12);
        debugContainer.addView(rejectedBtn, rejectedParams);

        // Row 3: Simulate free return shipping quote
        Button freeReturnBtn = buildDebugButton("Simulate free return shipping quote");
        freeReturnBtn.setOnClickListener(v -> simulateFreeReturn());
        LinearLayout.LayoutParams freeReturnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        freeReturnParams.topMargin = dp(12);
        debugContainer.addView(freeReturnBtn, freeReturnParams);

        // Loading indicator (centered)
        loadingIndicator = new ProgressBar(this);
        loadingIndicator.setVisibility(View.GONE);
        LinearLayout.LayoutParams loadingParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingParams.gravity = Gravity.CENTER_HORIZONTAL;
        loadingParams.topMargin = dp(16);
        mainLayout.addView(loadingIndicator, loadingParams);

        seelWFPView.setOptedInCallback((optedIn, quote) -> {
            Log.d(TAG, "optedIn:" + optedIn + " price:" + (quote != null ? quote.getPrice() : 0));
        });

        SeelPDPBannerView.PDPBannerStyle bannerStyle = new SeelPDPBannerView.PDPBannerStyle();
        bannerStyle.paddingTop = dp(12);
        bannerStyle.paddingLeft = dp(12);
        bannerStyle.paddingBottom = dp(12);
        bannerStyle.paddingRight = dp(12);
        bannerStyle.cornerRadius = dp(6);
        pdpBannerView.setup(TYPE, bannerStyle);
        setContentView(mainLayout);
    }

    private Button buildDebugButton(String title) {
        Button btn = new Button(this);
        btn.setText(title);
        btn.setAllCaps(false);
        btn.setTextSize(13);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE);
        bg.setStroke(dp(1), 0xFFE5E5E5);
        bg.setCornerRadius(dp(8));
        btn.setBackground(bg);
        btn.setPadding(dp(12), dp(8), dp(12), dp(8));
        return btn;
    }

    private void simulateWFPOn() {
        setLocalOptedIn(true);
        seelWFPView.setToggleState(true);
        QuotesRequest quotes = makeEBTHDebugQuote(true, true, true, false);
        setLoading(true);
        seelWFPView.setup(quotes, new SeelApiClient.SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(QuotesResponse response) { setLoading(false); }
            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) { setLoading(false); }
        });
    }

    private void simulateWFPOff() {
        setLocalOptedIn(false);
        seelWFPView.setToggleState(false);
        QuotesRequest quotes = makeEBTHDebugQuote(false, true, true, false);
        setLoading(true);
        seelWFPView.setup(quotes, new SeelApiClient.SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(QuotesResponse response) { setLoading(false); }
            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) { setLoading(false); }
        });
    }

    private void simulateRejected() {
        QuotesRequest quotes = makeEBTHDebugQuote(false, false, false, false);
        setLoading(true);
        seelWFPView.setup(quotes, new SeelApiClient.SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(QuotesResponse response) { setLoading(false); }
            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) { setLoading(false); }
        });
    }

    private void simulateFreeReturn() {
        QuotesRequest quotes = makeEBTHDebugQuote(false, true, true, true);
        setLoading(true);
        seelWFPView.setup(quotes, new SeelApiClient.SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(QuotesResponse response) { setLoading(false); }
            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) { setLoading(false); }
        });
    }

    private QuotesRequest makeEBTHDebugQuote(boolean isDefaultOn, boolean requiresShipping,
                                              boolean hasShippingFee, boolean freeReturnEligible) {
        String json = "{\n" +
                "  \"type\": \"ebth-wfp\",\n" +
                "  \"cart_id\": \"3b87ea2a6cecdb94bae186263feb9e7f\",\n" +
                "  \"session_id\": \"3b87ea2a6cecdb94bae186263feb9e7f\",\n" +
                "  \"merchant_id\": \"20251219208123118426\",\n" +
                "  \"device_id\": \"1737534673\",\n" +
                "  \"device_category\": \"mobile\",\n" +
                "  \"device_platform\": \"android\",\n" +
                "  \"is_default_on\": " + isDefaultOn + ",\n" +
                "  \"line_items\": [{\n" +
                "    \"line_item_id\": \"11111\",\n" +
                "    \"product_id\": \"10013-0000-319802\",\n" +
                "    \"variant_id\": \"10013-0000-319802\",\n" +
                "    \"product_title\": \"Brass Crystal Mini Table Lamp\",\n" +
                "    \"variant_title\": \"Brass Crystal Mini Table Lamp\",\n" +
                "    \"price\": 50,\n" +
                "    \"quantity\": 3,\n" +
                "    \"currency\": \"USD\",\n" +
                "    \"sales_tax\": 0,\n" +
                "    \"requires_shipping\": " + requiresShipping + ",\n" +
                "    \"final_price\": \"50\",\n" +
                "    \"is_final_sale\": true,\n" +
                "    \"allocated_discounts\": 0,\n" +
                "    \"condition\": \"used\",\n" +
                "    \"category_1\": \"Household Goods\",\n" +
                "    \"category_2\": \"Decor\",\n" +
                "    \"image_urls\": [\"https://example.com/image1\", \"https://example.com/image2\"],\n" +
                "    \"shipping_origin\": { \"country\": \"US\" },\n" +
                "    \"extra_info\": { \"free_return_eligible\": " + freeReturnEligible + " }\n" +
                "  }],\n" +
                "  \"shipping_address\": {\n" +
                "    \"address_1\": \"7 Buswell Street\",\n" +
                "    \"city\": \"Boston\",\n" +
                "    \"state\": \"MA\",\n" +
                "    \"zipcode\": \"02215\",\n" +
                "    \"country\": \"US\"\n" +
                "  },\n" +
                "  \"customer\": {\n" +
                "    \"customer_id\": \"1111\",\n" +
                "    \"first_name\": \"name\",\n" +
                "    \"last_name\": \"name\",\n" +
                "    \"email\": \"xie@seel.com\",\n" +
                "    \"phone\": null\n" +
                "  },\n" +
                "  \"extra_info\": { \"shipping_fee\": " + (hasShippingFee ? 10 : 0) + " }\n" +
                "}";
        return new Gson().fromJson(json, QuotesRequest.class);
    }

    private void setLocalOptedIn(boolean optedIn) {
        SharedPreferences sp = getSharedPreferences(Constants.SEEL_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        sp.edit()
                .putString(Constants.CART_ID_KEY, "3b87ea2a6cecdb94bae186263feb9e7f")
                .putBoolean(Constants.OPTED_VALUE_KEY, optedIn)
                .putLong(Constants.OPTED_OPERATION_TIME_KEY, System.currentTimeMillis())
                .apply();
    }

    // ===== Default Layout (non-EBTH brands) =====

    private void buildDefaultLayout() {
        ScrollView scrollView = new ScrollView(this);
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        int pad = dp(16);
        mainLayout.setPadding(pad, pad, pad, pad);
        mainLayout.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // PDP Banner
        pdpBannerView = new SeelPDPBannerView(this);
        LinearLayout.LayoutParams bannerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mainLayout.addView(pdpBannerView, bannerParams);

        // WFP View
        seelWFPView = new SeelWFPView(this);
        GradientDrawable wfpBg = new GradientDrawable();
        wfpBg.setColor(0xFFFFFFFF);
        wfpBg.setCornerRadius(dp(8));
        seelWFPView.setBackground(wfpBg);
        LinearLayout.LayoutParams wfpParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wfpParams.topMargin = dp(12);
        mainLayout.addView(seelWFPView, wfpParams);

        createDefaultTestControls(mainLayout);
        createDefaultButtons(mainLayout);

        localCacheText = new TextView(this);
        localCacheText.setTextColor(Color.WHITE);
        localCacheText.setTextSize(14);
        LinearLayout.LayoutParams cacheParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cacheParams.topMargin = dp(16);
        mainLayout.addView(localCacheText, cacheParams);
        refreshLocalCacheDisplay();

        loadingIndicator = new ProgressBar(this);
        loadingIndicator.setVisibility(View.GONE);
        LinearLayout.LayoutParams loadingParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingParams.gravity = Gravity.CENTER_HORIZONTAL;
        loadingParams.topMargin = dp(16);
        mainLayout.addView(loadingIndicator, loadingParams);

        seelWFPView.setOptedInCallback((optedIn, quote) -> {
            Log.d(TAG, "optedIn:" + optedIn + " price:" + (quote != null ? quote.getPrice() : 0));
            refreshLocalCacheDisplay();
        });

        SeelPDPBannerView.PDPBannerStyle bannerStyle = new SeelPDPBannerView.PDPBannerStyle();
        bannerStyle.paddingTop = dp(12);
        bannerStyle.paddingLeft = dp(12);
        bannerStyle.paddingBottom = dp(12);
        bannerStyle.paddingRight = dp(12);
        bannerStyle.cornerRadius = dp(6);
        pdpBannerView.setup(TYPE, bannerStyle);
        scrollView.addView(mainLayout);
        setContentView(scrollView);
    }

    private void createDefaultTestControls(LinearLayout parent) {
        // Error Switch
        LinearLayout errorLayout = createSwitchLayout("Error Data", false);
        errorSwitch = (Switch) errorLayout.getChildAt(1);
        parent.addView(errorLayout);

        // Accepted Switch
        LinearLayout acceptedLayout = createSwitchLayout("Status Accepted", true);
        acceptedSwitch = (Switch) acceptedLayout.getChildAt(1);
        parent.addView(acceptedLayout);

        // Default Switch
        LinearLayout defaultLayout = createSwitchLayout("Is Default On", true);
        defaultSwitch = (Switch) defaultLayout.getChildAt(1);
        parent.addView(defaultLayout);

        // Count SeekBar
        LinearLayout countLayout = new LinearLayout(this);
        countLayout.setOrientation(LinearLayout.HORIZONTAL);
        countLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams countLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        countLayoutParams.topMargin = dp(12);

        TextView countLabel = new TextView(this);
        countLabel.setText("Product Count");
        countLabel.setTextColor(Color.WHITE);
        countLabel.setTextSize(16);

        countValueText = new TextView(this);
        countValueText.setText("3");
        countValueText.setTextColor(Color.WHITE);
        countValueText.setTextSize(16);
        countValueText.setPadding(dp(8), 0, dp(8), 0);

        countSeekBar = new SeekBar(this);
        countSeekBar.setMax(98);
        countSeekBar.setProgress(2);
        countSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                countValueText.setText(String.valueOf(progress + 1));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        countLayout.addView(countLabel, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        countLayout.addView(countSeekBar, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        countLayout.addView(countValueText, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.addView(countLayout, countLayoutParams);

        // Opted Valid Time SeekBar
        LinearLayout optedLayout = new LinearLayout(this);
        optedLayout.setOrientation(LinearLayout.HORIZONTAL);
        optedLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams optedLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        optedLayoutParams.topMargin = dp(12);

        TextView optedLabel = new TextView(this);
        optedLabel.setText("Opted Valid Time (min/mins)");
        optedLabel.setTextColor(Color.WHITE);
        optedLabel.setTextSize(16);

        optedValidTimeValueText = new TextView(this);
        optedValidTimeValueText.setText(String.valueOf(optedValidTime()));
        optedValidTimeValueText.setTextColor(Color.WHITE);
        optedValidTimeValueText.setTextSize(16);
        optedValidTimeValueText.setPadding(dp(8), 0, dp(8), 0);

        optedValidTimeSeekBar = new SeekBar(this);
        optedValidTimeSeekBar.setMax(60);
        optedValidTimeSeekBar.setProgress(optedValidTime());
        optedValidTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                optedValidTimeValueText.setText(String.valueOf(progress));
                saveValidTime(progress);
                SeelWFPView.optedValidTime = progress * 60;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        optedLayout.addView(optedLabel, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        optedLayout.addView(optedValidTimeSeekBar, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        optedLayout.addView(optedValidTimeValueText, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.addView(optedLayout, optedLayoutParams);

        // Divider
        View divider = new View(this);
        divider.setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(1));
        dividerParams.topMargin = dp(16);
        parent.addView(divider, dividerParams);
    }

    private LinearLayout createSwitchLayout(String label, boolean defaultValue) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = dp(12);
        layout.setLayoutParams(layoutParams);

        TextView labelView = new TextView(this);
        labelView.setText(label);
        labelView.setTextColor(Color.WHITE);
        labelView.setTextSize(16);

        Switch switchView = new Switch(this);
        switchView.setChecked(defaultValue);

        layout.addView(labelView, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        layout.addView(switchView);
        return layout;
    }

    private void createDefaultButtons(LinearLayout parent) {
        // Row 1: Setup + Update
        LinearLayout row1 = new LinearLayout(this);
        row1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams row1Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        row1Params.topMargin = dp(16);

        setupButton = new Button(this);
        setupButton.setText("Setup Widget");
        setupButton.setOnClickListener(v -> createSampleRequest());

        updateButton = new Button(this);
        updateButton.setText("Update Widget");
        updateButton.setOnClickListener(v -> updateQuoteRequest());

        LinearLayout.LayoutParams btn1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        btn1.rightMargin = dp(8);
        LinearLayout.LayoutParams btn2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        btn2.leftMargin = dp(8);
        row1.addView(setupButton, btn1);
        row1.addView(updateButton, btn2);
        parent.addView(row1, row1Params);

        // Row 2: Send Event + Clean
        LinearLayout row2 = new LinearLayout(this);
        row2.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams row2Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        row2Params.topMargin = dp(8);

        eventButton = new Button(this);
        eventButton.setText("Send Event");
        eventButton.setOnClickListener(v -> createEventRequest());

        cleanButton = new Button(this);
        cleanButton.setText("Clean OptedIn Cache");
        cleanButton.setOnClickListener(v -> cleanOptedIn());

        row2.addView(eventButton, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        row2.addView(cleanButton, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        parent.addView(row2, row2Params);
    }

    // ===== Default mode actions =====

    private void createSampleRequest() {
        boolean isError = errorSwitch.isChecked();
        boolean isAccepted = acceptedSwitch.isChecked();
        boolean isDefaultOn = defaultSwitch.isChecked();
        int itemCount = countSeekBar.getProgress() + 1;

        String json = TestDataHelper.generateQuoteJson(TYPE, isError, isAccepted, isDefaultOn, itemCount);
        setLoading(true);
        QuotesRequest quote = new Gson().fromJson(json, QuotesRequest.class);

        seelWFPView.setup(quote, new SeelApiClient.SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(QuotesResponse response) {
                setLoading(false);
                Log.d(TAG, "Setup success");
                refreshLocalCacheDisplay();
            }
            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) {
                setLoading(false);
                Log.e(TAG, "Setup failed: " + message);
            }
        });
    }

    private void updateQuoteRequest() {
        boolean isError = errorSwitch.isChecked();
        boolean isAccepted = acceptedSwitch.isChecked();
        boolean isDefaultOn = defaultSwitch.isChecked();
        int itemCount = countSeekBar.getProgress() + 1;

        String json = TestDataHelper.generateQuoteJson(TYPE, isError, isAccepted, isDefaultOn, itemCount);
        setLoading(true);
        QuotesRequest quote = new Gson().fromJson(json, QuotesRequest.class);

        seelWFPView.updateWidgetWhenChanged(quote, new SeelApiClient.SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(QuotesResponse response) {
                setLoading(false);
                Log.d(TAG, "Update success");
                refreshLocalCacheDisplay();
            }
            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) {
                setLoading(false);
                Log.e(TAG, "Update failed: " + message);
            }
        });
    }

    private void createEventRequest() {
        EventsRequest event = new EventsRequest();
        event.setSessionID("3b87ea2a6cecdb94bae186263feb9e7f");
        event.setCustomerID("1111");
        event.setEventSource("android");
        event.setEventType("product_page_enter");
        java.util.HashMap<String, Object> eventInfo = new java.util.HashMap<>();
        eventInfo.put("user_email", "xie@seel.com");
        event.setEventInfo(eventInfo);

        setLoading(true);
        SeelWidgetSDK.getInstance().createEvents(event, new SeelApiClient.SeelApiCallback<EventsResponse>() {
            @Override
            public void onSuccess(EventsResponse response) {
                setLoading(false);
                Log.d(TAG, "Event tracked");
                refreshLocalCacheDisplay();
            }
            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) {
                setLoading(false);
                Log.e(TAG, "Event failed: " + message);
            }
        });
    }

    private void cleanOptedIn() {
        SeelWFPView.cleanLocalOpted(getApplicationContext());
        refreshLocalCacheDisplay();
    }

    // ===== Shared helpers =====

    private void setLoading(boolean loading) {
        if (loadingIndicator == null) return;
        if (loading) {
            loadingIndicator.setVisibility(View.VISIBLE);
            if (setupButton != null) setupButton.setEnabled(false);
            if (updateButton != null) updateButton.setEnabled(false);
            if (eventButton != null) eventButton.setEnabled(false);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            if (setupButton != null) setupButton.setEnabled(true);
            if (updateButton != null) updateButton.setEnabled(true);
            if (eventButton != null) eventButton.setEnabled(true);
        }
    }

    private int optedValidTime() {
        SharedPreferences sp = getSharedPreferences(TestDataHelper.exampleSharedPreferencesName, MODE_PRIVATE);
        int val = sp.getInt(TestDataHelper.optedValidTimeKey, -1);
        return val >= 0 ? val : TestDataHelper.defaultOptedValidTime;
    }

    private void saveValidTime(int mins) {
        getSharedPreferences(TestDataHelper.exampleSharedPreferencesName, MODE_PRIVATE)
                .edit().putInt(TestDataHelper.optedValidTimeKey, mins).apply();
    }

    private void refreshLocalCacheDisplay() {
        if (localCacheText == null) return;
        SharedPreferences sp = getSharedPreferences(Constants.SEEL_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        boolean hasOpted = sp.contains(Constants.OPTED_VALUE_KEY);
        Boolean optedValue = hasOpted ? sp.getBoolean(Constants.OPTED_VALUE_KEY, false) : null;
        String cartId = sp.getString(Constants.CART_ID_KEY, "-");
        long operationTime = sp.getLong(Constants.OPTED_OPERATION_TIME_KEY, -1);

        String updatedAt = operationTime > 0
                ? DateFormat.format("yyyy-MM-dd HH:mm:ss", operationTime).toString() : "-";
        String expireAt = "-";
        boolean expired = false;
        if (operationTime > 0 && SeelWFPView.optedValidTime > 0) {
            long expireMillis = operationTime + SeelWFPView.optedValidTime * 1000L;
            expireAt = DateFormat.format("yyyy-MM-dd HH:mm:ss", expireMillis).toString();
            expired = System.currentTimeMillis() > expireMillis;
        }

        localCacheText.setText("Local cache: " + (hasOpted ? "present" : "empty") +
                "\ncart_id: " + cartId +
                "\nopted_in: " + (optedValue != null ? optedValue : "-") +
                "\nupdated_at: " + updatedAt +
                "\nexpire_at: " + expireAt + (expired ? " (expired)" : ""));
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    // ===== Test Data =====

    private static class TestDataHelper {
        static final int defaultOptedValidTime = 30;
        static final String exampleSharedPreferencesName = "SeelExampleSharedPreferences";
        static final String optedValidTimeKey = "OptedValidTimeKey";
        static final String apiKey = "yojct9zbwxok8961hr7e1s6i3fgmm1o1";

        static String generateQuoteJson(String brandType, boolean isError, boolean isAccepted,
                                         boolean isDefaultOn, int itemCount) {
            String type = isError ? "" : brandType;
            String country = isAccepted ? "US" : "CN";
            String merchantId;
            String condition;
            if ("ebth-wfp".equals(brandType)) {
                merchantId = "20251219208123118426";
                condition = "used";
            } else if ("poshmark-wfp".equals(brandType)) {
                merchantId = "20251022203385298661";
                condition = "new";
            } else {
                merchantId = "20251022203385298661";
                condition = "new";
            }

            return "{\n" +
                    "  \"type\": \"" + type + "\",\n" +
                    "  \"cart_id\": \"3b87ea2a6cecdb94bae186263feb9e7f\",\n" +
                    "  \"session_id\": \"3b87ea2a6cecdb94bae186263feb9e7f\",\n" +
                    "  \"merchant_id\": \"" + merchantId + "\",\n" +
                    "  \"device_id\": \"1737534673\",\n" +
                    "  \"device_category\": \"mobile\",\n" +
                    "  \"device_platform\": \"android\",\n" +
                    "  \"is_default_on\": " + isDefaultOn + ",\n" +
                    "  \"line_items\": [{\n" +
                    "    \"line_item_id\": \"11111\",\n" +
                    "    \"product_id\": \"10013-0000-319802\",\n" +
                    "    \"variant_id\": \"10013-0000-319802\",\n" +
                    "    \"product_title\": \"Brass Crystal Mini Table Lamp\",\n" +
                    "    \"variant_title\": \"Brass Crystal Mini Table Lamp\",\n" +
                    "    \"price\": 50,\n" +
                    "    \"quantity\": " + itemCount + ",\n" +
                    "    \"currency\": \"USD\",\n" +
                    "    \"sales_tax\": 0,\n" +
                    "    \"requires_shipping\": true,\n" +
                    "    \"final_price\": \"50\",\n" +
                    "    \"is_final_sale\": true,\n" +
                    "    \"allocated_discounts\": 0,\n" +
                    "    \"condition\": \"" + condition + "\",\n" +
                    "    \"category_1\": \"Household Goods\",\n" +
                    "    \"category_2\": \"Decor\",\n" +
                    "    \"image_urls\": [\"https://example.com/image1\", \"https://example.com/image2\"],\n" +
                    "    \"shipping_origin\": { \"country\": \"US\" },\n" +
                    "    \"extra_info\": {\n" +
                    "      \"weight_lb\": 5.0,\n" +
                    "      \"free_return_eligible\": true,\n" +
                    "      \"length_cm\": 30.0,\n" +
                    "      \"width_cm\": 20.0,\n" +
                    "      \"height_cm\": 15.0\n" +
                    "    }\n" +
                    "  }, {\n" +
                    "    \"line_item_id\": \"22222\",\n" +
                    "    \"product_id\": \"10013-0000-319803\",\n" +
                    "    \"variant_id\": \"10013-0000-319803\",\n" +
                    "    \"product_title\": \"Williams Brand Allegro 2\",\n" +
                    "    \"variant_title\": \"Williams Brand Allegro 2\",\n" +
                    "    \"price\": 10,\n" +
                    "    \"quantity\": 3,\n" +
                    "    \"currency\": \"USD\",\n" +
                    "    \"sales_tax\": 6,\n" +
                    "    \"requires_shipping\": true,\n" +
                    "    \"final_price\": \"15.00\",\n" +
                    "    \"is_final_sale\": true,\n" +
                    "    \"allocated_discounts\": 1,\n" +
                    "    \"condition\": \"" + condition + "\",\n" +
                    "    \"category_1\": \"Household Goods\",\n" +
                    "    \"category_2\": \"Decor\",\n" +
                    "    \"image_urls\": [\"https://example.com/image1\", \"https://example.com/image2\"],\n" +
                    "    \"shipping_origin\": { \"country\": \"US\" },\n" +
                    "    \"extra_info\": {\n" +
                    "      \"weight_lb\": 12.0,\n" +
                    "      \"free_return_eligible\": false,\n" +
                    "      \"length_cm\": 100.0,\n" +
                    "      \"width_cm\": 40.0,\n" +
                    "      \"height_cm\": 80.0\n" +
                    "    }\n" +
                    "  }],\n" +
                    "  \"shipping_address\": {\n" +
                    "    \"address_1\": \"7 Buswell Street\",\n" +
                    "    \"city\": \"Boston\",\n" +
                    "    \"state\": \"MA\",\n" +
                    "    \"zipcode\": \"02215\",\n" +
                    "    \"country\": \"" + country + "\"\n" +
                    "  },\n" +
                    "  \"customer\": {\n" +
                    "    \"customer_id\": \"1111\",\n" +
                    "    \"first_name\": \"name\",\n" +
                    "    \"last_name\": \"name\",\n" +
                    "    \"email\": \"xie@seel.com\",\n" +
                    "    \"phone\": null\n" +
                    "  },\n" +
                    "  \"extra_info\": {\n" +
                    "    \"shipping_fee\": 10,\n" +
                    "    \"shipping_method\": \"standard\"\n" +
                    "  }\n" +
                    "}";
        }
    }
}
