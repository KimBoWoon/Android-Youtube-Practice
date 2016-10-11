package com.videovillage.application.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by BoWoon on 2016-10-11.
 */

public class SharedStore {
    private static final String KEY = "pref";

    public static int getInt(Context context, String param) {
        return context.getSharedPreferences(KEY, 0).getInt(param, 0);
    }

    public static void setInt(Context context, String param, long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, 0).edit();
        editor.putLong(param, value);
        editor.commit();
    }

    public static String getString(Context context, String param) {
        return context.getSharedPreferences(KEY, 0).getString(param, "");
    }

    public static void setString(Context context, String param, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, 0).edit();
        editor.putString(param, value);
        editor.commit();
    }
}
