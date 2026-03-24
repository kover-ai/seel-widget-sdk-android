package com.seel.widget.ui.layout;

/**
 * Factory that returns the correct info layout provider based on brandType.
 * Add new cases here as more brand types are supported.
 */
public class WFPInfoLayoutFactory {

    public static WFPInfoLayoutProvider provider(String brandType) {
        if ("ebth-wfp".equals(brandType)) {
            return new EBTHWFPInfoLayout();
        }
        return new DefaultWFPInfoLayout();
    }
}
