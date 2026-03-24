package com.seel.widget.ui.layout;

/**
 * Factory that returns the correct PDP banner layout provider based on brandType.
 */
public class PDPBannerLayoutFactory {

    public static PDPBannerLayoutProvider provider(String brandType, int backgroundColor, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        if ("ebth-wfp".equals(brandType)) {
            return new EBTHPDPBanner(backgroundColor, paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        return new DefaultPDPBanner(backgroundColor, paddingLeft, paddingTop, paddingRight, paddingBottom);
    }
}
