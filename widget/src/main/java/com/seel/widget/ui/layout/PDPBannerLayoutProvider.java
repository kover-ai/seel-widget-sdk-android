package com.seel.widget.ui.layout;

import android.view.ViewGroup;

/**
 * Protocol that defines how the PDP banner builds its UI.
 * Each brand type can provide a different implementation.
 */
public interface PDPBannerLayoutProvider {
    void buildLayout(ViewGroup container);
}
