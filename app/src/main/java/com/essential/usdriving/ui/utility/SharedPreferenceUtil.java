package com.essential.usdriving.ui.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dongc on 4/17/2016.
 */
public class SharedPreferenceUtil {

    public final static String PREF_NAME = "com.essential.usdriving.sharedprefences";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static SharedPreferenceUtil sInstance;

    private SharedPreferenceUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static SharedPreferenceUtil getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SharedPreferenceUtil.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreferenceUtil(context);
                }
            }
        }
        return sInstance;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return mEditor;
    }
}
