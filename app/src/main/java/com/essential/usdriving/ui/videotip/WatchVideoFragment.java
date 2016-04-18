package com.essential.usdriving.ui.videotip;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.ui.ultility.NetworkUtil;

/**
 * Created by dongc on 4/18/2016.
 */
public class WatchVideoFragment extends BaseFragment {

    private WebView mWebView;
    private TextView mTextNoConnection;

    private String mUrl;

    @Override
    public void defineButtonResult() {

    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_watch_video;
    }

    @Override
    protected String setTitle() {
        return getString(R.string.title_video_tips);
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        getData();

        if (NetworkUtil.getInstance(getActivity()).isNetworkAvailable()) {
            mWebView.setVisibility(View.VISIBLE);
            mTextNoConnection.setVisibility(View.GONE);
            mWebView.setWebViewClient(new MyBrowser());
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mWebView.loadUrl(mUrl);
        } else {
            mWebView.setVisibility(View.GONE);
            mTextNoConnection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void findViews(View rootView) {
        mWebView = (WebView) rootView.findViewById(R.id.web_view);
        mTextNoConnection = (TextView) rootView.findViewById(R.id.text_no_connection);
    }

    private void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(VideoTipsFragment.BUNDLE_URL, "");
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
