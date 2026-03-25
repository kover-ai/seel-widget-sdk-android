package com.seel.widget.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.seel.widget.SeelWidgetSDK;
import com.seel.widget.core.Constants;
import com.seel.widget.core.SeelEnvironment;
import com.seel.widget.models.QuotesRequest;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.network.SeelApiClient;
import com.seel.widget.network.SeelApiClient.SeelApiCallback;
import com.seel.widget.network.NetworkError;
import com.seel.widget.ui.layout.ToggleStyle;
import com.seel.widget.ui.layout.WFPWidgetLayoutActions;
import com.seel.widget.ui.layout.WFPWidgetLayoutData;
import com.seel.widget.ui.layout.WFPWidgetLayoutFactory;
import com.seel.widget.ui.layout.WFPWidgetLayoutProvider;

/**
 * Seel Worry-Free Purchase Main View.
 * Uses layout providers to support different brand-specific UI layouts.
 */
public class SeelWFPView extends LinearLayout {

    public interface WFPOptedInCallback {
        void onOptedIn(boolean optedIn, QuotesResponse quote);
    }

    /**
     * Opted Valid Time in seconds.
     * <=0: Never Expired.
     * Default is 365 days.
     */
    public static int optedValidTime = 365 * 24 * 3600;

    /**
     * Toggle style: SWITCH_STYLE (default) or CHECKBOX_STYLE
     */
    public static ToggleStyle toggleStyle = ToggleStyle.SWITCH_STYLE;

