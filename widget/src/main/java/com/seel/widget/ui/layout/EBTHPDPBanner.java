package com.seel.widget.ui.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

/**
 * EBTH-specific PDP banner layout with Seel logo and branded messaging.
 */
public class EBTHPDPBanner implements PDPBannerLayoutProvider {

    private final int backgroundColor;
    private final int paddingLeft;
    private final int paddingTop;
    private final int paddingRight;
    private final int paddingBottom;

    public EBTHPDPBanner(int backgroundColor, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
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

        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);

        ImageView logo = new ImageView(context);
        logo.setImageResource(R.mipmap.seel_icon);
        logo.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView label = new TextView(context);
        String text = "Worry-Free Purchase® available with seel";
        SpannableString spannable = new SpannableString(text);
        int seelStart = text.lastIndexOf("seel");
        spannable.setSpan(new StyleSpan(Typeface.BOLD), seelStart, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        label.setText(spannable);
        label.setTextSize(13);
        label.setTextColor(0xFF292728);

        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(DpPxUtils.dp(20), DpPxUtils.dp(20));
        logoParams.setMargins(0, 0, DpPxUtils.dp(6), 0);
        row.addView(logo, logoParams);
        row.addView(label, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        container.addView(row, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}
