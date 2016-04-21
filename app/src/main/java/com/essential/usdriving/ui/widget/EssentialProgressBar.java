package com.essential.usdriving.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
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
    private ImageView imageThumb;

    private OnProgressBarInteractListener mOnProgressBarInteractListener;

    private int mProgress;
    private float mRatio;
    private boolean mIsFirst;

    public EssentialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.essential_progress_bar, this);

        findViews();

        mIsFirst = true;

        getmProgressBar().getViewTreeObserver().addOnGlobalLayoutListener(mProgressBarGlobalLayoutListener);
        getmProgressBar().setOnTouchListener(mProgressBarTouchListener);
        mThumb.setOnTouchListener(mThumbMoveListener);
    }

    public EssentialProgressBar(Context context) {
        super(context);

        View.inflate(context, R.layout.essential_progress_bar, this);

        findViews();

        mIsFirst = true;

        getmProgressBar().getViewTreeObserver().addOnGlobalLayoutListener(mProgressBarGlobalLayoutListener);
        getmProgressBar().setOnTouchListener(mProgressBarTouchListener);
        mThumb.setOnTouchListener(mThumbMoveListener);
    }

    public void setMaxProgress(int value) {
        getmProgressBar().setMax(value);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        getmProgressBar().setProgress(progress + 1);
    }

    public void reset() {
        setProgress(1);
    }

    public void setOnProgressBarInteractListener(OnProgressBarInteractListener onProgressBarInteractListener) {
        mOnProgressBarInteractListener = onProgressBarInteractListener;
    }

    public void updateLayout(int progress) {
        mTextProgress.setText("" + (progress + 1));
        float thumbX = mProgressBarContainer.getX() + mRatio * (progress + 1) - mThumb.getWidth() / 2;
        mThumb.setX(thumbX);
    }

    private void findViews() {
        mThumb = (RelativeLayout) findViewById(R.id.thumb);
        mTextProgress = (TextView) findViewById(R.id.text_progress);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBarContainer = (RelativeLayout) findViewById(R.id.progress_bar_container);
        imageThumb = (ImageView) findViewById(R.id.image_thumb);

        imageThumb.setColorFilter(ContextCompat.getColor(getContext(), R.color.essential_progress_bar_color));
    }

    private ViewTreeObserver.OnGlobalLayoutListener mProgressBarGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (mIsFirst) {
                mIsFirst = false;
                if (getmProgressBar().getMax() != 0) {
                    mRatio = (float) getmProgressBar().getWidth() / getmProgressBar().getMax();
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
                    float ratio = event.getX() / getmProgressBar().getWidth();
                    setProgress((int) (ratio * getmProgressBar().getMax()));
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
                    float ratio = (event.getRawX() - mProgressBarContainer.getX()) / getmProgressBar().getWidth();
                    int tmp = (int) (ratio * getmProgressBar().getMax());
                    if (tmp >= 0 && tmp < getmProgressBar().getMax()) {
                        setProgress(tmp);
                        mOnProgressBarInteractListener.onSeekTo(mProgress);
                    }
                }
            }
            return false;
        }
    };

    public ProgressBar getmProgressBar() {
        return mProgressBar;
    }

    public interface OnProgressBarInteractListener {
        void onSeekTo(int progress);
    }
}
