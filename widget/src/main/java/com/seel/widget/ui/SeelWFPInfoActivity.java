package com.seel.widget.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.seel.widget.R;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.utils.DpPxUtils;

/**
 * Seel WFP Information Page
 */
public class SeelWFPInfoActivity extends Activity {
    
    // Callback interfaces
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
    
    // Static callbacks
    private static OptedInClickListener staticOptedInClickListener;
    private static NoNeedClickListener staticNoNeedClickListener;
    private static PrivacyPolicyClickListener staticPrivacyPolicyClickListener;
    private static TermsClickListener staticTermsClickListener;
    
    // Data
    private QuotesResponse quoteResponse;
    
    // UI Components
    private LinearLayout rootContainer;
    private LinearLayout navigationBar;
    private TextView navigationTitle;
    private TextView closeButton;
    private View navigationDivider;
    private android.widget.FrameLayout backgroundView;
    private ScrollView scrollView;
    private LinearLayout contentView;
    private CoverageTitleView wfpView;
    private TextView seelLabel;
    private CoverageDetailsView coverageDetailsView;
    private CoverageTipsView coverageTipsView;
    private CoverageInfoFooter coverageInfoFooter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get passed data
        quoteResponse = (QuotesResponse) getIntent().getSerializableExtra("quote_response");
        
