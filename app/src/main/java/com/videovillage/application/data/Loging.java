package com.videovillage.application.data;

import android.util.Log;

import com.videovillage.application.mainactivity.MainActivity;

/**
 * Created by 보운 on 2016-10-12.
 */

public class Loging {
    static final String TAG = "VideoVillage";

    /**
     * Log Level Error
     **/
    public static void e(String message) {
        if (MainActivity.DEBUG) {
            Log.e(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Warning
     **/
    public static void w(String message) {
        if (MainActivity.DEBUG) {
            Log.w(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Information
     **/
    public static void i(String message) {
        if (MainActivity.DEBUG) {
            Log.i(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Debug
     **/
    public static void d(String message) {
        if (MainActivity.DEBUG) {
            Log.d(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Verbose
     **/
    public static void v(String message) {
        if (MainActivity.DEBUG) {
            Log.v(TAG, buildLogMsg(message));
        }
    }


    private static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();
    }
}