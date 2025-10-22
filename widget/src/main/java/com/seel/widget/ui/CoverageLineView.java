package com.seel.widget.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seel.widget.utils.DpPxUtils;

/**
 * Coverage Line View Component
 */
public class CoverageLineView extends LinearLayout {

    private ImageView iconImageView;
    private TextView contentTextView;
    private String content;
    private int iconImage;
    private int contentColor;

    public CoverageLineView(Context context) {
        super(context);
        init();
    }

    public CoverageLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverageLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(android.view.Gravity.CENTER_VERTICAL);
        
        // Create icon
        iconImageView = new ImageView(getContext());
        LayoutParams iconParams = new LayoutParams(DpPxUtils.dp(19), DpPxUtils.dp(21));
        iconParams.gravity = android.view.Gravity.CENTER_VERTICAL;
        iconImageView.setLayoutParams(iconParams);
        
        // Create content text
        contentTextView = new TextView(getContext());
        LayoutParams textParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        textParams.gravity = android.view.Gravity.CENTER_VERTICAL;
        textParams.leftMargin = DpPxUtils.dp(4);
        contentTextView.setLayoutParams(textParams);
        contentTextView.setTextSize(14);
        contentTextView.setTextColor(getResources().getColor(android.R.color.black));
        contentTextView.setGravity(android.view.Gravity.CENTER_VERTICAL);
        
        addView(iconImageView);
        addView(contentTextView);
    }
    
    /**
     * Update views
     */
    public void updateViews() {
        if (iconImage != 0) {
            iconImageView.setImageResource(iconImage);
            iconImageView.setVisibility(VISIBLE);
        } else {
            iconImageView.setVisibility(GONE);
        }
        
        if (content != null) {
            contentTextView.setText(content);
            contentTextView.setVisibility(VISIBLE);
        } else {
            contentTextView.setVisibility(GONE);
        }
        
        if (contentColor != 0) {
            contentTextView.setTextColor(contentColor);
        }
    }
    
    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public int getIconImage() { return iconImage; }
    public void setIconImage(int iconImage) { this.iconImage = iconImage; }
    
    public int getContentColor() { return contentColor; }
    public void setContentColor(int contentColor) { this.contentColor = contentColor; }
}




