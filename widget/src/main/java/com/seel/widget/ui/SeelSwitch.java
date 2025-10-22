package com.seel.widget.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seel.widget.utils.DpPxUtils;

/**
 * SeelSwitch
 * Custom switch component with smooth animations
 */
public class SeelSwitch extends ViewGroup {
    
    private boolean isOn = false;
    private int onTintColor = Color.parseColor("#2121C4");
    private int thumbTintColor = Color.WHITE;
    private int trackTintColor = Color.LTGRAY;
    
    private OnValueChangedListener onValueChangedListener;
    
    // Track (background)
    private View trackView;
    // Thumb (sliding circle)
    private View thumbView;
    
    // Animation properties
    private ValueAnimator animator;
    private int thumbStartX;
    private int thumbEndX;
    
    public interface OnValueChangedListener {
        void onValueChanged(boolean isOn);
    }
    
    public SeelSwitch(Context context) {
        super(context);
        init();
    }
    
    public SeelSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public SeelSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        setupUI();
        updateSwitchState();
        setClickable(true);
        setFocusable(true);
    }
    
    private void setupUI() {
        // Create track view
        trackView = new View(getContext());
        trackView.setBackground(createTrackDrawable());
        addView(trackView);
        
        // Create thumb view
        thumbView = new View(getContext());
        thumbView.setBackground(createThumbDrawable());
        addView(thumbView);
        
        // Set initial size (scaled to 0.6x)
        setMinimumWidth(DpPxUtils.dp(30));
        setMinimumHeight(DpPxUtils.dp(18));
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = Math.max(DpPxUtils.dp(30), getSuggestedMinimumWidth());
        int height = DpPxUtils.dp(18);
        
        setMeasuredDimension(width, height);
        
        // Measure track
        int trackWidth = width;
        int trackHeight = height;
        trackView.measure(
            MeasureSpec.makeMeasureSpec(trackWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(trackHeight, MeasureSpec.EXACTLY)
        );
        
        // Measure thumb (scaled to 0.6x)
        int thumbSize = DpPxUtils.dp(14);
        thumbView.measure(
            MeasureSpec.makeMeasureSpec(thumbSize, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(thumbSize, MeasureSpec.EXACTLY)
        );
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = getWidth();
        int height = getHeight();
        
        // Layout track
        trackView.layout(0, 0, width, height);
        
        // Layout thumb (scaled to 0.6x)
        int thumbSize = DpPxUtils.dp(14);
        int thumbY = (height - thumbSize) / 2;
        
        thumbStartX = DpPxUtils.dp(2);
        thumbEndX = width - thumbSize - DpPxUtils.dp(2);
        
        int thumbX = isOn ? thumbEndX : thumbStartX;
        thumbView.layout(thumbX, thumbY, thumbX + thumbSize, thumbY + thumbSize);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Provide visual feedback when pressed
                setAlpha(0.8f);
                return true;
            case MotionEvent.ACTION_UP:
                // Toggle state when released
                setAlpha(1.0f);
                toggle();
                return true;
            case MotionEvent.ACTION_CANCEL:
                // Restore state when cancelled
                setAlpha(1.0f);
                return true;
        }
        return super.onTouchEvent(event);
    }
    
    public void toggle() {
        setOn(!isOn, true, true);
    }
    
    public void setOn(boolean on, boolean animated, boolean onChanged) {
        if (this.isOn != on) {
            this.isOn = on;
            if (animated) {
                animateThumb();
            } else {
                updateSwitchState();
            }
            if (onChanged && onValueChangedListener != null) {
                onValueChangedListener.onValueChanged(isOn);
            }
        }
    }
    
    public void setOn(boolean on) {
        setOn(on, true, false);
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public void setOnValueChangedListener(OnValueChangedListener listener) {
        this.onValueChangedListener = listener;
    }
    
    public void setOnTintColor(int color) {
        this.onTintColor = color;
        updateColors();
    }
    
    public void setThumbTintColor(int color) {
        this.thumbTintColor = color;
        updateColors();
    }
    
    public void setTrackTintColor(int color) {
        this.trackTintColor = color;
        updateColors();
    }
    
    private void updateSwitchState() {
        if (isOn) {
            trackView.setBackground(createTrackDrawable(onTintColor));
        } else {
            trackView.setBackground(createTrackDrawable(trackTintColor));
        }
        requestLayout();
    }
    
    private void updateColors() {
        updateSwitchState();
        thumbView.setBackground(createThumbDrawable());
    }
    
    private void animateThumb() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        
        int startX = isOn ? thumbStartX : thumbEndX;
        int endX = isOn ? thumbEndX : thumbStartX;
        
        animator = ValueAnimator.ofInt(startX, endX);
        animator.setDuration(200);
        animator.addUpdateListener(animation -> {
            int currentX = (int) animation.getAnimatedValue();
            int thumbSize = DpPxUtils.dp(14);
            int thumbY = (getHeight() - thumbSize) / 2;
            thumbView.layout(currentX, thumbY, currentX + thumbSize, thumbY + thumbSize);
        });
        
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                updateSwitchState();
            }
        });
        
        animator.start();
    }
    
    private GradientDrawable createTrackDrawable() {
        return createTrackDrawable(isOn ? onTintColor : trackTintColor);
    }
    
    private GradientDrawable createTrackDrawable(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(DpPxUtils.dp(15));
        drawable.setColor(color);
        return drawable;
    }
    
    private GradientDrawable createThumbDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(thumbTintColor);
        return drawable;
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }
}
