package com.essential.usdriving.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essential.usdriving.R;

/**
 * Created by dongc_000 on 4/14/2016.
 */
public class EssentialProgressBar extends RelativeLayout {

    private RelativeLayout mThumb;
    private RelativeLayout mProgressBarContainer;
    private TextView mTextProgress;
    private ProgressBar mProgressBar;

    private OnProgressBarInteractListener mOnProgressBarInteractListener;

    private int mProgress;
    private float mRatio;
    private boolean mIsFirst;

    public EssentialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.essential_progress_bar, this);

        findViews();

        mIsFirst = true;

        mProgressBar.getViewTreeObserver().addOnGlobalLayoutListener(mProgressBarGlobalLayoutListener);
        mProgressBar.setOnTouchListener(mProgressBarTouchListener);
        mThumb.setOnTouchListener(mThumbMoveListener);
    }

    public EssentialProgressBar(Context context) {
        super(context);

        View.inflate(context, R.layout.essential_progress_bar, this);

        findViews();

        mIsFirst = true;

        mProgressBar.getViewTreeObserver().addOnGlobalLayoutListener(mProgressBarGlobalLayoutListener);
        mProgressBar.setOnTouchListener(mProgressBarTouchListener);
        mThumb.setOnTouchListener(mThumbMoveListener);
    }

    public void setMaxProgress(int value) {
        mProgressBar.setMax(value);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        mProgressBar.setProgress(progress + 1);
    }

    public void reset() {
        setProgress(1);
    }

    public void setOnProgressBarInteractListener(OnProgressBarInteractListener onProgressBarInteractListener) {
        mOnProgressBarInteractListener = onProgressBarInteractListener;
    }

    private void updateLayout(int progress) {
        mTextProgress.setText("" + (progress + 1));
        float thumbX = mProgressBarContainer.getX() + mRatio * (progress + 1) - mThumb.getWidth() / 2;
        mThumb.setX(thumbX);
    }

    private void findViews() {
        mThumb = (RelativeLayout) findViewById(R.id.thumb);
        mTextProgress = (TextView) findViewById(R.id.text_progress);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBarContainer = (RelativeLayout) findViewById(R.id.progress_bar_container);
    }

    private ViewTreeObserver.OnGlobalLayoutListener mProgressBarGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (mIsFirst) {
                mIsFirst = false;
                if (mProgressBar.getMax() != 0) {
                    mRatio = (float) mProgressBar.getWidth() / mProgressBar.getMax();
                }
            }
            updateLayout(mProgress);
        }
    };

    private OnTouchListener mProgressBarTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mOnProgressBarInteractListener != null) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float ratio = event.getX() / mProgressBar.getWidth();
                    setProgress((int) (ratio * mProgressBar.getMax()));
                    mOnProgressBarInteractListener.onSeekTo(mProgress);
                }
            }
            return false;
        }
    };

    private OnTouchListener mThumbMoveListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mOnProgressBarInteractListener != null) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    float ratio = (event.getRawX() - mProgressBarContainer.getX()) / mProgressBar.getWidth();
                    int tmp = (int) (ratio * mProgressBar.getMax());
                    if (tmp >= 0 && tmp < mProgressBar.getMax()) {
                        setProgress(tmp);
                        mOnProgressBarInteractListener.onSeekTo(mProgress);
                    }
                }
            }
            return false;
        }
    };

    public interface OnProgressBarInteractListener {
        void onSeekTo(int progress);
    }
}
