package com.seel.widget.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.seel.widget.models.QuotesResponse;
import com.seel.widget.utils.DpPxUtils;

/**
 * Tooltip view shown when the widget is in disabled/rejected state.
 * Displays reasons why coverage is unavailable.
 */
public class SeelTooltipView extends FrameLayout {

    private static SeelTooltipView currentTooltip;

    private final TextView contentLabel;

    public SeelTooltipView(Context context) {
        super(context);

        setBackgroundColor(Color.TRANSPARENT);
        setClickable(true);
        setOnClickListener(v -> dismiss());

        FrameLayout cardView = new FrameLayout(context);
        GradientDrawable cardBg = new GradientDrawable();
        cardBg.setColor(Color.WHITE);
        cardBg.setCornerRadius(DpPxUtils.dp(8));
        cardView.setBackground(cardBg);
        cardView.setElevation(DpPxUtils.dp(8));

        int cardPadding = DpPxUtils.dp(16);
        cardView.setPadding(cardPadding, cardPadding, cardPadding, cardPadding);

        contentLabel = new TextView(context);
        contentLabel.setTextSize(12);
        contentLabel.setTextColor(0xFF202223);
        cardView.addView(contentLabel, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        int margin = DpPxUtils.dp(20);
        FrameLayout.LayoutParams cardParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        cardParams.gravity = Gravity.CENTER;
        cardParams.setMargins(margin, margin, margin, margin);
        addView(cardView, cardParams);
    }

    private void dismiss() {
        animate().alpha(0f).setDuration(200).withEndAction(() -> {
            ViewGroup parent = (ViewGroup) getParent();
            if (parent != null) {
                parent.removeView(this);
            }
        }).start();
    }

    public static void show(Context context, View anchorView, QuotesResponse quoteResponse) {
        if (!(context instanceof Activity)) return;
        Activity activity = (Activity) context;
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

        if (currentTooltip != null && currentTooltip.getParent() != null) {
            ((ViewGroup) currentTooltip.getParent()).removeView(currentTooltip);
        }

        SeelTooltipView tooltip = new SeelTooltipView(context);
        currentTooltip = tooltip;
        tooltip.setAlpha(0f);
        tooltip.contentLabel.setText(buildTooltipText());

        decorView.addView(tooltip, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        tooltip.animate().alpha(1f).setDuration(200).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (tooltip.getParent() != null) {
                tooltip.dismiss();
            }
        }, 8000);
    }

    private static CharSequence buildTooltipText() {
        String[] reasons = {
                "Shipping destination not supported",
                "Checkout currency not accepted",
                "Order value exceeds our coverage limit",
                "Item(s) not eligible for this service",
                "Our system has flagged this order as ineligible"
        };

        StringBuilder sb = new StringBuilder();
        sb.append("We're unable to offer Worry-Free Purchase\u00AE Protection for this order. This could be due to one or more of the following reasons:\n\n");
        for (String reason : reasons) {
            sb.append("  \u2022  ").append(reason).append("\n");
        }
        sb.append("\nIf you have any questions, please contact our customer support team for assistance.");
        return sb.toString();
    }
}
