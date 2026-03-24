package com.seel.widget.ui.layout;

import android.app.Activity;

import com.seel.widget.models.QuotesResponse;

/**
 * Protocol that defines how the WFP info modal builds its UI.
 * Each brand type can provide a different implementation.
 */
public interface WFPInfoLayoutProvider {

    /**
     * Build the info page layout in the given activity.
     */
    void buildLayout(Activity activity, QuotesResponse quoteResponse, WFPInfoLayoutActions actions);
}
