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
            // 通过反射获取 Application Context
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object currentActivityThread = activityThread.getMethod("currentActivityThread").invoke(null);
            Application app = (Application) activityThread.getMethod("getApplication").invoke(currentActivityThread);
            return dip2px(app, value);
        } catch (Exception e) {
            // 如果获取失败，抛出异常提示使用带 context 参数的版本
            throw new RuntimeException("无法获取 Application Context，请使用 dp(Context context, int value) 方法", e);
        }
    }


}