        createViews();
        configViews();
        updateViews();
    }
    
    private void createViews() {
        // Create root container
        rootContainer = new LinearLayout(this);
        rootContainer.setOrientation(LinearLayout.VERTICAL);
        rootContainer.setBackgroundColor(0x80000000); // Gray transparent background
        
        // Create navigation bar
        navigationBar = new LinearLayout(this);
        navigationBar.setOrientation(LinearLayout.HORIZONTAL);
        navigationBar.setGravity(android.view.Gravity.CENTER_VERTICAL);
        navigationBar.setBackgroundColor(getResources().getColor(R.color.seel_nav_background));
        // Set initial padding, will be adjusted in configViews()
        navigationBar.setPadding(DpPxUtils.dp(16), 0, DpPxUtils.dp(16), 0);
        
        // Create navigation title
        navigationTitle = new TextView(this);
        navigationTitle.setText(quoteResponse.getExtraInfo().getWidgetTitle());
        navigationTitle.setTextColor(getResources().getColor(android.R.color.black));
        navigationTitle.setTextSize(18);
        navigationTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        
        // Create close button
        closeButton = new TextView(this);
        closeButton.setText("Close");
        closeButton.setTextColor(getResources().getColor(android.R.color.black));
        closeButton.setTextSize(17);
        closeButton.setTypeface(null, android.graphics.Typeface.NORMAL);
        closeButton.setOnClickListener(v -> finish());
        
        // Create navigation divider
        navigationDivider = new View(this);
        navigationDivider.setBackgroundColor(0xFFEEEEEE); // #EEEEEE color
        
        // Create background view
        backgroundView = new android.widget.FrameLayout(this);
        backgroundView.setBackgroundColor(0xFFFFFFFF);
        
        // Create scroll view
        scrollView = new ScrollView(this);
        scrollView.setVerticalScrollBarEnabled(true);
        scrollView.setHorizontalScrollBarEnabled(false);
        
        // Create content view
        contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        
        // Create WFP title view
        wfpView = new CoverageTitleView(this);
        wfpView.setTitle(quoteResponse.getExtraInfo().getWidgetTitle());
        
        // Create Seel label
        seelLabel = new TextView(this);
        seelLabel.setText("What's Covered by Seel");
        seelLabel.setTextColor(getResources().getColor(android.R.color.black));
        seelLabel.setTextSize(16);
        seelLabel.setTypeface(null, android.graphics.Typeface.BOLD);
        
        // Set entire text to bold, "Seel" part in primary color
        android.text.SpannableString spannableString = new android.text.SpannableString("What's Covered by Seel");
        int seelStart = "What's Covered by ".length();
        int seelEnd = spannableString.length();
        spannableString.setSpan(new android.text.style.ForegroundColorSpan(getResources().getColor(R.color.seel_primary)), 
                               seelStart, seelEnd, android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        seelLabel.setText(spannableString);
        
        // Create coverage details view
        coverageDetailsView = new CoverageDetailsView(this);
        // Set background color to #333333, corner radius 4dp
        float detailsRadius = DpPxUtils.dp(4); // Convert 4dp to pixels
        coverageDetailsView.setBackground(new android.graphics.drawable.GradientDrawable() {
            {
                setColor(0xFF333333);
                setCornerRadius(detailsRadius);
            }
        });
        
        // Create coverage tips view
        coverageTipsView = new CoverageTipsView(this);
        
        // Create coverage info footer
        coverageInfoFooter = new CoverageInfoFooter(this);
        
        // Assemble navigation bar
        navigationBar.addView(navigationTitle);
        navigationBar.addView(closeButton);
        
        // Assemble content view
        contentView.addView(wfpView);
        contentView.addView(seelLabel);
        contentView.addView(coverageDetailsView);
        contentView.addView(coverageTipsView);
        contentView.addView(coverageInfoFooter);
        
        // Assemble scroll view
        scrollView.addView(contentView);
        
        // Assemble background view
        backgroundView.addView(scrollView);
        
        // Assemble root container
        rootContainer.addView(navigationBar);
        rootContainer.addView(navigationDivider);
        rootContainer.addView(backgroundView);
        
        setContentView(rootContainer);
    }
    
    private void configViews() {
        // Set root container layout params - full screen
        android.widget.FrameLayout.LayoutParams rootParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT, 
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        );
        rootContainer.setLayoutParams(rootParams);
        
        // Set navigation bar layout params
        LinearLayout.LayoutParams navParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                DpPxUtils.dp(44) // Navigation bar height
        );
        navigationBar.setLayoutParams(navParams);

        // Set navigation title layout params
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                0, 
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                1.0f
        );
        titleParams.gravity = android.view.Gravity.CENTER; // Center horizontally
        navigationTitle.setGravity(android.view.Gravity.CENTER); // Center text
        navigationTitle.setLayoutParams(titleParams);
        
        // Set close button layout params
        LinearLayout.LayoutParams closeParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        closeButton.setLayoutParams(closeParams);
        
        // Set navigation divider layout params
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                DpPxUtils.dp(1)
        );
        navigationDivider.setLayoutParams(dividerParams);
        
        // Set background view layout params
        LinearLayout.LayoutParams bgParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f // Give it weight to fill remaining space
        );
        backgroundView.setLayoutParams(bgParams);
        
        // Set scroll view layout params
        android.widget.FrameLayout.LayoutParams scrollParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT, 
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        );
        scrollView.setLayoutParams(scrollParams);
        
        // Set content view layout params
        android.widget.FrameLayout.LayoutParams contentParams = new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT, 
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
        );
        contentView.setLayoutParams(contentParams);
        
        // Set WFP view layout params
        LinearLayout.LayoutParams wfpParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        wfpParams.setMargins(DpPxUtils.dp(20), DpPxUtils.dp(10), DpPxUtils.dp(20), 0);
        wfpView.setLayoutParams(wfpParams);
        
        // Set Seel label layout params
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        labelParams.setMargins(DpPxUtils.dp(20), DpPxUtils.dp(20), DpPxUtils.dp(20), 0);
        seelLabel.setLayoutParams(labelParams);
        
        // Set coverage details view layout params
        LinearLayout.LayoutParams detailsParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        detailsParams.setMargins(DpPxUtils.dp(20), DpPxUtils.dp(20), DpPxUtils.dp(20), 0);
        coverageDetailsView.setLayoutParams(detailsParams);
        
        // Set coverage tips view layout params
        LinearLayout.LayoutParams tipsParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tipsParams.setMargins(DpPxUtils.dp(30), DpPxUtils.dp(20), DpPxUtils.dp(30), 0);
        coverageTipsView.setLayoutParams(tipsParams);
        
        // Set coverage info footer layout params
        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        footerParams.setMargins(DpPxUtils.dp(20), DpPxUtils.dp(20), DpPxUtils.dp(20), DpPxUtils.dp(18));
        coverageInfoFooter.setLayoutParams(footerParams);
        
        // Set callbacks
        coverageInfoFooter.setOptedInClickListener(() -> {
            if (staticOptedInClickListener != null) {
                staticOptedInClickListener.onOptedInClicked();
            }
            finish();
        });
        
        coverageInfoFooter.setNoNeedClickListener(() -> {
            if (staticNoNeedClickListener != null) {
                staticNoNeedClickListener.onNoNeedClicked();
            }
            finish();
        });
        
        coverageInfoFooter.setPrivacyPolicyClickListener(() -> {
            if (staticPrivacyPolicyClickListener != null) {
                staticPrivacyPolicyClickListener.onPrivacyPolicyClicked();
            }
        });
        
        coverageInfoFooter.setTermsClickListener(() -> {
            if (staticTermsClickListener != null) {
                staticTermsClickListener.onTermsClicked();
            }
        });
    }
    
    private void updateViews() {
        if (quoteResponse == null) return;
        
        // Update WFP view
        wfpView.setPrice(quoteResponse.getPrice());
        wfpView.updateViews();
        
        // Update coverage details view
        coverageDetailsView.setQuoteResponse(quoteResponse);
        coverageDetailsView.updateViews();
    }
    
    // Static callback setup method
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




