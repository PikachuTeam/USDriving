package com.essential.usdriving.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.essential.usdriving.R;

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
                Toast.makeText(getContext(), "Button ok", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
