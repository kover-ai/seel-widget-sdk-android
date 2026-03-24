package com.seel.widget.ui.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seel.widget.utils.DpPxUtils;

/**
 * Default PDP banner layout — generic "Worry-Free Purchase® available" message.
 */
public class DefaultPDPBanner implements PDPBannerLayoutProvider {

    private final int backgroundColor;
    private final int paddingLeft;
    private final int paddingTop;
    private final int paddingRight;
    private final int paddingBottom;

    public DefaultPDPBanner(int backgroundColor, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.backgroundColor = backgroundColor;
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;
    }

    @Override
    public void buildLayout(ViewGroup container) {
        Context context = container.getContext();
        container.setBackgroundColor(backgroundColor);
        container.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        TextView label = new TextView(context);
        String text = "Worry-Free Purchase® available with Seel";
        SpannableString spannable = new SpannableString(text);
        int seelStart = text.indexOf("Seel");
        spannable.setSpan(new StyleSpan(Typeface.BOLD), seelStart, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(0xFF2121C4), seelStart, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        label.setText(spannable);
        label.setTextSize(13);
        label.setTextColor(0xFF333333);

        container.addView(label, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}
