package com.seel.widget.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.ProgressBar;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;

import com.google.gson.Gson;
import com.seel.widget.SeelWidgetSDK;
import com.seel.widget.core.SeelClient;
import com.seel.widget.core.SeelEnvironment;
import java.util.HashMap;
import com.seel.widget.models.EventsRequest;
import com.seel.widget.models.EventsResponse;
import com.seel.widget.models.QuotesRequest;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.network.SeelApiClient;
import com.seel.widget.ui.SeelWFPView;

/**
 * Example Activity demonstrating how to use Seel Widget SDK
 */
public class MainActivity extends Activity {

    private SeelWFPView seelWFPView;
    private Button setupButton;
    private Button eventButton;
    private Button updateButton;
    
    // Test parameter controls
    private Switch errorSwitch;
    private Switch acceptedSwitch;
    private Switch defaultSwitch;
    private SeekBar countSeekBar;
    private TextView countValueText;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SeelClient
        SeelClient.getInstance().configure(this, "yojct9zbwxok8961hr7e1s6i3fgmm1o1", SeelEnvironment.DEVELOPMENT, null);

        // Create main layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(50, 50, 50, 50);
        mainLayout.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // Create SeelWFPView
        seelWFPView = new SeelWFPView(this);
        android.graphics.drawable.GradientDrawable background = new android.graphics.drawable.GradientDrawable();
        background.setColor(0xFFFFFFFF);
        background.setCornerRadius(32);
        seelWFPView.setBackground(background);

        // Create test parameter controls
        createTestControls(mainLayout);

        // Create buttons
        createButtons(mainLayout);

        // Create loading indicator
        loadingIndicator = new ProgressBar(this);
        loadingIndicator.setVisibility(View.GONE);
        mainLayout.addView(loadingIndicator);

        // Set callbacks
        seelWFPView.setOptedInCallback((optedIn, quote) -> {
            if (optedIn) {
                android.util.Log.d("SeelWidgetExample", "User opted in for price: " + quote.getPrice());
            } else {
                android.util.Log.d("SeelWidgetExample", "User opted out");
            }
        });

