package com.seel.widget.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

/**
 * Coverage Tips View
 */
public class CoverageTipsView extends LinearLayout {
    
    private TextView titleText;
    private TextView subtitleText;
    private TextView descriptionText;
    private TextView benefitsText;
    
    public CoverageTipsView(Context context) {
        super(context);
        init();
    }
    
    public CoverageTipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public CoverageTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        setOrientation(VERTICAL);
        createViews();
        configViews();
    }
    
    private void createViews() {
        // Create title
        titleText = new TextView(getContext());
        titleText.setText(R.string.seel_easy_resolution);
        titleText.setTextSize(16);
        titleText.setTextColor(0xFF1E2022);
        titleText.setTypeface(null, android.graphics.Typeface.BOLD);
        
        // Create subtitle
        subtitleText = new TextView(getContext());
        subtitleText.setText(R.string.seel_easy_resolution_detail);
        subtitleText.setTextSize(14);
        subtitleText.setTextColor(0xFF5C5F62);
        
        // Create description
        descriptionText = new TextView(getContext());
        descriptionText.setText(R.string.seel_complete_peace_of_mind);
        descriptionText.setTextSize(16);
        descriptionText.setTextColor(0xFF1E2022);
        descriptionText.setTypeface(null, android.graphics.Typeface.BOLD);
        
        // Create benefits list
        benefitsText = new TextView(getContext());
        benefitsText.setText(R.string.seel_benefits_list);
        benefitsText.setTextSize(14);
        benefitsText.setTextColor(0xFF5C5F62);

        // Add to layout
        addView(titleText);
        addView(subtitleText);
        addView(descriptionText);
        addView(benefitsText);
    }
    
    private void configViews() {
        // Set spacing
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.setMargins(0, 0, 0, DpPxUtils.dp(2));
        titleText.setLayoutParams(titleParams);
        
        LinearLayout.LayoutParams subtitleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        subtitleParams.setMargins(0, 0, 0, DpPxUtils.dp(20));
        subtitleText.setLayoutParams(subtitleParams);
        
        LinearLayout.LayoutParams descriptionParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        descriptionParams.setMargins(0, 0, 0, DpPxUtils.dp(2));
        descriptionText.setLayoutParams(descriptionParams);
        
        LinearLayout.LayoutParams benefitsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        benefitsParams.setMargins(0, 0, 0, 0);
        benefitsText.setLayoutParams(benefitsParams);
    }
}



