package com.seel.widget.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;
import android.widget.Button;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

/**
 * Seel Navigation Bar Component
 * Provides a consistent navigation bar with back button, title, and optional right button
 */
public class SeelNavigationBar extends RelativeLayout {
    
    // UI Components
    private Button backButton;
    private TextView titleLabel;
    private Button rightButton;
    private View divider;
    
    // Callback interfaces
    public interface BackButtonClickListener {
        void onBackButtonClicked();
    }
    
    public interface RightButtonClickListener {
        void onRightButtonClicked();
    }
    
    // Callbacks
    private BackButtonClickListener backButtonClickListener;
    private RightButtonClickListener rightButtonClickListener;
    
    public SeelNavigationBar(Context context) {
        super(context);
        init();
    }
    
    private void init() {
        // Set background
        setBackgroundColor(getResources().getColor(R.color.seel_nav_background));
        
        // Set padding
        setPadding(DpPxUtils.dp(16), DpPxUtils.dp(8), DpPxUtils.dp(16), DpPxUtils.dp(8));
        
        // Set minimum height
        setMinimumHeight(DpPxUtils.dp(44));
        
        // Add elevation for shadow
        setElevation(4);
        
        createViews();
        setupLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int fixedHeight = DpPxUtils.dp(44);
        int fixedHeightSpec = View.MeasureSpec.makeMeasureSpec(fixedHeight, View.MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, fixedHeightSpec);
        setMeasuredDimension(getMeasuredWidth(), fixedHeight);
    }
    
    private void createViews() {
        // Create back button
        backButton = new Button(getContext());
        backButton.setText(null);
        Drawable backDrawable = getResources().getDrawable(R.drawable.ic_arrow_left);
        backDrawable = DrawableCompat.wrap(backDrawable);
        DrawableCompat.setTint(backDrawable, Color.BLACK);
        // Ensure the icon fills the button by setting explicit bounds
        int backIconSize = DpPxUtils.dp(28);
        backDrawable.setBounds(0, 0, backIconSize, backIconSize);
        backButton.setCompoundDrawables(
                backDrawable, // scaled/tinted arrow icon
                null, null, null
        );
        backButton.setBackgroundColor(Color.TRANSPARENT);
        backButton.setOnClickListener(v -> {
            if (backButtonClickListener != null) {
                backButtonClickListener.onBackButtonClicked();
            }
        });
        
        // Create title label
        titleLabel = new TextView(getContext());
        titleLabel.setText("What's Covered by Seel");
        titleLabel.setTextSize(17);
        titleLabel.setTextColor(getResources().getColor(R.color.seel_nav_title));
        titleLabel.setGravity(android.view.Gravity.CENTER);
        titleLabel.setTypeface(null, android.graphics.Typeface.BOLD);
        titleLabel.setMaxLines(1);
        titleLabel.setEllipsize(android.text.TextUtils.TruncateAt.END);
        
        // Create right button (initially hidden)
        rightButton = new Button(getContext());
        rightButton.setText(null);
        Drawable closeDrawable = getResources().getDrawable(R.mipmap.button_close);
        closeDrawable = DrawableCompat.wrap(closeDrawable);
        DrawableCompat.setTint(closeDrawable, Color.BLACK);
        // Ensure the icon fills the button by setting explicit bounds
        int iconSize = DpPxUtils.dp(24);
        closeDrawable.setBounds(0, 0, iconSize, iconSize);
        rightButton.setCompoundDrawables(
                closeDrawable, // close icon
                null, null, null
        );
        rightButton.setBackgroundColor(Color.TRANSPARENT);
        rightButton.setMinWidth(DpPxUtils.dp(44));
        rightButton.setGravity(android.view.Gravity.CENTER);
        rightButton.setPadding(0, 0, 0, 0);
        rightButton.setOnClickListener(v -> {
            if (rightButtonClickListener != null) {
                rightButtonClickListener.onRightButtonClicked();
            }
        });
        rightButton.setVisibility(View.GONE); // Initially hidden
        
        // Create divider
        divider = new View(getContext());
        divider.setBackgroundColor(getResources().getColor(R.color.seel_nav_divider));
        divider.setVisibility(View.GONE);
        
        // Add views to layout
        addView(backButton);
        addView(titleLabel);
        addView(rightButton);
        addView(divider);
    }
    
    private void setupLayout() {
        // Back button layout params
        RelativeLayout.LayoutParams backParams = new RelativeLayout.LayoutParams(
                DpPxUtils.dp(20), 
                DpPxUtils.dp(20)
        );
        backParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        backParams.addRule(RelativeLayout.CENTER_VERTICAL);
        backButton.setLayoutParams(backParams);
        
        // Title label layout params - center in parent with margins to avoid overlap
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        // Add margins to prevent overlap with buttons
        titleParams.setMargins(DpPxUtils.dp(40), 0, DpPxUtils.dp(40), 0);
        titleLabel.setLayoutParams(titleParams);
        
        // Right button layout params
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(
                DpPxUtils.dp(24), 
                DpPxUtils.dp(24)
        );
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rightButton.setLayoutParams(rightParams);
        
        // Divider layout params
        RelativeLayout.LayoutParams dividerParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                1 // 1dp height
        );
        dividerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        divider.setLayoutParams(dividerParams);
    }
    
    // Public methods
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    public void setRightButtonVisible(boolean visible) {
        rightButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
    
    public void setBackButtonVisible(boolean visible) {
        backButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
    
    public void setBackButtonClickListener(BackButtonClickListener listener) {
        this.backButtonClickListener = listener;
    }
    
    public void setRightButtonClickListener(RightButtonClickListener listener) {
        this.rightButtonClickListener = listener;
    }
    
    public void setDividerVisible(boolean visible) {
        divider.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
