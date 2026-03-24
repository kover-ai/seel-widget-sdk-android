package com.seel.widget.ui.layout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.seel.widget.R;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.utils.DpPxUtils;

import java.util.List;
import java.util.Locale;

/**
 * EBTH-specific WFP info modal layout.
 * Bottom sheet with background image header, white card overlay,
 * coverage cards, feature cards, and full-width CTA button.
 * Matches iOS EBTHWFPInfoLayout.swift exactly.
 */
public class EBTHWFPInfoLayout implements WFPInfoLayoutProvider {

    @Override
    public void buildLayout(Activity activity, QuotesResponse quoteResponse, WFPInfoLayoutActions actions) {
        // Root: dim overlay
        FrameLayout rootContainer = new FrameLayout(activity);
        rootContainer.setBackgroundColor(0x66000000);
        rootContainer.setOnClickListener(v -> actions.getOnClose().run());

        // Bottom sheet container with rounded top corners
        LinearLayout sheetContainer = new LinearLayout(activity);
        sheetContainer.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable sheetBg = new GradientDrawable();
        sheetBg.setColor(Color.WHITE);
        float r = DpPxUtils.dp(16);
        sheetBg.setCornerRadii(new float[]{r, r, r, r, 0, 0, 0, 0});
        sheetContainer.setBackground(sheetBg);
        sheetContainer.setClickable(true);
        sheetContainer.setClipToOutline(true);

        ScrollView scrollView = new ScrollView(activity);
        scrollView.setVerticalScrollBarEnabled(true);
        scrollView.setClipToPadding(false);

        LinearLayout contentView = new LinearLayout(activity);
        contentView.setOrientation(LinearLayout.VERTICAL);

        // ===== HEADER (background image + blur overlay + logo + close + titles) =====
        int headerHeight = Math.max(DpPxUtils.dp(180),
                (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.20));

        FrameLayout headerContainer = new FrameLayout(activity);

        // Background image (flipped horizontally like iOS)
        ImageView backgroundImageView = new ImageView(activity);
        backgroundImageView.setImageResource(R.mipmap.background_image);
        backgroundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        backgroundImageView.setScaleX(-1f);
        backgroundImageView.setBackgroundColor(0xFF3A3A5C);
        headerContainer.addView(backgroundImageView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // Semi-transparent overlay (simulates iOS blur)
        View headerOverlay = new View(activity);
        headerOverlay.setBackgroundColor(0x4DFFFFFF);
        headerContainer.addView(headerOverlay, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // Seel logo (top-left)
        ImageView seelLogoIcon = new ImageView(activity);
        seelLogoIcon.setImageResource(R.mipmap.seel_logo);
        seelLogoIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        FrameLayout.LayoutParams logoParams = new FrameLayout.LayoutParams(DpPxUtils.dp(78), DpPxUtils.dp(22));
        logoParams.setMargins(DpPxUtils.dp(24), DpPxUtils.dp(24), 0, 0);
        headerContainer.addView(seelLogoIcon, logoParams);

        // Close button (top-right)
        ImageView closeButton = new ImageView(activity);
        closeButton.setImageResource(R.mipmap.close_white);
        closeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        closeButton.setOnClickListener(v -> actions.getOnClose().run());
        FrameLayout.LayoutParams closeParams = new FrameLayout.LayoutParams(DpPxUtils.dp(26), DpPxUtils.dp(26));
        closeParams.gravity = Gravity.END;
        closeParams.setMargins(0, DpPxUtils.dp(24), DpPxUtils.dp(24), 0);
        headerContainer.addView(closeButton, closeParams);

        // Header title: "We've Got You Covered"
        TextView headerTitleLabel = new TextView(activity);
        headerTitleLabel.setText("We've Got You Covered");
        headerTitleLabel.setTextSize(20);
        headerTitleLabel.setTypeface(Typeface.create("sans-serif-black", Typeface.BOLD));
        headerTitleLabel.setTextColor(Color.WHITE);
        // iOS: top = seelLogoIcon.bottom + 24 = (24+22) + 24 = 70
        FrameLayout.LayoutParams headerTitleParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        headerTitleParams.setMargins(DpPxUtils.dp(24), DpPxUtils.dp(70), DpPxUtils.dp(24), 0);
        headerContainer.addView(headerTitleLabel, headerTitleParams);

        // Header subtitle: "Only $X.XX for Complete Peace of Mind"
        TextView headerSubtitleLabel = new TextView(activity);
        Double price = quoteResponse != null ? quoteResponse.getPrice() : null;
        if (price != null) {
            headerSubtitleLabel.setText(String.format(Locale.US, "Only $%.2f for Complete Peace of Mind", price));
        }
        headerSubtitleLabel.setTextSize(16);
        headerSubtitleLabel.setTypeface(null, Typeface.NORMAL);
        headerSubtitleLabel.setTextColor(Color.WHITE);
        // iOS: top = headerTitleLabel.bottom + 24
        // Approximate: title at 70, single line ~24dp height, so subtitle at ~118
        FrameLayout.LayoutParams headerSubParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        headerSubParams.setMargins(DpPxUtils.dp(24), DpPxUtils.dp(118), DpPxUtils.dp(24), 0);
        headerContainer.addView(headerSubtitleLabel, headerSubParams);

        contentView.addView(headerContainer, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, headerHeight));

        // ===== WHITE CARD (overlaps header by 20dp, rounded top corners) =====
        LinearLayout whiteCard = new LinearLayout(activity);
        whiteCard.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable whiteCardBg = new GradientDrawable();
        whiteCardBg.setColor(Color.WHITE);
        float cr = DpPxUtils.dp(10);
        whiteCardBg.setCornerRadii(new float[]{cr, cr, cr, cr, 0, 0, 0, 0});
        whiteCard.setBackground(whiteCardBg);
        LinearLayout.LayoutParams whiteCardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        whiteCardParams.topMargin = DpPxUtils.dp(-20);
        contentView.addView(whiteCard, whiteCardParams);

        // ===== WFP Title (centered) =====
        TextView wfpTitleLabel = new TextView(activity);
        String widgetTitle = quoteResponse != null && quoteResponse.getExtraInfo() != null
                ? quoteResponse.getExtraInfo().getWidgetTitle() : "";
        wfpTitleLabel.setText(widgetTitle != null ? widgetTitle : "");
        wfpTitleLabel.setTextSize(20);
        wfpTitleLabel.setTypeface(null, Typeface.BOLD);
        wfpTitleLabel.setTextColor(0xFF1E2022);
        wfpTitleLabel.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams wfpTitleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wfpTitleParams.setMargins(DpPxUtils.dp(24), DpPxUtils.dp(24), DpPxUtils.dp(24), 0);
        whiteCard.addView(wfpTitleLabel, wfpTitleParams);

        // ===== Coverage Card =====
        LinearLayout coverageCard = new LinearLayout(activity);
        coverageCard.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable cardBg = new GradientDrawable();
        cardBg.setColor(0xFFF8F9FF);
        cardBg.setCornerRadius(DpPxUtils.dp(10));
        coverageCard.setBackground(cardBg);
        int cardPadding = DpPxUtils.dp(20);
        coverageCard.setPadding(cardPadding, cardPadding, cardPadding, cardPadding);

        // Coverage header: accredited icon + "What's Covered"
        LinearLayout coverageHeader = new LinearLayout(activity);
        coverageHeader.setOrientation(LinearLayout.HORIZONTAL);
        coverageHeader.setGravity(Gravity.CENTER_VERTICAL);

        ImageView shieldIcon = new ImageView(activity);
        shieldIcon.setImageResource(R.mipmap.accredited);
        shieldIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView coverageHeaderLabel = new TextView(activity);
        coverageHeaderLabel.setText("What's Covered");
        coverageHeaderLabel.setTextSize(16);
        coverageHeaderLabel.setTypeface(null, Typeface.BOLD);
        coverageHeaderLabel.setTextColor(0xFF000000);
        LinearLayout.LayoutParams coverageLabelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        coverageLabelParams.setMargins(DpPxUtils.dp(8), 0, 0, 0);

        coverageHeader.addView(shieldIcon, new LinearLayout.LayoutParams(DpPxUtils.dp(32), DpPxUtils.dp(32)));
        coverageHeader.addView(coverageHeaderLabel, coverageLabelParams);
        coverageCard.addView(coverageHeader);

        // Coverage items
        List<String> coverageTexts = quoteResponse != null && quoteResponse.getExtraInfo() != null
                ? quoteResponse.getExtraInfo().getCoverageDetailsText() : null;
        if (coverageTexts != null) {
            for (String text : coverageTexts) {
                View itemView = buildCoverageItem(activity, text);
                LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                itemParams.setMargins(0, DpPxUtils.dp(20), 0, 0);
                coverageCard.addView(itemView, itemParams);
            }
        }

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(DpPxUtils.dp(24), DpPxUtils.dp(20), DpPxUtils.dp(24), 0);
        whiteCard.addView(coverageCard, cardParams);

        // ===== Feature Cards Row (bolt + headphones) =====
        LinearLayout featureRow = new LinearLayout(activity);
        featureRow.setOrientation(LinearLayout.HORIZONTAL);

        View resolutionCard = buildFeatureCard(activity, R.mipmap.bolt,
                "Instant Resolution", "Quick resolution in just a few clicks");
        View supportCard = buildFeatureCard(activity, R.mipmap.headphones,
                "24/7 Support", "Get help anytime with fast response");

        LinearLayout.LayoutParams featureParams1 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        featureParams1.setMargins(0, 0, DpPxUtils.dp(6), 0);
        featureRow.addView(resolutionCard, featureParams1);

        LinearLayout.LayoutParams featureParams2 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        featureParams2.setMargins(DpPxUtils.dp(6), 0, 0, 0);
        featureRow.addView(supportCard, featureParams2);

        LinearLayout.LayoutParams featureRowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        featureRowParams.setMargins(DpPxUtils.dp(24), DpPxUtils.dp(16), DpPxUtils.dp(24), 0);
        whiteCard.addView(featureRow, featureRowParams);

        // ===== Footer =====
        LinearLayout footer = buildFooter(activity, actions);
        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        footerParams.setMargins(DpPxUtils.dp(24), DpPxUtils.dp(24), DpPxUtils.dp(24), DpPxUtils.dp(24));
        whiteCard.addView(footer, footerParams);

        // Assemble scroll -> sheet -> root
        scrollView.addView(contentView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        sheetContainer.addView(scrollView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        FrameLayout.LayoutParams sheetParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        sheetParams.topMargin = DpPxUtils.dp(40);
        rootContainer.addView(sheetContainer, sheetParams);

        activity.setContentView(rootContainer);
    }

    /**
     * iOS layout reference:
     *   checkIcon: 20x20, left=7
     *   label: left=40 (aligned with coverageHeaderLabel)
     *   checkIcon centerY = label.top + font.ascender/2
     */
    private View buildCoverageItem(Activity activity, String text) {
        FrameLayout container = new FrameLayout(activity);

        ImageView checkIcon = new ImageView(activity);
        checkIcon.setImageResource(R.mipmap.icon_check_selected_black);
        checkIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView label = new TextView(activity);
        label.setTextSize(14);
        label.setTextColor(0xFF000000);

        String[] parts = text.split(" - ", 2);
        if (parts.length >= 2) {
            SpannableString spannable = new SpannableString(text);
            spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, parts[0].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            label.setText(spannable);
        } else {
            String[] dashParts = text.split(" – ", 2);
            if (dashParts.length >= 2) {
                SpannableString spannable = new SpannableString(text);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, dashParts[0].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                label.setText(spannable);
            } else {
                label.setText(text);
            }
        }

        // checkIcon: 20x20, left=7, vertically centered to first line of label
        FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(DpPxUtils.dp(20), DpPxUtils.dp(20));
        iconParams.leftMargin = DpPxUtils.dp(7);
        container.addView(checkIcon, iconParams);

        // label: left=40 (aligned with coverage header label)
        FrameLayout.LayoutParams labelParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        labelParams.leftMargin = DpPxUtils.dp(40);
        container.addView(label, labelParams);

        return container;
    }

    private View buildFeatureCard(Activity activity, int iconResId, String title, String detail) {
        LinearLayout card = new LinearLayout(activity);
        card.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(0xFFF8F9FF);
        bg.setCornerRadius(DpPxUtils.dp(10));
        card.setBackground(bg);
        int padding = DpPxUtils.dp(20);
        card.setPadding(padding, padding, padding, padding);

        // Icon: 32x32
        ImageView iconView = new ImageView(activity);
        iconView.setImageResource(iconResId);
        iconView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        card.addView(iconView, new LinearLayout.LayoutParams(DpPxUtils.dp(32), DpPxUtils.dp(32)));

        // Title
        TextView titleLabel = new TextView(activity);
        titleLabel.setText(title);
        titleLabel.setTextSize(16);
        titleLabel.setTypeface(null, Typeface.BOLD);
        titleLabel.setTextColor(0xFF000000);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.topMargin = DpPxUtils.dp(10);
        card.addView(titleLabel, titleParams);

        // Detail
        TextView detailLabel = new TextView(activity);
        detailLabel.setText(detail);
        detailLabel.setTextSize(14);
        detailLabel.setTextColor(0xFF000000);
        LinearLayout.LayoutParams detailParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        detailParams.topMargin = DpPxUtils.dp(4);
        card.addView(detailLabel, detailParams);

        return card;
    }

    private LinearLayout buildFooter(Activity activity, WFPInfoLayoutActions actions) {
        LinearLayout footer = new LinearLayout(activity);
        footer.setOrientation(LinearLayout.VERTICAL);

        // Opt-in button: height=52dp
        TextView optInButton = new TextView(activity);
        optInButton.setText("Secure Your Purchase Now");
        optInButton.setTextColor(Color.WHITE);
        optInButton.setTextSize(14);
        optInButton.setTypeface(null, Typeface.BOLD);
        optInButton.setGravity(Gravity.CENTER);
        GradientDrawable optInBg = new GradientDrawable();
        optInBg.setColor(0xFF000000);
        optInBg.setCornerRadius(DpPxUtils.dp(10));
        optInButton.setBackground(optInBg);
        optInButton.setOnClickListener(v -> actions.getOnOptIn().run());
        footer.addView(optInButton, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, DpPxUtils.dp(52)));

        // "Continue Without Protection" button
        TextView noNeedButton = new TextView(activity);
        noNeedButton.setText("Continue Without Protection");
        noNeedButton.setTextColor(0xFF808692);
        noNeedButton.setTextSize(14);
        noNeedButton.setTypeface(null, Typeface.BOLD);
        noNeedButton.setGravity(Gravity.CENTER);
        noNeedButton.setOnClickListener(v -> actions.getOnNoNeed().run());
        LinearLayout.LayoutParams noNeedParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        noNeedParams.topMargin = DpPxUtils.dp(12);
        footer.addView(noNeedButton, noNeedParams);

        // Divider
        View divider = new View(activity);
        divider.setBackgroundColor(0xFFE0E0E0);
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, DpPxUtils.dp(1));
        dividerParams.topMargin = DpPxUtils.dp(20);
        footer.addView(divider, dividerParams);

        // Bottom row: links on left, "Powered By Seel" on right
        LinearLayout bottomRow = new LinearLayout(activity);
        bottomRow.setOrientation(LinearLayout.HORIZONTAL);
        bottomRow.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout linksStack = new LinearLayout(activity);
        linksStack.setOrientation(LinearLayout.HORIZONTAL);

        TextView privacyButton = buildUnderlineButton(activity, "Privacy Policy");
        privacyButton.setOnClickListener(v -> actions.getOnPrivacyPolicy().run());
        TextView termsButton = buildUnderlineButton(activity, "Terms of Service");
        termsButton.setOnClickListener(v -> actions.getOnTerms().run());

        LinearLayout.LayoutParams termsParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        termsParams.setMargins(DpPxUtils.dp(16), 0, 0, 0);

        linksStack.addView(privacyButton);
        linksStack.addView(termsButton, termsParams);

        // "Powered By Seel"
        TextView poweredBy = new TextView(activity);
        SpannableString poweredText = new SpannableString("Powered By Seel");
        poweredText.setSpan(new StyleSpan(Typeface.BOLD), 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        poweredBy.setText(poweredText);
        poweredBy.setTextSize(10);
        poweredBy.setTextColor(0xFF000000);

        bottomRow.addView(linksStack, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        bottomRow.addView(poweredBy);

        LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bottomParams.setMargins(DpPxUtils.dp(14), DpPxUtils.dp(14), DpPxUtils.dp(14), 0);
        footer.addView(bottomRow, bottomParams);

        return footer;
    }

    private TextView buildUnderlineButton(Activity activity, String title) {
        TextView button = new TextView(activity);
        SpannableString spannable = new SpannableString(title);
        spannable.setSpan(new UnderlineSpan(), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        button.setText(spannable);
        button.setTextSize(11);
        button.setTextColor(0xFF5C5F62);
        return button;
    }
}
