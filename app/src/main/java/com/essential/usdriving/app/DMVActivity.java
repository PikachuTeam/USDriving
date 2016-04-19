package com.essential.usdriving.app;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.essential.usdriving.R;
import com.essential.usdriving.ui.home.HomeFragment;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.ads.AdsSmallBannerHandler;
import tatteam.com.app_common.ui.fragment.BaseFragment;
import tatteam.com.app_common.util.AppConstant;
import tatteam.com.app_common.util.CloseAppHandler;

public class DMVActivity extends tatteam.com.app_common.ui.activity.BaseActivity {

    private Toolbar toolbar;
    private FrameLayout adsContainer;
    private MenuItem menuToolbar;
    private FloatingActionButton fab;
    private CoordinatorLayout mCoordinatorLayout;

    private CloseAppHandler mCloseAppHandler;
    private AdsSmallBannerHandler mAdsSmallBannerHandler;

    private final static boolean ADS_ENABLE = true;

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreateContentView() {
        setUpToolbar();
        invalidateOptionsMenu();

        setupAds();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(floatingActionButtonClickListener);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mCloseAppHandler = new CloseAppHandler(this, false);
        mCloseAppHandler.setListener(closeAppListener);
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
    public void onBackPressed() {

        if (getFragmentManager() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                BaseFragment currentFragment = (BaseFragment) getFragmentManager().findFragmentById(getFragmentContainerId());
                currentFragment.onBackPressed();
            } else {
                mCloseAppHandler.setKeyBackPress(this);
            }
        }else{
            mCloseAppHandler.setKeyBackPress(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdsSmallBannerHandler != null) {
            mAdsSmallBannerHandler.destroy();
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

    public FloatingActionButton getFloatingActionButton() {
        return fab;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private View.OnClickListener floatingActionButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppCommon.getInstance().openMoreAppDialog(DMVActivity.this);
        }
    };

    private void setupAds() {
        adsContainer = (FrameLayout) findViewById(R.id.adsContainer);
        if (ADS_ENABLE) {
            mAdsSmallBannerHandler = new AdsSmallBannerHandler(this, adsContainer, AppConstant.AdsType.SMALL_BANNER_TEST);
            mAdsSmallBannerHandler.setup();
        } else {
            adsContainer.setVisibility(View.GONE);
        }
    }
}
