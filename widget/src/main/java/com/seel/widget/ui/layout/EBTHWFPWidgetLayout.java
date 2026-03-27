package com.seel.widget.ui.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seel.widget.R;
import com.seel.widget.core.FormatMoney;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.ui.LoadingAnimationView;
import com.seel.widget.utils.DpPxUtils;

import java.util.List;

/**
 * EBTH-specific WFP widget layout.
 * Checkbox on the left, title + subtitle + disclaimer on the right.
 *
 * iOS constraints reference:
 *   rootContainer: edges = inset(16)
 *   checkboxButton: 44x44, left=-13 of rootContainer, centerY=titleRow
 *   textContainer:  left=checkboxButton.right-3, right=0, top=0
 *   disclaimerLabel: top=textContainer.bottom+10, left=textContainer
 */
public class EBTHWFPWidgetLayout implements WFPWidgetLayoutProvider {

    private static final int ID_TITLE_ROW = View.generateViewId();
    private static final int ID_CHECKBOX = View.generateViewId();
    private static final int ID_TEXT_CONTAINER = View.generateViewId();

    private WFPWidgetLayoutActions actions;

    private FrameLayout checkboxButton;
    private ImageView checkboxImage;
    private TextView titleLabel;
    private ImageView infoButton;
    private LoadingAnimationView priceLoadingView;
    private TextView subtitleLabel;
    private TextView disclaimerLabel;
    private LinearLayout textContainer;
    private LinearLayout titleRow;
    private LinearLayout rootContainer;

    private boolean isOn = false;
    private boolean isDisabled = false;

    @Override
    public WFPWidgetDefaults defaults() {
        WFPWidgetDefaults d = new WFPWidgetDefaults();
        d.showDisclaimer = false;
        return d;
    }

