package com.seel.widget.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

/**
 * Checkbox toggle control for the WFP widget.
 * Supports normal/selected states with custom images.
 */
public class SeelCheckbox extends FrameLayout {

    public interface OnValueChangedListener {
        void onValueChanged(boolean isOn);
    }

    private boolean isOn = false;
    private OnValueChangedListener onValueChangedListener;
    private ImageView imageView;

    public SeelCheckbox(Context context) {
        super(context);
        init();
    }

    public SeelCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeelCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int size = DpPxUtils.dp(44);

        imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(DpPxUtils.dp(24), DpPxUtils.dp(24));
        imgParams.gravity = android.view.Gravity.CENTER;
        addView(imageView, imgParams);

        setOnClickListener(v -> {
            isOn = !isOn;
            updateState();
            if (onValueChangedListener != null) {
                onValueChangedListener.onValueChanged(isOn);
            }
        });

        updateState();
    }

    private void updateState() {
        if (isOn) {
            imageView.setImageResource(R.mipmap.checkbox_selected);
        } else {
            imageView.setImageResource(R.mipmap.checkbox_normal);
        }
        setContentDescription(getContext().getString(isOn ? R.string.seel_a11y_selected : R.string.seel_a11y_unselected));
    }

    public boolean isOn() { return isOn; }

    public void setOn(boolean on) {
        if (this.isOn != on) {
            this.isOn = on;
            updateState();
        }
    }

    public void setOnValueChangedListener(OnValueChangedListener listener) {
        this.onValueChangedListener = listener;
    }
}
