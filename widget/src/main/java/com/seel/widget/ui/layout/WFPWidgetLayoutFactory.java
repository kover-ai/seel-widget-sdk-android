package com.seel.widget.ui.layout;

/**
 * Factory that returns the correct widget layout provider based on brandType.
 * Add new cases here as more brand types are supported.
 */
public class WFPWidgetLayoutFactory {

    public static WFPWidgetLayoutProvider provider(String brandType) {
        if ("ebth-wfp".equals(brandType)) {
            return new EBTHWFPWidgetLayout();
        }
        return new DefaultWFPWidgetLayout();
    }
}