        setContentView(mainLayout);
    }

    private void createTestControls(LinearLayout parent) {
        // Add SeelWFPView
        parent.addView(seelWFPView);

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
        countLayout.setPadding(0, 20, 0, 20);

        TextView countLabel = new TextView(this);
        countLabel.setText("Product Count: ");
        countLabel.setTextColor(Color.WHITE);
        countLabel.setTextSize(16);
        countLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        countValueText = new TextView(this);
        countValueText.setText("3");
        countValueText.setTextColor(Color.WHITE);
        countValueText.setTextSize(16);
        countValueText.setPadding(20, 0, 20, 0);
        countValueText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        countSeekBar = new SeekBar(this);
        countSeekBar.setMax(98); // 1-99
        countSeekBar.setProgress(2); // Default to 3
        countSeekBar.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        countSeekBar.setPadding(10, 0, 10, 0);
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

        countLayout.addView(countLabel);
        countLayout.addView(countValueText);
        countLayout.addView(countSeekBar);
        parent.addView(countLayout);
    }

    private LinearLayout createSwitchLayout(String label, boolean defaultValue) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(0, 20, 0, 20);

        TextView labelView = new TextView(this);
        labelView.setText(label);
        labelView.setTextColor(Color.WHITE);
        labelView.setTextSize(16);
        labelView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        Switch switchView = new Switch(this);
        switchView.setChecked(defaultValue);

        layout.addView(labelView);
        layout.addView(switchView);
        return layout;
    }

    private void createButtons(LinearLayout parent) {
        // Create horizontal layout for Setup and Update buttons
        LinearLayout buttonRow1 = new LinearLayout(this);
        buttonRow1.setOrientation(LinearLayout.HORIZONTAL);
        buttonRow1.setPadding(0, 20, 0, 20);

        // Setup Button
        setupButton = new Button(this);
        setupButton.setText("Setup Widget");
        setupButton.setOnClickListener(v -> createSampleRequest());
        setupButton.setPadding(20, 20, 20, 20);
        LinearLayout.LayoutParams setupParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        setupParams.setMargins(0, 0, 10, 0);
        setupButton.setLayoutParams(setupParams);

        // Update Button
        updateButton = new Button(this);
        updateButton.setText("Update Widget");
        updateButton.setOnClickListener(v -> updateQuoteRequest());
        updateButton.setPadding(20, 20, 20, 20);
        LinearLayout.LayoutParams updateParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        updateParams.setMargins(10, 0, 0, 0);
        updateButton.setLayoutParams(updateParams);

        buttonRow1.addView(setupButton);
        buttonRow1.addView(updateButton);
        parent.addView(buttonRow1);

        // Event Button (full width)
        eventButton = new Button(this);
        eventButton.setText("Send Event");
        eventButton.setOnClickListener(v -> createEventRequest());
        eventButton.setPadding(20, 20, 20, 20);
        parent.addView(eventButton);
    }

    private void createSampleRequest() {
        // Get test parameters from UI controls
        boolean isError = errorSwitch.isChecked();
        boolean isAccepted = acceptedSwitch.isChecked();
        boolean isDefaultOn = defaultSwitch.isChecked();
        int itemCount = countSeekBar.getProgress() + 1;

        android.util.Log.d("SeelWidgetExample", "Creating quote with parameters - Error: " + isError + 
                ", Accepted: " + isAccepted + ", DefaultOn: " + isDefaultOn + ", Count: " + itemCount);

        // Generate test data based on parameters
        String json = TestDataHelper.generateQuoteJson(isError, isAccepted, isDefaultOn, itemCount);
        
//        android.util.Log.d("SeelWidgetExample", "Generated JSON: " + json);

        // Show loading indicator
        setLoading(true);

        // Use Gson to parse JSON string to QuotesRequest object
        Gson gson = new Gson();
        QuotesRequest quote = gson.fromJson(json, QuotesRequest.class);

        // Set data and get quote
        seelWFPView.setup(quote, new SeelApiClient.SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(com.seel.widget.models.QuotesResponse response) {
                setLoading(false);
                android.util.Log.d("SeelWidgetExample", "Setup quote successfully: " + response);
            }

            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) {
                setLoading(false);
                android.util.Log.e("SeelWidgetExample", "Setup quote failed: " + message);
            }
        });
    }

    private void updateQuoteRequest() {
        // Get test parameters from UI controls
        boolean isError = errorSwitch.isChecked();
        boolean isAccepted = acceptedSwitch.isChecked();
        boolean isDefaultOn = defaultSwitch.isChecked();
        int itemCount = countSeekBar.getProgress() + 1;

        android.util.Log.d("SeelWidgetExample", "Updating quote with parameters - Error: " + isError + 
                ", Accepted: " + isAccepted + ", DefaultOn: " + isDefaultOn + ", Count: " + itemCount);

        // Generate test data based on parameters
        String json = TestDataHelper.generateQuoteJson(isError, isAccepted, isDefaultOn, itemCount);
        
        android.util.Log.d("SeelWidgetExample", "Generated JSON: " + json);

        // Show loading indicator
        setLoading(true);

        // Use Gson to parse JSON string to QuotesRequest object
        Gson gson = new Gson();
        QuotesRequest quote = gson.fromJson(json, QuotesRequest.class);

        // Set data and get quote
        seelWFPView.updateWidgetWhenChanged(quote, new SeelApiClient.SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(com.seel.widget.models.QuotesResponse response) {
                setLoading(false);
                android.util.Log.d("SeelWidgetExample", "Update quote successfully: " + response);
            }

            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) {
                setLoading(false);
                android.util.Log.e("SeelWidgetExample", "Update quote failed: " + message);
            }
        });
    }

    private void createEventRequest() {
        EventsRequest event = new EventsRequest();
        event.setSessionID("3b87ea2a6cecdb94bae186263feb9e7f");
        event.setCustomerID("1111");
        event.setEventSource("android");
        event.setEventType("product_page_enter");
        HashMap<String, Object> eventInfo = new HashMap<>();
        eventInfo.put("user_email", "xie@seel.com");
        event.setEventInfo(eventInfo);

        setLoading(true);
        SeelWidgetSDK.getInstance().createEvents(event, new SeelApiClient.SeelApiCallback<EventsResponse>() {
            @Override
            public void onSuccess(com.seel.widget.models.EventsResponse response) {
                setLoading(false);
                android.util.Log.d("SeelWidgetExample", "Event tracked successfully: " + response);
            }

            @Override
            public void onError(com.seel.widget.network.NetworkError error, String message) {
                setLoading(false);
                android.util.Log.e("SeelWidgetExample", "Event tracking failed: " + message);
            }
        });
    }

    private void setLoading(boolean loading) {
        if (loading) {
            loadingIndicator.setVisibility(View.VISIBLE);
            setupButton.setEnabled(false);
            updateButton.setEnabled(false);
            eventButton.setEnabled(false);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            setupButton.setEnabled(true);
            updateButton.setEnabled(true);
            eventButton.setEnabled(true);
        }
    }

    /**
     * Helper class to generate test data based on parameters
     */
    private static class TestDataHelper {
        
        public static String generateQuoteJson(boolean isError, boolean isAccepted, boolean isDefaultOn, int itemCount) {
            String type = isError ? "" : "poshmark-wfp";
            String country = isAccepted ? "US" : "CN";
            
            return "{\n" +
                    "    \"type\": \"" + type + "\",\n" +
                    "    \"cart_id\": \"3b87ea2a6cecdb94bae186263feb9e7f\",\n" +
                    "    \"session_id\": \"3b87ea2a6cecdb94bae186263feb9e7f\",\n" +
                    "    \"merchant_id\": \"20251022203385298661\",\n" +
                    "    \"device_id\": \"1737534673\",\n" +
                    "    \"device_category\": \"web\",\n" +
                    "    \"device_platform\": \"android\",\n" +
                    "    \"is_default_on\": " + isDefaultOn + ",\n" +
                    "    \"line_items\": [\n" +
                    "        {\n" +
                    "            \"line_item_id\": \"11111\",\n" +
                    "            \"product_id\": \"10013-0000-319802\",\n" +
                    "            \"variant_id\": \"10013-0000-319802\",\n" +
                    "            \"product_title\": \"Williams Brand Allegro 2 Model Digital Piano w/ Accessories\",\n" +
                    "            \"variant_title\": \"Williams Brand Allegro 2 Model Digital Piano w/ Accessories\",\n" +
                    "            \"price\": 50,\n" +
                    "            \"quantity\": " + itemCount + ",\n" +
                    "            \"currency\": \"USD\",\n" +
                    "            \"sales_tax\": 0,\n" +
                    "            \"requires_shipping\": true,\n" +
                    "            \"final_price\": \"50\",\n" +
                    "            \"is_final_sale\": true,\n" +
                    "            \"allocated_discounts\": 0,\n" +
                    "            \"category_1\": \"Household Goods\",\n" +
                    "            \"category_2\": \"Decor\",\n" +
                    "            \"image_urls\": [\n" +
                    "                \"https://example.com/image1\",\n" +
                    "                \"https://example.com/image2\"\n" +
                    "            ],\n" +
                    "            \"shipping_origin\": {\n" +
                    "                \"country\": \"US\"\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"line_item_id\": \"22222\",\n" +
                    "            \"product_id\": \"10013-0000-319803\",\n" +
                    "            \"variant_id\": \"10013-0000-319803\",\n" +
                    "            \"product_title\": \"Williams Brand Allegro 2\",\n" +
                    "            \"variant_title\": \"Williams Brand Allegro 2\",\n" +
                    "            \"price\": 10,\n" +
                    "            \"quantity\": 3,\n" +
                    "            \"currency\": \"USD\",\n" +
                    "            \"sales_tax\": 6,\n" +
                    "            \"requires_shipping\": true,\n" +
                    "            \"final_price\": \"15.00\",\n" +
                    "            \"is_final_sale\": true,\n" +
                    "            \"allocated_discounts\": 1,\n" +
                    "            \"category_1\": \"Household Goods\",\n" +
                    "            \"category_2\": \"Decor\",\n" +
                    "            \"image_urls\": [\n" +
                    "                \"https://example.com/image1\",\n" +
                    "                \"https://example.com/image2\"\n" +
                    "            ],\n" +
                    "            \"shipping_origin\": {\n" +
                    "                \"country\": \"US\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"shipping_address\": {\n" +
                    "        \"address_1\": \"7 Buswell Street\",\n" +
                    "        \"city\": \"Boston\",\n" +
                    "        \"state\": \"MA\",\n" +
                    "        \"zipcode\": \"02215\",\n" +
                    "        \"country\": \"" + country + "\"\n" +
                    "    },\n" +
                    "    \"customer\": {\n" +
                    "        \"customer_id\": \"1111\",\n" +
                    "        \"first_name\": \"name\",\n" +
                    "        \"last_name\": \"name\",\n" +
                    "        \"email\": \"xie@seel.com\",\n" +
                    "        \"phone\": null\n" +
                    "    },\n" +
                    "    \"extra_info\": {\n" +
                    "        \"shipping_fee\": 10\n" +
                    "    }\n" +
                    "}";
        }
    }
}