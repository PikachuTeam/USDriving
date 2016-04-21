package com.essential.usdriving.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.essential.usdriving.R;

/**
 * Created by the_e_000 on 8/14/2015.
 */
public class WarningDialog extends Dialog {

    public final static int OK = 1, CANCEL = 0;

    private Context context;

    private TextView tvWarning;

    private int state;

    private OnDialogItemClickListener listener;

    public WarningDialog(Context context, int state) {
        super(context);
        this.context = context;
        this.state = state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_warning);
        setCanceledOnTouchOutside(true);

        findViews();

        if (state == 1) {
            tvWarning.setText(this.context.getString(R.string.exam_simulator_dialog_text_1));
        }
//        else if(state==2) {
//            tvWarning.setText(this.context.getString(R.string.exam_simulator_dialog_text_2));
//        }
        else if (state == 3) {
            tvWarning.setText(this.context.getString(R.string.exam_simulator_dialog_text_3));
        }
    }

    public void setOnDialogItemClickListener(OnDialogItemClickListener listener) {
        this.listener = listener;
    }

    private void findViews() {
        tvWarning = (TextView) findViewById(R.id.tvWarning);

        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDialogItemClick(CANCEL);
                }
            }
        });

        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDialogItemClick(OK);
                }
            }
        });
    }

    public interface OnDialogItemClickListener {
        void onDialogItemClick(int code);
    }
}
