package com.essential.usdriving.ui.utility;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.essential.usdriving.R;

/**
 * Created by dongc on 4/18/2016.
 */
public class DefaultItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public DefaultItemDecoration(Context context) {
        space = context.getResources().getDimensionPixelSize(R.dimen.common_size_3);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
    }
}
