package com.haisom.workspace.lib;

import android.content.Context;

/**
 * Created by wagner on 6/10/17.
 */

public class HaisomWorkspace {
    static native String getAdInfo();

    static native String getAlarm();

    native int nativeDip0(Context context);

    native int nativeDip1(Context context);

    native int nativeDip2(Context context);

    native int nativeDip3(Context context);

    native String nativeDip4(Context context);

    static {
        System.loadLibrary("haisomwork");
    }

    public static String getAlarmMsg() {
        return getAlarm();
    }

    public static String getAd() {
        return getAdInfo();
    }

    public int reflectNativeDip0(Context context) {
        return nativeDip0(context);
    }

    public int reflectNativeDip1(Context context) {
        return nativeDip1(context);
    }

    public int reflectNativeDip2(Context context) {
        return nativeDip2(context);
    }

    public int reflectNativeDip3(Context context) {
        return nativeDip3(context);
    }

    public String reflectNativeDip4(Context context) {
        return nativeDip4(context);
    }
}
