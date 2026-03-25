package com.seel.widget.utils;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

public class DpPxUtils {
    public static int dip2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
                context.getResources().getDisplayMetrics());
    }
    
    public static int dp(float value) {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object currentActivityThread = activityThread.getMethod("currentActivityThread").invoke(null);
            Application app = (Application) activityThread.getMethod("getApplication").invoke(currentActivityThread);
            return dip2px(app, value);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to obtain Application Context via reflection. Use dip2px(Context, float) instead.", e);
        }
    }


}
