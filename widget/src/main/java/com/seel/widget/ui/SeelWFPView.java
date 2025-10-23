package com.seel.widget.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.seel.widget.ui.SeelSwitch;
import android.widget.TextView;

import com.seel.widget.R;
import com.seel.widget.core.Constants;
import com.seel.widget.models.QuotesRequest;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.models.QuoteStatus;
import com.seel.widget.models.EventsRequest;
import com.seel.widget.models.EventsResponse;
import com.seel.widget.network.SeelApiClient;
import com.seel.widget.network.SeelApiClient.SeelApiCallback;
import com.seel.widget.network.NetworkError;
import com.seel.widget.utils.DpPxUtils;

/**
 * Seel Worry-Free Purchase Main View
 */
public class SeelWFPView extends LinearLayout {

    // Callback interfaces
    public interface WFPOptedInCallback {
        void onOptedIn(boolean optedIn, QuotesResponse quote);
    }

    // Callbacks
    private WFPOptedInCallback optedInCallback;

    // Data
    private QuotesResponse quoteResponse;
    private Boolean loading;

    // UI Components
    private SeelWFPTitleView titleView;
    private SeelSwitch switcher;
    private LinearLayout detailContainer;

    public SeelWFPView(Context context) {
        super(context);
        init();
    }

    public SeelWFPView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeelWFPView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);

        loading = false;

        // Create views
        createViews();
        configViews();
        updateViews();
    }

    private void createViews() {
        // Create title view
        titleView = new SeelWFPTitleView(getContext());
        titleView.setInfoClickListener(new SeelWFPTitleView.InfoClickListener() {
            @Override
            public void onInfoClicked() {
                displayInfo();
            }
        });

        // Create switch
        switcher = new SeelSwitch(getContext());
        switcher.setOnValueChangedListener((isChecked) -> {
            statusChanged(isChecked);
        });
        
        // Set style using constants
        int switcherColor = Constants.PRIMARY_COLOR;
        int trackColorOff = Constants.TRACK_COLOR_OFF;
        int trackColorOn = switcherColor;
        int thumbColor = Constants.THUMB_COLOR;
        
        // Set colors for SeelSwitch
        switcher.setOnTintColor(trackColorOn);
        switcher.setTrackTintColor(trackColorOff);
        switcher.setThumbTintColor(thumbColor);

        // Create detail container
        detailContainer = new LinearLayout(getContext());
        detailContainer.setOrientation(VERTICAL);

        LinearLayout titleContainer = new LinearLayout(getContext());
        titleContainer.setOrientation(HORIZONTAL);
        titleContainer.addView(titleView);
        titleContainer.addView(switcher);

        addView(titleContainer);
        addView(detailContainer);
    }

    private void configViews() {
        setBackgroundColor(Constants.BACKGROUND_COLOR);
        int padding = DpPxUtils.dp(16);
        setPadding(padding, padding, padding, padding);

        // Set layout parameters
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        titleView.setLayoutParams(titleParams);
        titleView.setShowPowered(true);

        LinearLayout.LayoutParams switchParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        switcher.setLayoutParams(switchParams);
    }

    /**
     * Set data and get quote
     */
    public void setup(QuotesRequest quote, SeelApiClient.SeelApiCallback<QuotesResponse> callback) {
        getQuote(quote, callback);
    }

    /**
     * Update component when cart info changes
     */
    public void updateWidgetWhenChanged(QuotesRequest quote, SeelApiClient.SeelApiCallback<QuotesResponse> callback) {
        getQuote(quote, callback);
    }

    /**
     * Set switch state
     */
    void turnOn(boolean on) {
        switcher.setOn(on);
        if (quoteResponse != null) {
            optedChanged(on);
        }
    }

    /**
     * Get quote
     */
    private void getQuote(QuotesRequest quote, SeelApiClient.SeelApiCallback<QuotesResponse> callback) {
        loading = true;
        updateViews();
        SeelApiClient.getInstance(getContext()).getQuotes(quote, new SeelApiCallback<QuotesResponse>() {
            @Override
            public void onSuccess(QuotesResponse response) {
                loading = false;
                ((Activity) getContext()).runOnUiThread(() -> {
                    quoteResponse = response;
                    updateViews();
                    
                    // Set switch state based on isDefaultOn
                    boolean isTargetOn = response.getIsDefaultOn() != null && response.getIsDefaultOn();
                    boolean canOptedIn = canOptedIn();
                    if (!canOptedIn) {
                        isTargetOn = false;
                    }
                    if (switcher.isOn() != isTargetOn) {
                        turnOn(isTargetOn);
                    }
                    
                    // Call external callback
                    if (callback != null) {
                        callback.onSuccess(response);
                    }
                });
            }

            @Override
            public void onError(NetworkError error, String message) {
                loading = false;
                quoteResponse = null;
                updateViews();
                // Handle error
                android.util.Log.e("SeelWFPView", "SeelWFPView_Get quote error: " + message);
                if (callback != null) {
                    callback.onError(error, message);
                }
            }
        });
    }

    /**
     * Update views
     */
    private void updateViews() {
        setVisibility(quoteResponse == null ? GONE : VISIBLE);

        boolean isRejected = quoteResponse != null && quoteResponse.isRejected();

        setAlpha(isRejected ? 0.9f : 1.0f);

        // Update title view
        titleView.setTitle(quoteResponse != null ? quoteResponse.getExtraInfo().getWidgetTitle() : null);
        titleView.setPrice(quoteResponse != null && !isRejected ? quoteResponse.getPrice() : null);
        titleView.setShowInfo(quoteResponse != null && !isRejected);
        titleView.setLoading(loading);
        titleView.updateViews();

        // Update switch
        switcher.setVisibility((quoteResponse == null || isRejected) ? GONE : VISIBLE);

        // Update details
        updateDetailViews();
    }

    /**
     * Update detail views
     */
    private void updateDetailViews() {
        detailContainer.removeAllViews();

        if (quoteResponse == null) return;

        // Show coverage information
        if (quoteResponse.getExtraInfo() != null &&
                quoteResponse.getExtraInfo().getDisplayWidgetText() != null) {
            for (String text : quoteResponse.getExtraInfo().getDisplayWidgetText()) {
                LineView displayText = new LineView(getContext());
                int textSize = quoteResponse.getExtraInfo().getDisplayWidgetText().size();
                if (textSize > 1) {
                    displayText.setIconImage(R.mipmap.icon_select);
                } else {
                    displayText.setIconImage(0);
                }
                displayText.setContent(text);
                displayText.updateViews();
                detailContainer.addView(displayText);
            }
        }
    }

    /**
     * Display info page
     */
    private void displayInfo() {
        if (quoteResponse == null) return;

        // Launch detail Activity
        android.content.Intent intent = new android.content.Intent(getContext(), SeelWFPInfoActivity.class);
        intent.putExtra("quote_response", (java.io.Serializable) quoteResponse);
        
        // Use static callback handling mechanism
        SeelWFPInfoActivity.setStaticCallbacks(
            () -> {
                // OptedIn callback
                turnOn(true);
            },
            () -> {
                // NoNeed callback
                turnOn(false);
            },
            () -> {
                // PrivacyPolicy callback
                if (quoteResponse.getExtraInfo() != null && quoteResponse.getExtraInfo().getPrivacyPolicyURL() != null) {
                   SeelWebViewController.show(getContext(), quoteResponse.getExtraInfo().getPrivacyPolicyURL());
                }
            },
            () -> {
                // Terms callback
                if (quoteResponse.getExtraInfo() != null && quoteResponse.getExtraInfo().getTermsURL() != null) {
                   SeelWebViewController.show(getContext(), quoteResponse.getExtraInfo().getTermsURL());
                }
            }
        );
        
        getContext().startActivity(intent);
    }

    /**
     * Switch state changed
     */
    private void statusChanged(boolean isChecked) {
        optedChanged(isChecked);
    }

    /**
     * Check if can opt in
     */
    private boolean canOptedIn() {
        return quoteResponse != null && !quoteResponse.isRejected();
    }
    
    /**
     * Opt-in state changed
     */
    private void optedChanged(boolean opted) {
        if (!canOptedIn()) {
            return;
        }

        if (optedInCallback != null) {
            optedInCallback.onOptedIn(opted, quoteResponse);
        }
    }

    // Set callbacks
    public void setOptedInCallback(WFPOptedInCallback callback) {
        this.optedInCallback = callback;
    }
}