    private WFPOptedInCallback optedInCallback;
    private QuotesResponse quoteResponse;
    private boolean loading = false;
    private boolean toggleIsOn = true;
    private WFPWidgetLayoutProvider layoutProvider;
    private int latestRequestToken = 0;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

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
        buildDefaultLayout();
    }

    private void buildDefaultLayout() {
        rebuildLayout(null);
    }

    /**
     * Tear down existing layout and rebuild with the correct provider for the given brandType.
     */
    private void rebuildLayout(String brandType) {
        removeAllViews();
        layoutProvider = WFPWidgetLayoutFactory.provider(brandType);
        layoutProvider.buildLayout(this, new WFPWidgetLayoutActions(
                this::displayInfo,
                this::statusChanged,
                this::showDisabledTooltip
        ));
        refreshLayout();
    }

    private void refreshLayout() {
        if (layoutProvider != null) {
            layoutProvider.updateLayout(this, new WFPWidgetLayoutData(
                    quoteResponse, loading, toggleStyle, toggleIsOn
            ));
        }
    }

    private void sdkDebugLog(String message) {
        if (SeelWidgetSDK.getInstance().getEnvironment() == SeelEnvironment.PRODUCTION) return;
        android.util.Log.d("SeelWidgetSDK", message);
    }

    // MARK: - Public API

    /**
     * Set data and get quote
     */
    public void setup(QuotesRequest quote, SeelApiClient.SeelApiCallback<QuotesResponse> callback) {
        createQuote(quote, true, callback);
    }

    /**
     * Update component when cart info changes
     */
    public void updateWidgetWhenChanged(QuotesRequest quote, SeelApiClient.SeelApiCallback<QuotesResponse> callback) {
        createQuote(quote, false, callback);
    }

    /**
     * Update toggle UI state immediately.
     */
    public void setToggleState(boolean isOn) {
        toggleIsOn = isOn;
        refreshLayout();
    }

    public void setOptedInCallback(WFPOptedInCallback callback) {
        this.optedInCallback = callback;
    }

    // MARK: - Actions & Business Logic

    private void displayInfo() {
        if (quoteResponse == null) return;

        Context ctx = getContext();
        android.content.Intent intent = new android.content.Intent(ctx, SeelWFPInfoActivity.class);
        intent.putExtra("quote_response", quoteResponse);
        intent.putExtra("brand_type", quoteResponse.getType());

        if (!(ctx instanceof android.app.Activity)) {
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        SeelWFPInfoActivity.setStaticCallbacks(
                () -> {
                    updateLocalOptedIn(true);
                    turnOn(true);
                },
                () -> {
                    updateLocalOptedIn(false);
                    turnOn(false);
                },
                () -> {
                    if (quoteResponse.getExtraInfo() != null && quoteResponse.getExtraInfo().getPrivacyPolicyURL() != null) {
                        SeelWebViewController.show(ctx, quoteResponse.getExtraInfo().getPrivacyPolicyURL());
                    }
                },
                () -> {
                    if (quoteResponse.getExtraInfo() != null && quoteResponse.getExtraInfo().getTermsURL() != null) {
                        SeelWebViewController.show(ctx, quoteResponse.getExtraInfo().getTermsURL());
                    }
                }
        );

        ctx.startActivity(intent);
    }

    private void showDisabledTooltip() {
        SeelTooltipView.show(getContext(), this, quoteResponse);
    }

    private void statusChanged(boolean isOn) {
        toggleIsOn = isOn;
        updateLocalOptedIn(isOn);
        optedChanged(isOn);
    }

    private void createQuote(QuotesRequest quote, boolean isSetup, SeelApiClient.SeelApiCallback<QuotesResponse> callback) {
        latestRequestToken++;
        final int requestToken = latestRequestToken;
        loading = true;
        refreshLayout();

        SeelApiClient.getInstance(getContext()).getQuotes(quote, new SeelApiCallback<>() {
            @Override
            public void onSuccess(QuotesResponse response) {
                mainHandler.post(() -> {
                    if (requestToken != latestRequestToken) {
                        sdkDebugLog("ignore stale quote response => token: " + requestToken + ", latest: " + latestRequestToken);
                        if (callback != null) callback.onSuccess(response);
                        return;
                    }

                    loading = false;
                    if (callback != null) callback.onSuccess(response);

                    sdkDebugLog("quote response => type: " + response.getType()
                            + ", status: " + response.getStatus()
                            + ", is_default_on: " + response.getIsDefaultOn());

                    String previousType = quoteResponse != null ? quoteResponse.getType() : null;
                    quoteResponse = response;

                    if (!java.util.Objects.equals(response.getType(), previousType)) {
                        rebuildLayout(response.getType());
                    } else {
                        refreshLayout();
                    }

                    sdkDebugLog("ignore is_default_on for UI => server: " + response.getIsDefaultOn() + ", current toggle: " + toggleIsOn);
                    optedChanged(toggleIsOn);
                });
            }

            @Override
            public void onError(NetworkError error, String message) {
                mainHandler.post(() -> {
                    if (requestToken != latestRequestToken) {
                        if (callback != null) callback.onError(error, message);
                        return;
                    }

                    loading = false;
                    sdkDebugLog("quote request failed => " + message);
                    quoteResponse = null;
                    refreshLayout();
                    optedChanged(false);
                    if (callback != null) callback.onError(error, message);
                });
            }
        });
    }

    boolean turnOn(boolean on) {
        boolean isTargetOn = optedChanged(on);
        toggleIsOn = isTargetOn;
        refreshLayout();
        return isTargetOn;
    }

    private boolean canOptedIn() {
        return !loading && quoteResponse != null && !quoteResponse.isRejected();
    }

    private boolean optedChanged(boolean opted) {
        boolean isTargetOn = opted;
        if (!canOptedIn()) {
            isTargetOn = false;
        }
        if (optedInCallback != null) {
            optedInCallback.onOptedIn(isTargetOn, quoteResponse);
        }
        return isTargetOn;
    }

    // MARK: - Local Opted State Persistence

    public static void cleanLocalOpted(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SEEL_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.OPTED_VALUE_KEY);
        editor.remove(Constants.OPTED_OPERATION_TIME_KEY);
        editor.apply();
    }

    private void updateLocalOptedIn(Boolean optedIn) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SEEL_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.CART_ID_KEY, quoteResponse != null ? quoteResponse.getCartID() : null);
        editor.putBoolean(Constants.OPTED_VALUE_KEY, optedIn);
        editor.putLong(Constants.OPTED_OPERATION_TIME_KEY, System.currentTimeMillis());
        editor.apply();
    }

    private Boolean localOptedIn(String cartID) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SEEL_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        if (cartID != null && cartID.equals(sharedPreferences.getString(Constants.CART_ID_KEY, null))) {
            return sharedPreferences.getBoolean(Constants.OPTED_VALUE_KEY, toggleIsOn);
        }
        if (SeelWFPView.optedValidTime > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            long optedOperationTime = sharedPreferences.getLong(Constants.OPTED_OPERATION_TIME_KEY, -1);
            long optedExpireTime = optedOperationTime > 0 ? (optedOperationTime + SeelWFPView.optedValidTime * 1000L) : 0;
            if (optedOperationTime < 0 || optedExpireTime <= currentTimeMillis) {
                return null;
            }
        }
        return sharedPreferences.getBoolean(Constants.OPTED_VALUE_KEY, toggleIsOn);
    }
}
