package com.seel.widget.ui;

import android.app.Activity;
import android.os.Bundle;

import com.seel.widget.models.QuotesResponse;
import com.seel.widget.ui.layout.WFPInfoLayoutActions;
import com.seel.widget.ui.layout.WFPInfoLayoutFactory;
import com.seel.widget.ui.layout.WFPInfoLayoutProvider;

/**
 * Seel WFP Information Page.
 * Uses layout providers to support different brand-specific UI layouts.
 */
public class SeelWFPInfoActivity extends Activity {

    public interface OptedInClickListener {
        void onOptedInClicked();
    }

    public interface NoNeedClickListener {
        void onNoNeedClicked();
    }

    public interface PrivacyPolicyClickListener {
        void onPrivacyPolicyClicked();
    }

    public interface TermsClickListener {
        void onTermsClicked();
    }

    private static OptedInClickListener staticOptedInClickListener;
    private static NoNeedClickListener staticNoNeedClickListener;
    private static PrivacyPolicyClickListener staticPrivacyPolicyClickListener;
    private static TermsClickListener staticTermsClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QuotesResponse quoteResponse = (QuotesResponse) getIntent().getSerializableExtra("quote_response");
        String brandType = getIntent().getStringExtra("brand_type");

        WFPInfoLayoutProvider layoutProvider = WFPInfoLayoutFactory.provider(brandType);
        WFPInfoLayoutActions actions = new WFPInfoLayoutActions(
                this::finish,
                () -> {
                    if (staticOptedInClickListener != null) staticOptedInClickListener.onOptedInClicked();
                    finish();
                },
                () -> {
                    if (staticNoNeedClickListener != null) staticNoNeedClickListener.onNoNeedClicked();
                    finish();
                },
                () -> {
                    if (staticPrivacyPolicyClickListener != null) staticPrivacyPolicyClickListener.onPrivacyPolicyClicked();
                },
                () -> {
                    if (staticTermsClickListener != null) staticTermsClickListener.onTermsClicked();
                }
        );

        layoutProvider.buildLayout(this, quoteResponse, actions);
    }

    public static void setStaticCallbacks(
            OptedInClickListener optedInListener,
            NoNeedClickListener noNeedListener,
            PrivacyPolicyClickListener privacyPolicyListener,
            TermsClickListener termsListener) {
        staticOptedInClickListener = optedInListener;
        staticNoNeedClickListener = noNeedListener;
        staticPrivacyPolicyClickListener = privacyPolicyListener;
        staticTermsClickListener = termsListener;
    }
}
