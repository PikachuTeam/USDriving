package com.essential.usdriving.app;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.essential.usdriving.BuildConfig;
import com.essential.usdriving.R;
import com.essential.usdriving.ui.home.HomeFragment;
import com.essential.usdriving.ui.utility.SharedPreferenceUtil;

import tatteam.com.app_common.ads.AdsBigBannerHandler;
import tatteam.com.app_common.ads.AdsSmallBannerHandler;
import tatteam.com.app_common.ui.fragment.BaseFragment;
import tatteam.com.app_common.util.AppConstant;
import tatteam.com.app_common.util.CloseAppHandler;

public class DMVActivity extends tatteam.com.app_common.ui.activity.BaseActivity {

    public final static boolean ADS_ENABLE = true;
    public final static String PREF_RATE = "is rated";
    public final static int START_LOCKING_POSITION = 2;
    public final static int BIG_ADS_SHOWING_INTERVAL = 20;

    public static int sCount;
    private Toolbar toolbar;
    private FrameLayout adsContainer;
    private MenuItem menuToolbar;
    private CoordinatorLayout mCoordinatorLayout;

    private CloseAppHandler mCloseAppHandler;
    private AdsSmallBannerHandler mAdsSmallBannerHandler;
    private AdsBigBannerHandler mAdsBigBannerHandler;
    private static boolean sIsRated;

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreateContentView() {
        setUpToolbar();
        invalidateOptionsMenu();
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        if(BuildConfig.IS_PRO_VERSION) {
            mCloseAppHandler = new CloseAppHandler(this, false);
        }else{
            mCloseAppHandler = new CloseAppHandler(this, true);
        }
        mCloseAppHandler.setListener(closeAppListener);
        if (BuildConfig.IS_PRO_VERSION) {
            setRated();
        } else {
            sIsRated = SharedPreferenceUtil.getInstance(this).getSharedPreferences().getBoolean(PREF_RATE, false);
            setRated();
        }
        setupAds();
        sCount = 0;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }

    @Override
    protected BaseFragment getFragmentContent() {
        return new HomeFragment();
    }

    public void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        menuToolbar = menu.findItem(R.id.Result);
        menuToolbar.setVisible(false);
        return true;
    }

    @Override
    public void onCloseActivity() {
        this.mCloseAppHandler.setKeyBackPress(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdsSmallBannerHandler != null) {
            mAdsSmallBannerHandler.destroy();
        }
        if (mAdsBigBannerHandler != null) {
            mAdsBigBannerHandler.destroy();
        }
    }

    private CloseAppHandler.OnCloseAppListener closeAppListener = new CloseAppHandler.OnCloseAppListener() {
        @Override
        public void onRateAppDialogClose() {
            finish();
        }

        @Override
        public void onTryToCloseApp() {
            Snackbar.make(mCoordinatorLayout, getString(R.string.message_exit), Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void onReallyWantToCloseApp() {
            finish();
        }
    };

    public static boolean isRated() {
        return sIsRated;
    }

    public static void setRated() {
        sIsRated = true;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void showBigAdsIfNeeded() {
        if (!BuildConfig.IS_PRO_VERSION) {
            if (ADS_ENABLE) {
                sCount++;
                if (sCount % BIG_ADS_SHOWING_INTERVAL == 0) {
                    if (mAdsBigBannerHandler != null) {
                        mAdsBigBannerHandler.show();
                    }
                }
            }
        }
    }

    private void setupAds() {
        adsContainer = (FrameLayout) findViewById(R.id.adsContainer);
        if (!BuildConfig.IS_PRO_VERSION) {
            if (ADS_ENABLE) {
                mAdsSmallBannerHandler = new AdsSmallBannerHandler(this, adsContainer, AppConstant.AdsType.SMALL_BANNER_DRIVING_TEST);
                mAdsSmallBannerHandler.setup();
                mAdsBigBannerHandler = new AdsBigBannerHandler(this, AppConstant.AdsType.BIG_BANNER_DRIVING_TEST);
                mAdsBigBannerHandler.setup();
            } else {
                adsContainer.setVisibility(View.GONE);
            }
        } else {
            adsContainer.setVisibility(View.GONE);
        }
    }
}
