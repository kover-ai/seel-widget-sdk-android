package com.seel.widget.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

/**
 * Line View Component
 */
public class LineView extends LinearLayout {
    
    private ImageView iconImageView;
    private TextView contentTextView;
    private String content;
    private int iconImage;
    private int contentColor;
    
    public LineView(Context context) {
        super(context);
        init();
    }
    
    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        setOrientation(HORIZONTAL);
        
        // Create icon
        iconImageView = new ImageView(getContext());
        iconImageView.setLayoutParams(new LayoutParams(DpPxUtils.dp(12), DpPxUtils.dp(12)));
        
        // Create content text
        contentTextView = new TextView(getContext());
        contentTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        contentTextView.setTextSize(12);
        contentTextView.setTextColor(getResources().getColor(android.R.color.black));
        
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