    @Override
    public void buildLayout(ViewGroup container, WFPWidgetLayoutActions actions) {
        this.actions = actions;
        Context context = container.getContext();

        rootContainer = new LinearLayout(context);
        rootContainer.setOrientation(LinearLayout.VERTICAL);

        // Use RelativeLayout for the top section so checkbox can centerY-align to titleRow
        RelativeLayout topSection = new RelativeLayout(context);

        // titleRow
        titleRow = new LinearLayout(context);
        titleRow.setId(ID_TITLE_ROW);
        titleRow.setOrientation(LinearLayout.HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);
        titleRow.setVisibility(View.GONE);

        titleLabel = new TextView(context);
        titleLabel.setMaxLines(1);

        infoButton = new ImageView(context);
        infoButton.setImageResource(R.mipmap.ebth_info);
        infoButton.setOnClickListener(v -> {
            if (this.actions != null) this.actions.getOnInfoTapped().run();
        });

        priceLoadingView = new LoadingAnimationView(context);
        priceLoadingView.setVisibility(View.GONE);

        titleRow.addView(titleLabel, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        titleRow.addView(priceLoadingView, new LinearLayout.LayoutParams(
                DpPxUtils.dp(36), DpPxUtils.dp(12)));
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(DpPxUtils.dp(16), DpPxUtils.dp(16));
        infoParams.setMargins(DpPxUtils.dp(4), 0, 0, 0);
        titleRow.addView(infoButton, infoParams);

        // subtitleLabel
        subtitleLabel = new TextView(context);
        subtitleLabel.setTextSize(12);
        subtitleLabel.setTextColor(0xFF676667);
        subtitleLabel.setVisibility(View.GONE);

        // textContainer: vertical stack of titleRow + subtitleLabel
        textContainer = new LinearLayout(context);
        textContainer.setId(ID_TEXT_CONTAINER);
        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setVisibility(View.GONE);

        LinearLayout.LayoutParams subtitleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        subtitleParams.topMargin = DpPxUtils.dp(2);
        textContainer.addView(titleRow);
        textContainer.addView(subtitleLabel, subtitleParams);

        // Checkbox: 44dp touch area, 18dp image centered
        checkboxButton = new FrameLayout(context);
        checkboxButton.setId(ID_CHECKBOX);
        checkboxImage = new ImageView(context);
        checkboxImage.setImageResource(R.mipmap.ebth_checkbox_normal);
        checkboxImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(DpPxUtils.dp(18), DpPxUtils.dp(18));
        imgParams.gravity = Gravity.CENTER;
        checkboxButton.addView(checkboxImage, imgParams);
        checkboxButton.setOnClickListener(v -> {
            if (!isDisabled && this.actions != null) {
                this.actions.getOnToggleChanged().onChanged(!isOn);
            }
        });

        container.setOnClickListener(v -> {
            if (isDisabled && this.actions != null) {
                this.actions.getOnDisabledTapped().run();
            }
        });

        // --- Add views to RelativeLayout ---

        // textContainer: top=0, left=28dp (= -13 + 44 - 3), right=0
        RelativeLayout.LayoutParams textRlParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textRlParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        textRlParams.leftMargin = DpPxUtils.dp(28);
        topSection.addView(textContainer, textRlParams);

        // checkboxButton: 44x44, left=-13, centerY aligned to titleRow
        // We use ALIGN_TOP + ALIGN_BOTTOM on titleRow so the checkbox area spans titleRow's height,
        // then the 44dp FrameLayout with centered 18dp image naturally centers the icon.
        // But we actually want the checkbox to be 44x44 and its center to match titleRow's center.
        // So we position it relative to titleRow using a custom approach:
        // Place checkbox aligned to titleRow's top, then adjust with negative margin.
        RelativeLayout.LayoutParams cbRlParams = new RelativeLayout.LayoutParams(
                DpPxUtils.dp(44), DpPxUtils.dp(44));
        cbRlParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        cbRlParams.leftMargin = DpPxUtils.dp(-13);
        topSection.addView(checkboxButton, cbRlParams);

        rootContainer.addView(topSection, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // disclaimerLabel
        disclaimerLabel = new TextView(context);
        disclaimerLabel.setTextSize(12);
        disclaimerLabel.setTextColor(0xFF676667);
        disclaimerLabel.setVisibility(View.GONE);

        LinearLayout.LayoutParams disclaimerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        disclaimerParams.setMargins(DpPxUtils.dp(28), DpPxUtils.dp(10), 0, 0);
        rootContainer.addView(disclaimerLabel, disclaimerParams);

        int p = DpPxUtils.dp(16);
        container.setPadding(p, p, p, p);
        container.setClipToPadding(false);

        container.addView(rootContainer, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void updateLayout(ViewGroup container, WFPWidgetLayoutData data) {
        QuotesResponse quoteResponse = data.getQuoteResponse();
        boolean displayView = quoteResponse != null;

        container.setVisibility(displayView ? View.VISIBLE : View.GONE);
        if (displayView) {
            int p = DpPxUtils.dp(16);
            container.setPadding(p, p, p, p);
        } else {
            container.setPadding(0, 0, 0, 0);
        }

        if (!displayView) {
            textContainer.setVisibility(View.GONE);
            titleRow.setVisibility(View.GONE);
            checkboxButton.setVisibility(View.GONE);
            disclaimerLabel.setVisibility(View.GONE);
            return;
        }

        textContainer.setVisibility(View.VISIBLE);
        titleRow.setVisibility(View.VISIBLE);
        checkboxButton.setVisibility(View.VISIBLE);

        boolean isRejected = quoteResponse.isRejected();
        boolean isChecked = data.isToggleOn() && !isRejected;
        boolean isLoading = data.isLoading();

        isOn = isChecked;
        isDisabled = isRejected;
        updateCheckboxState();

        if (isRejected) {
            Integer disabledBg = data.getDisabledBackgroundColor();
            container.setBackgroundColor(disabledBg != null ? disabledBg : 0xFFF0EFEF);
        } else if (isChecked) {
            Integer selectedBg = data.getSelectedBackgroundColor();
            container.setBackgroundColor(selectedBg != null ? selectedBg : 0xFFFFFFFF);
        } else {
            Integer normalBg = data.getNormalBackgroundColor();
            container.setBackgroundColor(normalBg != null ? normalBg : 0xFFFFFFFF);
        }
        container.setAlpha(1.0f);

        int titleColor = isRejected ? 0xFF676667 : 0xFF292728;

        if (isRejected) {
            String widgetTitle = quoteResponse.getExtraInfo() != null
                    ? quoteResponse.getExtraInfo().getWidgetTitle() : "";
            titleLabel.setText(widgetTitle);
            titleLabel.setTextSize(14);
            setFontWeight(titleLabel, 600);
            titleLabel.setTextColor(titleColor);
            priceLoadingView.setVisibility(View.GONE);
            priceLoadingView.stopAnimating();
        } else if (isLoading) {
            String title = quoteResponse.getExtraInfo() != null
                    ? quoteResponse.getExtraInfo().getWidgetTitle() : "";
            titleLabel.setText(title);
            titleLabel.setTextSize(14);
            setFontWeight(titleLabel, 600);
            titleLabel.setTextColor(titleColor);
            priceLoadingView.setVisibility(View.VISIBLE);
            priceLoadingView.startAnimating();
        } else {
            String title = quoteResponse.getExtraInfo() != null
                    ? quoteResponse.getExtraInfo().getWidgetTitle() : "";
            Double price = quoteResponse.getPrice();
            String priceText = price != null
                    ? " for " + FormatMoney.formatMoney(price, quoteResponse.getCurrency()) : "";
            String full = title + priceText;

            SpannableString spannable = new SpannableString(full);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                spannable.setSpan(new android.text.style.TypefaceSpan(Typeface.create(null, 600, false)),
                        0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new android.text.style.TypefaceSpan(Typeface.create(null, 400, false)),
                        title.length(), full.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.NORMAL), title.length(), full.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            spannable.setSpan(new ForegroundColorSpan(titleColor), 0, full.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            titleLabel.setTypeface(null, Typeface.NORMAL);
            titleLabel.setText(spannable);
            titleLabel.setTextSize(14);
            priceLoadingView.setVisibility(View.GONE);
            priceLoadingView.stopAnimating();
        }

        infoButton.setVisibility(isRejected ? View.GONE : View.VISIBLE);

        // Subtitle
        List<String> msgs = quoteResponse.getExtraInfo() != null
                ? quoteResponse.getExtraInfo().getDisplayWidgetText() : null;
        if (msgs != null && !msgs.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < msgs.size(); i++) {
                if (i > 0) sb.append("\n");
                sb.append(msgs.get(i));
            }
            subtitleLabel.setText(sb.toString());
            subtitleLabel.setVisibility(View.VISIBLE);
        } else {
            subtitleLabel.setVisibility(View.GONE);
        }

        // Disclaimer — hidden when showDisclaimer is false or rejected
        String disclaimer = quoteResponse.getExtraInfo() != null
                ? quoteResponse.getExtraInfo().getWidgetDisclaimer() : null;
        if (data.isShowDisclaimer() && !isRejected && disclaimer != null && !disclaimer.isEmpty()) {
            disclaimerLabel.setText(disclaimer);
            disclaimerLabel.setVisibility(View.VISIBLE);
        } else {
            disclaimerLabel.setVisibility(View.GONE);
        }

        // After layout, vertically center checkbox to titleRow
        titleRow.post(() -> alignCheckboxToTitleRow());
    }

    /**
     * Dynamically positions the checkbox so its vertical center aligns with titleRow's vertical center.
     * iOS: checkboxButton.centerY.equalTo(titleRow)
     */
    private void alignCheckboxToTitleRow() {
        int titleRowTop = titleRow.getTop() + textContainer.getTop();
        int titleRowHeight = titleRow.getHeight();
        int cbHeight = DpPxUtils.dp(44);
        int targetTop = titleRowTop + (titleRowHeight - cbHeight) / 2;

        ViewGroup.MarginLayoutParams cbLp = (ViewGroup.MarginLayoutParams) checkboxButton.getLayoutParams();
        cbLp.topMargin = targetTop;
        checkboxButton.setLayoutParams(cbLp);
    }

    private static void setFontWeight(TextView tv, int weight) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            tv.setTypeface(Typeface.create(null, weight, false));
        } else {
            tv.setTypeface(null, weight >= 600 ? Typeface.BOLD : Typeface.NORMAL);
        }
    }

    private void updateCheckboxState() {
        if (isDisabled) {
            checkboxImage.setImageResource(R.mipmap.ebth_checkbox_disabled);
            checkboxButton.setEnabled(false);
        } else if (isOn) {
            checkboxImage.setImageResource(R.mipmap.ebth_checkbox_selected);
            checkboxButton.setEnabled(true);
        } else {
            checkboxImage.setImageResource(R.mipmap.ebth_checkbox_normal);
            checkboxButton.setEnabled(true);
        }
        checkboxButton.setContentDescription(
                checkboxButton.getContext().getString(isOn ? R.string.seel_a11y_selected : R.string.seel_a11y_unselected));
    }
}
