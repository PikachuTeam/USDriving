package com.essential.usdriving.app;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.essential.usdriving.R;
import com.essential.usdriving.ui.home.HomeFragment;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.ads.AdsSmallBannerHandler;
import tatteam.com.app_common.ui.fragment.BaseFragment;
import tatteam.com.app_common.util.AppConstant;

public class BaseActivity extends tatteam.com.app_common.ui.activity.BaseActivity {
    private Toolbar toolbar;
    private FrameLayout adsContainer;
    private AdsSmallBannerHandler adsSmallBannerHandler;
    private MenuItem menuToolbar;
    private FloatingActionButton fab;

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreateContentView() {
        adsContainer = (FrameLayout) findViewById(R.id.adsContainer);
        adsSmallBannerHandler = new AdsSmallBannerHandler(this, adsContainer, AppConstant.AdsType.SMALL_BANNER_TEST);
        adsSmallBannerHandler.setup();
        setUpToolbar();
        invalidateOptionsMenu();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(floatingActionButtonClickListener);
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

    public FloatingActionButton getFloatingActionButton() {
        return fab;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private View.OnClickListener floatingActionButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppCommon.getInstance().openMoreAppDialog(BaseActivity.this);
        }
    };
}
