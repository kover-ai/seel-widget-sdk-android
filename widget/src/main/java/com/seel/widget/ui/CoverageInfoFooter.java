package com.seel.widget.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

/**
 * Coverage Info Footer
 */
public class CoverageInfoFooter extends LinearLayout {
    
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
    
    // Callbacks
    private OptedInClickListener optedInClickListener;
    private NoNeedClickListener noNeedClickListener;
    private PrivacyPolicyClickListener privacyPolicyClickListener;
    private TermsClickListener termsClickListener;
    
    // UI Components
    private Button optedInButton;
    private TextView noNeedButton;
    private TextView privacyPolicyButton;
    private TextView termsButton;
    
    public CoverageInfoFooter(Context context) {
        super(context);
        init();
    }
    
    public CoverageInfoFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public CoverageInfoFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        setOrientation(VERTICAL);
        setGravity(android.view.Gravity.CENTER); // Center child views
        createViews();
        configViews();
    }
    
    private void createViews() {
        // Create Opt-In button
        optedInButton = new Button(getContext());
        optedInButton.setText("Opt-In Now for Full Protection");
        optedInButton.setAllCaps(false);
        optedInButton.setTextColor(getResources().getColor(android.R.color.white));
        optedInButton.setTextSize(12);

        float radius = DpPxUtils.dp(19);
        optedInButton.setBackground(new android.graphics.drawable.GradientDrawable() {
            {
                setColor(0xFF333333);
                setCornerRadius(radius);
            }
        });
        
        // Set left and right padding to 17dp
        int horizontalPadding = DpPxUtils.dp(17);
        optedInButton.setPadding(horizontalPadding, 0, horizontalPadding, 0);
        optedInButton.setOnClickListener(v -> {
            if (optedInClickListener != null) {
                optedInClickListener.onOptedInClicked();
            }
        });
        
        // Create No Need button
        noNeedButton = new TextView(getContext());
        noNeedButton.setText("No Need");
        noNeedButton.setTextColor(0xFF4F4F4F);
        noNeedButton.setTextSize(14);
        noNeedButton.setOnClickListener(v -> {
            if (noNeedClickListener != null) {
                noNeedClickListener.onNoNeedClicked();
            }
        });
        
        // Create links container
        LinearLayout linksContainer = new LinearLayout(getContext());
        linksContainer.setOrientation(HORIZONTAL);
        linksContainer.setGravity(android.view.Gravity.CENTER); // Center elements in links container
        
        // Create privacy policy link
        privacyPolicyButton = new TextView(getContext());
        privacyPolicyButton.setText("Privacy Policy");
        privacyPolicyButton.setTextColor(0xFF555555);
        privacyPolicyButton.setTextSize(12);
        privacyPolicyButton.getPaint().setUnderlineText(true);
        privacyPolicyButton.setOnClickListener(v -> {
            if (privacyPolicyClickListener != null) {
                privacyPolicyClickListener.onPrivacyPolicyClicked();
            }
        });
        
        // Create terms of service link
        termsButton = new TextView(getContext());
        termsButton.setText("Terms of Service");
        termsButton.setTextColor(0xFF555555);
        termsButton.setTextSize(12);
        termsButton.getPaint().setUnderlineText(true);
        termsButton.setOnClickListener(v -> {
            if (termsClickListener != null) {
                termsClickListener.onTermsClicked();
            }
        });
        
        // Assemble views
        linksContainer.addView(privacyPolicyButton);
        linksContainer.addView(termsButton);
        
        addView(optedInButton);
        addView(noNeedButton);
        addView(linksContainer);
    }
    
    private void configViews() {
        // Set Opt-In button
        LinearLayout.LayoutParams optedInParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                DpPxUtils.dp(38)
        );
        optedInParams.setMargins(0, 0, 0, DpPxUtils.dp(12));
        optedInButton.setLayoutParams(optedInParams);
        
        // Set No Need button
        LinearLayout.LayoutParams noNeedParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        noNeedParams.setMargins(0, 0, 0, DpPxUtils.dp(30));
        noNeedButton.setLayoutParams(noNeedParams);
        
        // Set links container
        LinearLayout.LayoutParams linksParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linksParams.setMargins(0, 0, 0, 0);
        ((LinearLayout) getChildAt(2)).setLayoutParams(linksParams);
        
        // Set link spacing
        LinearLayout linksContainer = (LinearLayout) getChildAt(2);
        LinearLayout.LayoutParams privacyParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        privacyParams.setMargins(0, 0, DpPxUtils.dp(30), 0);
        privacyPolicyButton.setLayoutParams(privacyParams);
    }
    
    // Set callbacks
    public void setOptedInClickListener(OptedInClickListener listener) {
        this.optedInClickListener = listener;
    }
    
    public void setNoNeedClickListener(NoNeedClickListener listener) {
        this.noNeedClickListener = listener;
    }
    
    public void setPrivacyPolicyClickListener(PrivacyPolicyClickListener listener) {
        this.privacyPolicyClickListener = listener;
    }
    
    public void setTermsClickListener(TermsClickListener listener) {
        this.termsClickListener = listener;
    }
}




