package com.seel.widget.ui.layout;

import android.view.ViewGroup;

import com.seel.widget.models.QuotesResponse;

/**
 * Protocol that defines how the WFP widget builds its UI.
 * Each brand type can provide a different implementation.
 */
public interface WFPWidgetLayoutProvider {

    /**
     * Called once to create and add subviews into the container.
     */
    void buildLayout(ViewGroup container, WFPWidgetLayoutActions actions);

    /**
     * Called whenever data changes (quote loaded, toggle state, loading, etc.).
     */
    void updateLayout(ViewGroup container, WFPWidgetLayoutData data);
}
