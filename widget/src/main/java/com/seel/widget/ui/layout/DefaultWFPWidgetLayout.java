package com.seel.widget.ui.layout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seel.widget.R;
import com.seel.widget.core.Constants;
import com.seel.widget.models.QuotesResponse;
import com.seel.widget.ui.LineView;
import com.seel.widget.ui.SeelCheckbox;
import com.seel.widget.ui.SeelSwitch;
import com.seel.widget.ui.SeelWFPTitleView;
import com.seel.widget.utils.DpPxUtils;

import java.util.List;

/**
 * The original WFP widget layout — used when brandType is nil or unrecognized.
 * Switch/Checkbox on the right, title on the left, detail lines below.
 */
public class DefaultWFPWidgetLayout implements WFPWidgetLayoutProvider {

    private SeelWFPTitleView titleView;
    private SeelSwitch switcher;
    private SeelCheckbox checkbox;
    private LinearLayout detailContainer;
    private LinearLayout disclaimerContainer;
    private android.widget.TextView disclaimerLabel;
    private ToggleStyle currentToggleStyle = ToggleStyle.SWITCH_STYLE;
    private LinearLayout titleContainer;

    @Override
    public void buildLayout(ViewGroup container, WFPWidgetLayoutActions actions) {
        android.content.Context context = container.getContext();

        container.setBackgroundColor(Constants.BACKGROUND_COLOR);
        int padding = DpPxUtils.dp(16);
        container.setPadding(padding, padding, padding, padding);

        LinearLayout contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);

        titleContainer = new LinearLayout(context);
        titleContainer.setOrientation(LinearLayout.HORIZONTAL);

        titleView = new SeelWFPTitleView(context);
        titleView.setInfoClickListener(actions.getOnInfoTapped()::run);
        titleView.setShowPowered(true);

        switcher = new SeelSwitch(context);
        switcher.setOnValueChangedListener(isOn -> actions.getOnToggleChanged().onChanged(isOn));
        switcher.setOnTintColor(Constants.PRIMARY_COLOR);
        switcher.setTrackTintColor(Constants.TRACK_COLOR_OFF);
        switcher.setThumbTintColor(Constants.THUMB_COLOR);

        checkbox = new SeelCheckbox(context);
        checkbox.setOnValueChangedListener(isOn -> actions.getOnToggleChanged().onChanged(isOn));

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        titleView.setLayoutParams(titleParams);

        LinearLayout.LayoutParams toggleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        titleContainer.addView(titleView);
        titleContainer.addView(switcher);
        switcher.setLayoutParams(toggleParams);
        checkbox.setLayoutParams(toggleParams);

        detailContainer = new LinearLayout(context);
        detailContainer.setOrientation(LinearLayout.VERTICAL);

        disclaimerLabel = new android.widget.TextView(context);
        disclaimerLabel.setTextSize(11);
        disclaimerLabel.setTextColor(0xFF808692);
        disclaimerLabel.setVisibility(View.GONE);

        contentLayout.addView(titleContainer);
        contentLayout.addView(detailContainer);
        contentLayout.addView(disclaimerLabel);

        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        container.addView(contentLayout, contentParams);
    }

    @Override
    public void updateLayout(ViewGroup container, WFPWidgetLayoutData data) {
        QuotesResponse quoteResponse = data.getQuoteResponse();
        boolean displayView = quoteResponse != null;

        container.setVisibility(displayView ? View.VISIBLE : View.GONE);

        boolean isRejected = quoteResponse != null && quoteResponse.isRejected();
        container.setAlpha(isRejected ? 0.9f : 1.0f);

        titleView.setTitle(quoteResponse != null && quoteResponse.getExtraInfo() != null
                ? quoteResponse.getExtraInfo().getWidgetTitle() : null);
        titleView.setPrice(quoteResponse != null && !isRejected ? quoteResponse.getPrice() : null);
        titleView.setShowInfo(!isRejected && quoteResponse != null);
        titleView.setLoading(data.isLoading());
        titleView.updateViews();

        // Swap toggle if style changed
        if (data.getToggleStyle() != currentToggleStyle) {
            View oldToggle = currentToggleStyle == ToggleStyle.SWITCH_STYLE ? switcher : checkbox;
            View newToggle = data.getToggleStyle() == ToggleStyle.SWITCH_STYLE ? switcher : checkbox;
            titleContainer.removeView(oldToggle);
            titleContainer.addView(newToggle);
            currentToggleStyle = data.getToggleStyle();
        }

        View toggle = currentToggleStyle == ToggleStyle.SWITCH_STYLE ? switcher : checkbox;
        toggle.setVisibility(quoteResponse == null || isRejected ? View.GONE : View.VISIBLE);

        switcher.setOn(data.isToggleOn());
        checkbox.setOn(data.isToggleOn());

        updateDetailViews(quoteResponse);

        // Disclaimer
        if (quoteResponse != null && quoteResponse.getExtraInfo() != null
                && quoteResponse.getExtraInfo().getWidgetDisclaimer() != null
                && !quoteResponse.getExtraInfo().getWidgetDisclaimer().isEmpty()) {
            disclaimerLabel.setText(quoteResponse.getExtraInfo().getWidgetDisclaimer());
            disclaimerLabel.setVisibility(View.VISIBLE);
        } else {
            disclaimerLabel.setVisibility(View.GONE);
        }
    }

    private void updateDetailViews(QuotesResponse quoteResponse) {
        detailContainer.removeAllViews();
        if (quoteResponse == null) return;

        if (quoteResponse.getExtraInfo() != null && quoteResponse.getExtraInfo().getDisplayWidgetText() != null) {
            List<String> texts = quoteResponse.getExtraInfo().getDisplayWidgetText();
            for (String text : texts) {
                LineView displayText = new LineView(detailContainer.getContext());
                if (texts.size() > 1) {
                    displayText.setIconImage(R.mipmap.icon_select);
                } else {
                    displayText.setIconImage(0);
                }
                displayText.setContent(text);
                displayText.updateViews();
                detailContainer.addView(displayText);
            }
        }
    }
}
