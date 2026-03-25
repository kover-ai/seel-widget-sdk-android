package com.seel.widget.ui.layout;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.seel.widget.R;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.ui.CoverageDetailsView;
import com.seel.widget.ui.CoverageInfoFooter;
import com.seel.widget.ui.CoverageTipsView;
import com.seel.widget.ui.CoverageTitleView;
import com.seel.widget.ui.SeelNavigationBar;
import com.seel.widget.utils.DpPxUtils;

/**
 * The original WFP info layout — used when brandType is nil or unrecognized.
 */
public class DefaultWFPInfoLayout implements WFPInfoLayoutProvider {

    @Override
    public void buildLayout(Activity activity, QuotesResponse quoteResponse, WFPInfoLayoutActions actions) {
        LinearLayout rootContainer = new LinearLayout(activity);
        rootContainer.setOrientation(LinearLayout.VERTICAL);
        rootContainer.setBackgroundColor(0x80000000);

        // Navigation bar
        LinearLayout navigationBar = new LinearLayout(activity);
        navigationBar.setOrientation(LinearLayout.HORIZONTAL);
        navigationBar.setGravity(Gravity.CENTER_VERTICAL);
        navigationBar.setBackgroundColor(activity.getResources().getColor(R.color.seel_nav_background));
        navigationBar.setPadding(DpPxUtils.dp(16), 0, DpPxUtils.dp(16), 0);

        TextView navigationTitle = new TextView(activity);
        if (quoteResponse != null && quoteResponse.getExtraInfo() != null) {
            navigationTitle.setText(quoteResponse.getExtraInfo().getWidgetTitle());
        }
        navigationTitle.setTextColor(0xFF000000);
        navigationTitle.setTextSize(18);
        navigationTitle.setTypeface(null, Typeface.BOLD);

        TextView closeButton = new TextView(activity);
        closeButton.setText(R.string.seel_close);
        closeButton.setTextColor(0xFF000000);
        closeButton.setTextSize(17);
        closeButton.setOnClickListener(v -> actions.getOnClose().run());

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        titleParams.gravity = Gravity.CENTER;
        navigationTitle.setGravity(Gravity.CENTER);
        navigationTitle.setLayoutParams(titleParams);
        closeButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        navigationBar.addView(navigationTitle);
        navigationBar.addView(closeButton);

        View navigationDivider = new View(activity);
        navigationDivider.setBackgroundColor(0xFFEEEEEE);

        android.widget.FrameLayout backgroundView = new android.widget.FrameLayout(activity);
        backgroundView.setBackgroundColor(0xFFFFFFFF);

        ScrollView scrollView = new ScrollView(activity);
        scrollView.setVerticalScrollBarEnabled(true);

        LinearLayout contentView = new LinearLayout(activity);
        contentView.setOrientation(LinearLayout.VERTICAL);

        CoverageTitleView wfpView = new CoverageTitleView(activity);
        if (quoteResponse != null && quoteResponse.getExtraInfo() != null) {
            wfpView.setTitle(quoteResponse.getExtraInfo().getWidgetTitle());
        }

        TextView seelLabel = new TextView(activity);
        String coveredText = activity.getString(R.string.seel_whats_covered_by_seel);
        SpannableString spannableString = new SpannableString(coveredText);
        int seelStart = coveredText.lastIndexOf("Seel");
        spannableString.setSpan(new ForegroundColorSpan(0xFF2121C4),
                seelStart, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        seelLabel.setText(spannableString);
        seelLabel.setTextColor(0xFF000000);
        seelLabel.setTextSize(16);
        seelLabel.setTypeface(null, Typeface.BOLD);

        CoverageDetailsView coverageDetailsView = new CoverageDetailsView(activity);
        float detailsRadius = DpPxUtils.dp(4);
        android.graphics.drawable.GradientDrawable detailsBg = new android.graphics.drawable.GradientDrawable();
        detailsBg.setColor(0xFF333333);
        detailsBg.setCornerRadius(detailsRadius);
        coverageDetailsView.setBackground(detailsBg);

        CoverageTipsView coverageTipsView = new CoverageTipsView(activity);
        CoverageInfoFooter coverageInfoFooter = new CoverageInfoFooter(activity);

        contentView.addView(wfpView);
        contentView.addView(seelLabel);
        contentView.addView(coverageDetailsView);
        contentView.addView(coverageTipsView);
        contentView.addView(coverageInfoFooter);

        scrollView.addView(contentView);
        backgroundView.addView(scrollView);

        rootContainer.addView(navigationBar);
        rootContainer.addView(navigationDivider);
        rootContainer.addView(backgroundView);

        // Layout params
        navigationBar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, DpPxUtils.dp(44)));
        navigationDivider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, DpPxUtils.dp(1)));
        backgroundView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

        LinearLayout.LayoutParams wfpParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wfpParams.setMargins(DpPxUtils.dp(20), DpPxUtils.dp(10), DpPxUtils.dp(20), 0);
        wfpView.setLayoutParams(wfpParams);

        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        labelParams.setMargins(DpPxUtils.dp(20), DpPxUtils.dp(20), DpPxUtils.dp(20), 0);
        seelLabel.setLayoutParams(labelParams);

        LinearLayout.LayoutParams detailsParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        detailsParams.setMargins(DpPxUtils.dp(20), DpPxUtils.dp(20), DpPxUtils.dp(20), 0);
        coverageDetailsView.setLayoutParams(detailsParams);

        LinearLayout.LayoutParams tipsParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tipsParams.setMargins(DpPxUtils.dp(30), DpPxUtils.dp(20), DpPxUtils.dp(30), 0);
        coverageTipsView.setLayoutParams(tipsParams);

        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        footerParams.setMargins(DpPxUtils.dp(20), DpPxUtils.dp(20), DpPxUtils.dp(20), DpPxUtils.dp(18));
        coverageInfoFooter.setLayoutParams(footerParams);

        // Set callbacks
        coverageInfoFooter.setOptedInClickListener(() -> {
            actions.getOnOptIn().run();
            activity.finish();
        });
        coverageInfoFooter.setNoNeedClickListener(() -> {
            actions.getOnNoNeed().run();
            activity.finish();
        });
        coverageInfoFooter.setPrivacyPolicyClickListener(() -> actions.getOnPrivacyPolicy().run());
        coverageInfoFooter.setTermsClickListener(() -> actions.getOnTerms().run());

        // Update views
        if (quoteResponse != null) {
            wfpView.setPrice(quoteResponse.getPrice());
            wfpView.updateViews();
            coverageDetailsView.setQuoteResponse(quoteResponse);
            coverageDetailsView.updateViews();
        }

        activity.setContentView(rootContainer);
    }
}
