package com.essential.usdriving;

import android.app.Application;

import com.essential.usdriving.database.DataSource;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.sqlite.DatabaseLoader;
import tatteam.com.app_common.util.AppSpeaker;

/**
 * Created by ThanhNH-Mac on 10/23/15.
 */
public class ClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataSource.getInstance().init(getApplicationContext());
        AppCommon.getInstance().initIfNeeded(getApplicationContext());
        DatabaseLoader.getInstance().restoreState(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        AppCommon.getInstance().destroy();
       // AppSpeaker.getInstance().destroy();
        DatabaseLoader.getInstance().destroy();

        super.onTerminate();
    }
}
