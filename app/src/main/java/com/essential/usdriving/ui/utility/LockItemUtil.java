package com.essential.usdriving.ui.utility;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.essential.usdriving.R;
import com.essential.usdriving.app.DMVActivity;
import com.essential.usdriving.ui.widget.ConfirmDialog;

/**
 * Created by yue on 20/04/2016.
 */
public class LockItemUtil {

    public final static String LOCKED = "locked";
    public final static String UNLOCK = "unlock";

    private Context mContext;
    private static LockItemUtil sInstance;

    private LockItemUtil(Context context) {
        mContext = context;
    }

    public static LockItemUtil getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LockItemUtil.class) {
                if (sInstance == null) {
                    sInstance = new LockItemUtil(context);
                }
            }
        }
        return sInstance;
    }

    public void lockOrUnlock(int position, ImageView lock, View container) {
        if (!DMVActivity.isRated()) {
            if (position >= DMVActivity.START_LOCKING_POSITION) {
                container.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_lock_area));
                lock.setVisibility(View.VISIBLE);
                container.setTag(LOCKED);
            } else {
                container.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.raised_button));
                lock.setVisibility(View.GONE);
                container.setTag(UNLOCK);
            }
        } else {
            container.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.raised_button));
            lock.setVisibility(View.GONE);
            container.setTag(UNLOCK);
        }
    }

    public void showDialog() {
        ConfirmDialog dialog = new ConfirmDialog(mContext);
        dialog.show();
    }
}
