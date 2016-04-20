package com.essential.usdriving.ui.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dongc on 4/18/2016.
 */
public class NetworkUtil {

    private static NetworkUtil sInstance;
    private ConnectivityManager connectivityManager;

    private NetworkUtil(Context context) {
        connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkUtil getInstance(Context context) {
        if (sInstance == null) {
            synchronized (NetworkUtil.class) {
                if (sInstance == null) {
                    sInstance = new NetworkUtil(context);
                }
            }
        }
        return sInstance;
    }

    public boolean isNetworkAvailable() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
