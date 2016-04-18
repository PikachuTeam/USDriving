package com.essential.usdriving.ui.ultility;

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
        space = context.getResources().getInteger(R.integer.recycler_view_item_default_space);
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
