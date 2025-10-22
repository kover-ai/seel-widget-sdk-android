package com.seel.widget.ui;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

public class CoverageTitleView extends LinearLayout {
    // Data
    private String title;
    private Double price;

    // UI Components
    private ImageView seelIcon;
    private TextView titleLabel;
    private TextView priceLabel;

    public CoverageTitleView(Context context) {
        super(context);
        init();
    }

    public CoverageTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverageTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        createViews();
        configViews();
        updateViews();
    }

    private void createViews() {
        // Create Seel icon
        seelIcon = new ImageView(getContext());
        seelIcon.setImageResource(R.mipmap.seel_icon);

        // Create title row
        LinearLayout titleRow = new LinearLayout(getContext());
        titleRow.setOrientation(HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);

        // Create title label
        titleLabel = new TextView(getContext());
        titleLabel.setTextSize(20);
        titleLabel.setTextColor(getResources().getColor(android.R.color.black));
        titleLabel.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.setMargins(DpPxUtils.dp(8), 0, 0, 0);
        titleLabel.setLayoutParams(titleParams);

        // Create price label
        priceLabel = new TextView(getContext());
        priceLabel.setText("for -");
        priceLabel.setTextSize(14);
        priceLabel.setTextColor(getResources().getColor(android.R.color.black));
        
        // Set left margin for price label
        LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT);
        priceParams.setMargins(DpPxUtils.dp(6), 0, 0, 0);
        priceLabel.setLayoutParams(priceParams);

        // Assemble views
        titleRow.addView(titleLabel);
        titleRow.addView(priceLabel);

        addView(seelIcon);
        addView(titleRow);
    }

    private void configViews() {
        // Set icon size
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(DpPxUtils.dp(44), DpPxUtils.dp(44));
        seelIcon.setLayoutParams(iconParams);
    }

    /**
     * Update views
     */
    public void updateViews() {
        titleLabel.setText(title);
        // Update price display
        if (price != null) {
            priceLabel.setText("for $" + String.format("%.2f", price));
            priceLabel.setVisibility(VISIBLE);
        } else {
            priceLabel.setVisibility(GONE);
        }
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

}
