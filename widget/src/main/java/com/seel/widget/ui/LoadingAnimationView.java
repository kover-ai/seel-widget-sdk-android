package com.seel.widget.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class LoadingAnimationView extends View {

    private ValueAnimator animator;
    private Paint paint;
    private float gradientOffset = 0f;
    private int[] colors = {0xFFF0F0F0, 0xFFE0E0E0, 0xFFF0F0F0};
    private float cornerRadius = 4f;

    public LoadingAnimationView(Context context) {
        super(context);
        init();
    }

    public LoadingAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setupGradient();
    }

    private void setupGradient() {
        // Create initial gradient
        updateGradientShader();
    }

    private void updateGradientShader() {
        if (getWidth() > 0) {
            // Create gradient that moves with animation
            LinearGradient gradient = new LinearGradient(
                    -getWidth() + gradientOffset * getWidth() * 2, 0,
                    getWidth() + gradientOffset * getWidth() * 2, 0,
                    colors, null, Shader.TileMode.CLAMP
            );
            paint.setShader(gradient);
        }
    }

    // Start loading animation
    public void startAnimating() {
        stopAnimating();

        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                gradientOffset = (float) animation.getAnimatedValue();
                updateGradientShader();
                invalidate();
            }
        });
        animator.start();
    }

    // Stop loading animation
    public void stopAnimating() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        gradientOffset = 0f;
        updateGradientShader();
        invalidate();
    }

    // Check if animation is running
    public boolean isAnimating() {
        return animator != null && animator.isRunning();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw rounded rectangle with gradient
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateGradientShader();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimating();
    }
}
