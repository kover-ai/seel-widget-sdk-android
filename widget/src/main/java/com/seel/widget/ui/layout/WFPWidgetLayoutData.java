package com.seel.widget.ui.layout;

import com.seel.widget.models.QuotesResponse;

/**
 * Data model passed to widget layout providers for rendering.
 */
public class WFPWidgetLayoutData {
    private final QuotesResponse quoteResponse;
    private final boolean loading;
    private final ToggleStyle toggleStyle;
    private final boolean toggleIsOn;

    public WFPWidgetLayoutData(QuotesResponse quoteResponse, boolean loading, ToggleStyle toggleStyle, boolean toggleIsOn) {
        this.quoteResponse = quoteResponse;
        this.loading = loading;
        this.toggleStyle = toggleStyle;
        this.toggleIsOn = toggleIsOn;
    }

    public QuotesResponse getQuoteResponse() { return quoteResponse; }
    public boolean isLoading() { return loading; }
    public ToggleStyle getToggleStyle() { return toggleStyle; }
    public boolean isToggleOn() { return toggleIsOn; }
}
