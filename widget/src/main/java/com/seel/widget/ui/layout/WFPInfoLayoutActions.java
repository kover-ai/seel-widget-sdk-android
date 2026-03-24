package com.seel.widget.ui.layout;

/**
 * Callbacks for the WFP info layout.
 */
public class WFPInfoLayoutActions {
    private final Runnable onClose;
    private final Runnable onOptIn;
    private final Runnable onNoNeed;
    private final Runnable onPrivacyPolicy;
    private final Runnable onTerms;

    public WFPInfoLayoutActions(Runnable onClose, Runnable onOptIn, Runnable onNoNeed, Runnable onPrivacyPolicy, Runnable onTerms) {
        this.onClose = onClose;
        this.onOptIn = onOptIn;
        this.onNoNeed = onNoNeed;
        this.onPrivacyPolicy = onPrivacyPolicy;
        this.onTerms = onTerms;
    }

    public Runnable getOnClose() { return onClose; }
    public Runnable getOnOptIn() { return onOptIn; }
    public Runnable getOnNoNeed() { return onNoNeed; }
    public Runnable getOnPrivacyPolicy() { return onPrivacyPolicy; }
    public Runnable getOnTerms() { return onTerms; }
}
