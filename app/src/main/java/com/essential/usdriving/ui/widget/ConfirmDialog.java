package com.essential.usdriving.ui.widget;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.essential.usdriving.R;
import com.essential.usdriving.app.DMVActivity;
import com.essential.usdriving.ui.utility.SharedPreferenceUtil;

/**
 * Created by yue on 21/04/2016.
 */
public class ConfirmDialog extends Dialog {
    public ConfirmDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_confirm);

        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtil.getInstance(getContext()).saveRatedState();

                Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    getContext().startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
                }
                dismiss();
            }
        });

        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        changeStarColor();
    }

    private void changeStarColor() {
        ((ImageView) findViewById(R.id.image_star_1)).setColorFilter(ContextCompat.getColor(getContext(), R.color.ColorPrimaryDark));
        ((ImageView) findViewById(R.id.image_star_2)).setColorFilter(ContextCompat.getColor(getContext(), R.color.ColorPrimaryDark));
        ((ImageView) findViewById(R.id.image_star_3)).setColorFilter(ContextCompat.getColor(getContext(), R.color.ColorPrimaryDark));
        ((ImageView) findViewById(R.id.image_star_4)).setColorFilter(ContextCompat.getColor(getContext(), R.color.ColorPrimaryDark));
        ((ImageView) findViewById(R.id.image_star_5)).setColorFilter(ContextCompat.getColor(getContext(), R.color.ColorPrimaryDark));
    }
}
