package com.seel.widget.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

/**
 * Seel WFP Title View
 */
public class SeelWFPTitleView extends LinearLayout {
    
    // Callback interface
    public interface InfoClickListener {
        void onInfoClicked();
    }
    
    // Callback
    private InfoClickListener infoClickListener;
    
    // Data
    private String title;
    private Double price;
    private Boolean loading;
    private boolean showInfo = false;
    private boolean showPowered = false;
    
    // UI Components
    private ImageView seelIcon;
    private TextView titleLabel;
    private TextView priceLabel;
    private LoadingAnimationView animationView;
    private ImageView infoButton;
    private TextView poweredLabel;
    private ImageView seelWordIcon;
    
    public SeelWFPTitleView(Context context) {
        super(context);
        init();
    }
    
    public SeelWFPTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public SeelWFPTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        
        // Create text container
        LinearLayout textContainer = new LinearLayout(getContext());
        textContainer.setOrientation(VERTICAL);
        textContainer.setPadding(DpPxUtils.dp(6), 0, 0, 0);
        
        // Create title row
        LinearLayout titleRow = new LinearLayout(getContext());
        titleRow.setOrientation(HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);
        
        // Create title label
        titleLabel = new TextView(getContext());
        titleLabel.setTextSize(12);
        titleLabel.setTextColor(getResources().getColor(android.R.color.black));
        titleLabel.setTypeface(null, Typeface.BOLD);
        
        // Create price label
        priceLabel = new TextView(getContext());
        priceLabel.setText(" for -");
        priceLabel.setTextSize(10);
        priceLabel.setTextColor(getResources().getColor(android.R.color.black));

        // Create animation view
        animationView = new LoadingAnimationView(getContext());
        
        // Create info button
        infoButton = new ImageView(getContext());
        infoButton.setImageResource(R.mipmap.info_black);
        infoButton.setOnClickListener(v -> {
            if (infoClickListener != null) {
                infoClickListener.onInfoClicked();
            }
        });
        
        // Create detail row
        LinearLayout detailRow = new LinearLayout(getContext());
        detailRow.setOrientation(HORIZONTAL);
        detailRow.setGravity(Gravity.CENTER_VERTICAL);

        // Create Powered by label
        poweredLabel = new TextView(getContext());
        poweredLabel.setText(R.string.powered_by);
        poweredLabel.setTextSize(7.5f);
        poweredLabel.setTextColor(0xFF565656);
        
        // Create Seel word icon
        seelWordIcon = new ImageView(getContext());
        seelWordIcon.setImageResource(R.mipmap.seel_word);
        
        // Assemble views
        titleRow.addView(titleLabel);
        titleRow.addView(priceLabel);
        titleRow.addView(animationView);
        titleRow.addView(infoButton);
        
        detailRow.addView(poweredLabel);
        detailRow.addView(seelWordIcon);
        
        textContainer.addView(titleRow);
        textContainer.addView(detailRow);
        
        addView(seelIcon);
        addView(textContainer);
    }
    
    private void configViews() {
        // Set icon size
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(DpPxUtils.dp(24), DpPxUtils.dp(24));
        seelIcon.setLayoutParams(iconParams);
        
        // Set animation view size
        LinearLayout.LayoutParams animationParams = new LinearLayout.LayoutParams(DpPxUtils.dp(30), DpPxUtils.dp(11));
        animationParams.leftMargin = DpPxUtils.dp(4);
        animationView.setLayoutParams(animationParams);
        
        // Set info button size - keep original image size
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(DpPxUtils.dp(12), DpPxUtils.dp(12));
        infoParams.leftMargin = DpPxUtils.dp(4);
        infoButton.setLayoutParams(infoParams);
        infoButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        
        // Set seelWordIcon height same as poweredLabel
        // poweredLabel font size is 7.5f, calculate corresponding height
        int textHeight = (int) (DpPxUtils.dp(7.5f) * 1.3f); // Font size * density * line height multiplier
        int iconWidth = (textHeight * 2); // Calculate appropriate width ratio based on height
        LinearLayout.LayoutParams seelWordParams = new LinearLayout.LayoutParams(iconWidth, textHeight);
        seelWordIcon.setLayoutParams(seelWordParams);
        seelWordIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        
        // Set text container weight
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
//        textContainer.setLayoutParams(textParams);
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // Set TouchDelegate after layout completion to expand infoButton touch area
        expandTouchArea(infoButton, 32); // Expand 32dp touch area
    }
    
    /**
     * Expand touch area for specified view
     * @param view View to expand touch area for
     * @param expandSize Expand size (dp)
     */
    private void expandTouchArea(final View view, int expandSize) {
        final View parent = (View) view.getParent();
        parent.post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.getHitRect(bounds);
                
                // Expand touch area
                int expandPx = DpPxUtils.dp(expandSize);
                bounds.left -= expandPx;
                bounds.top -= expandPx;
                bounds.right += expandPx;
                bounds.bottom += expandPx;
                
                parent.setTouchDelegate(new TouchDelegate(bounds, view));
            }
        });
    }
    
    /**
     * Update views
     */
    public void updateViews() {
        titleLabel.setText(title);

        boolean isLoading = loading != null && loading;
        animationView.setVisibility(isLoading ? VISIBLE : GONE);
        if (price != null) {
            if (isLoading) {
                animationView.startAnimating();
                priceLabel.setText(" for");
            } else  {
                animationView.stopAnimating();
                priceLabel.setText(" for $" + String.format("%.2f", price));
            }
            priceLabel.setVisibility(VISIBLE);
        } else {
            priceLabel.setVisibility(GONE);
        }
        
        // Update info button display
        infoButton.setVisibility(showInfo ? VISIBLE : GONE);
        
        // Update Powered by display
        LinearLayout detailRow = (LinearLayout) ((LinearLayout) getChildAt(1)).getChildAt(1);
        detailRow.setVisibility(showPowered ? VISIBLE : GONE);
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Boolean getLoading() { return loading; }
    public void setLoading(Boolean loading) { this.loading = loading; }
    
    public boolean isShowInfo() { return showInfo; }
    public void setShowInfo(boolean showInfo) { this.showInfo = showInfo; }
    
    public boolean isShowPowered() { return showPowered; }
    public void setShowPowered(boolean showPowered) { this.showPowered = showPowered; }
    
    public void setInfoClickListener(InfoClickListener listener) {
        this.infoClickListener = listener;
    }
}




