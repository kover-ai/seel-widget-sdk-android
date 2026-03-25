package com.seel.widget.core;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Formats monetary values with locale-aware currency symbols.
 * Aligned with iOS SeelWidget/Core/FormatMoney.swift.
 */
public final class FormatMoney {

    public static class Options {
        public boolean showCurrency = true;
        public String locale = "";
        public String fallbackValue = "-";

        public Options() {}
    }

    private static final Map<String, String> CURRENCY_LOCALE_MAP = new HashMap<>();
    static {
        CURRENCY_LOCALE_MAP.put("USD", "en-US");
        CURRENCY_LOCALE_MAP.put("CAD", "en-CA");
        CURRENCY_LOCALE_MAP.put("AUD", "en-AU");
        CURRENCY_LOCALE_MAP.put("EUR", "de-DE");
        CURRENCY_LOCALE_MAP.put("GBP", "en-GB");
        CURRENCY_LOCALE_MAP.put("NZD", "en-NZ");
        CURRENCY_LOCALE_MAP.put("HKD", "zh-HK");
        CURRENCY_LOCALE_MAP.put("SGD", "zh-SG");
        CURRENCY_LOCALE_MAP.put("DKK", "da-DK");
    }

    private static final Map<String, NumberFormat> formatterCache = new HashMap<>();

    private static synchronized NumberFormat cachedFormatter(Locale locale, String currencyCode) {
        String key = locale.toString() + "_" + currencyCode;
        NumberFormat cached = formatterCache.get(key);
        if (cached != null) return cached;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        try {
            formatter.setCurrency(Currency.getInstance(currencyCode));
        } catch (IllegalArgumentException ignored) {
        }
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatterCache.put(key, formatter);
        return formatter;
    }

    private static Locale parseLocale(String localeStr) {
        String[] parts = localeStr.replace("_", "-").split("-");
        if (parts.length >= 2) {
            return new Locale(parts[0], parts[1]);
        }
        return new Locale(parts[0]);
    }

    public static String formatMoney(Double money, String currency) {
        return formatMoney(money, currency, new Options());
    }

    public static String formatMoney(Double money, String currency, Options options) {
        if (money == null) return options.fallbackValue;

        String currencyCode = (currency != null ? currency.trim().toUpperCase(Locale.ROOT) : "");

        if (currencyCode.isEmpty()) {
            return String.format(Locale.US, "%.2f", money);
        }

        String usedLocale;
        if (!options.locale.isEmpty()) {
            usedLocale = options.locale;
        } else {
            usedLocale = CURRENCY_LOCALE_MAP.containsKey(currencyCode)
                    ? CURRENCY_LOCALE_MAP.get(currencyCode) : "en-US";
        }

        Locale locale = parseLocale(usedLocale);
        NumberFormat formatter = cachedFormatter(locale, currencyCode);

        String formatted;
        synchronized (formatter) {
            try {
                formatted = formatter.format(money);
            } catch (Exception e) {
                String plain = String.format(Locale.US, "%.2f", money);
                return options.showCurrency ? plain + " " + currencyCode : plain;
            }
        }

        // HKD: replace "HK$" with "$"
        if ("HKD".equals(currencyCode)) {
            String symbol = formatter.getCurrency() != null
                    ? formatter.getCurrency().getSymbol(locale) : "HK$";
            String replaced = formatted.replace(symbol, "$").trim();
            return options.showCurrency ? replaced + " " + currencyCode : replaced;
        }

        // If the currency symbol equals the currency code (e.g. "USD" instead of "$"),
        // fall back to plain format
        if (formatter.getCurrency() != null) {
            String symbol = formatter.getCurrency().getSymbol(locale);
            if (symbol.equals(currencyCode)) {
                String plain = String.format(Locale.US, "%.2f", money);
                return options.showCurrency ? plain + " " + currencyCode : plain;
            }
        }

        return options.showCurrency ? formatted + " " + currencyCode : formatted;
    }

    private FormatMoney() {}
}
