# Changelog

## Version 1.1.0
- Refine EBTH widget and info modal font weights, sizes, and formatting

## Version 1.0.20
- Add opt-out user config API (`POST /v1/ecommerce/user-configs/{user_id}`) aligned with iOS SDK

## Version 1.0.18
- Add locale-aware currency formatting (`FormatMoney`) aligned with iOS SDK
- Add widget style customization APIs: `setNormalBackgroundColor`, `setSelectedBackgroundColor`, `setDisabledBackgroundColor`, `setCornerRadius`, `setShowDisclaimer`
- Refactor brand-specific defaults with Provider Defaults pattern (OCP compliant)
- Fix disclaimer text duplication and background color not applying correctly
- Fix checkbox toggle not updating background color
- Harden error handling and extract UI strings to resources
- Update README with widget customization documentation

## Version 1.0.3
- Add brand-specific layout system (Provider + Factory pattern)
- Add EBTH brand layouts (widget, info modal, PDP banner)
- Add SeelCheckbox, SeelTooltipView, SeelPDPBannerView components
- Refactor SeelWFPView and SeelWFPInfoActivity to use layout providers
- Align required fields and UI with iOS SDK
- Add image assets to mipmap/

## Version 1.0.1
- Update configuration API

## Version 1.0.0
- Initial release
