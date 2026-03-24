package com.seel.widget.ui.layout;

/**
 * Callbacks the widget layout can trigger back to SeelWFPView.
 */
public class WFPWidgetLayoutActions {
    private final Runnable onInfoTapped;
    private final OnToggleChanged onToggleChanged;
    private final Runnable onDisabledTapped;

    public interface OnToggleChanged {
        void onChanged(boolean isOn);
    }

    public WFPWidgetLayoutActions(Runnable onInfoTapped, OnToggleChanged onToggleChanged, Runnable onDisabledTapped) {
        this.onInfoTapped = onInfoTapped;
        this.onToggleChanged = onToggleChanged;
        this.onDisabledTapped = onDisabledTapped;
    }

    public Runnable getOnInfoTapped() { return onInfoTapped; }
    public OnToggleChanged getOnToggleChanged() { return onToggleChanged; }
    public Runnable getOnDisabledTapped() { return onDisabledTapped; }
}
