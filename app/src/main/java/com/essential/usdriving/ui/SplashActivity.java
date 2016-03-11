package com.essential.usdriving.ui;

import android.content.Intent;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseActivity;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.sqlite.DatabaseLoader;
import tatteam.com.app_common.ui.activity.BaseSplashActivity;
import tatteam.com.app_common.ui.activity.EssentialSplashActivity;
import tatteam.com.app_common.util.AppConstant;

public class SplashActivity extends EssentialSplashActivity {


    @Override
    protected void onCreateContentView() {
        super.onCreateContentView();
    }

    @Override
    protected void onInitAppCommon() {
        AppCommon.getInstance().initIfNeeded(getApplicationContext());
        AppCommon.getInstance().increaseLaunchTime();
        AppCommon.getInstance().syncAdsIfNeeded(AppConstant.AdsType.SMALL_BANNER_TEST, AppConstant.AdsType.BIG_BANNER_TEST);
        DatabaseLoader.getInstance().createIfNeeded(getApplicationContext(), "usdriving.db");
    }

    @Override
    protected void onFinishInitAppCommon() {
        switchToChooseTargetActivity();
    }
    private void switchToChooseTargetActivity() {
        Intent intent = new Intent(SplashActivity.this, BaseActivity.class);
        startActivity(intent);
        finish();
    }
}
