package com.seel.widget.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.seel.widget.ui.layout.PDPBannerLayoutFactory;
import com.seel.widget.ui.layout.PDPBannerLayoutProvider;

/**
 * A lightweight banner view for the Product Detail Page.
 * Displays brand-specific messaging (e.g. "Worry-Free Purchase® available with seel").
 * Hidden by default; shows content when a matching brand type is provided.
 */
public class SeelPDPBannerView extends LinearLayout {

    /**
     * Style configuration for the PDP banner.
     */
    public static class PDPBannerStyle {
        public int backgroundColor = Color.WHITE;
        public int paddingLeft = 0;
        public int paddingTop = 0;
        public int paddingRight = 0;
        public int paddingBottom = 0;
        public float cornerRadius = 0;
        public Integer borderColor = null;
        public float borderWidth = 0;

        public PDPBannerStyle() {}
    }

    private PDPBannerLayoutProvider layoutProvider;

    public SeelPDPBannerView(Context context) {
        super(context);
        init();
    }

    public SeelPDPBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeelPDPBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setVisibility(View.GONE);
    }

    /**
     * Configure the banner for a specific brand type.
     *
     * @param type  The brand identifier (e.g. "ebth-wfp"). Pass null to hide.
     * @param style Visual style configuration.
     */
    public void setup(String type, PDPBannerStyle style) {
        removeAllViews();

        if (style == null) style = new PDPBannerStyle();

        layoutProvider = PDPBannerLayoutFactory.provider(
                type, style.backgroundColor,
                style.paddingLeft, style.paddingTop,
                style.paddingRight, style.paddingBottom
        );
        layoutProvider.buildLayout(this);

        if (style.cornerRadius > 0) {
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(style.backgroundColor);
            bg.setCornerRadius(style.cornerRadius);
            if (style.borderColor != null) {
                bg.setStroke((int) style.borderWidth, style.borderColor);
            }
            setBackground(bg);
            setClipToOutline(true);
        }
        setVisibility(type != null ? View.VISIBLE : View.GONE);
    }

    /**
     * Configure the banner with default style.
     */
    public void setup(String type) {
        setup(type, new PDPBannerStyle());
    }
}
