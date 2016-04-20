package com.essential.usdriving.ui.exam_simulator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;

/**
 * Created by the_e_000 on 8/14/2015.
 */
public class ExamSimulatorStartFragment extends BaseFragment implements View.OnClickListener {

    public final static String TRANSACTION_NAME = "Start Exam";
    public final static String TAG = "Start Fragment";

    private TextView btnStart;
    private LinearLayout layoutNotShowAgain;
    private ImageView imgTick;
    private int isShownRule = 1;

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_exam_simulator_start;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

    @Override
    protected String setTitle() {
        return getString(R.string.title_exam_simulator);
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    public void onClick(View v) {
        if (v == btnStart) {
            if (imgTick.getVisibility() == View.VISIBLE) {
                SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
                editor.putInt(getString(R.string.is_shown_again), isShownRule);
                editor.commit();
            }
            replaceFragment(new ExamSimulatorDoExamFragment(), TAG, TRANSACTION_NAME);
        } else if (v == layoutNotShowAgain) {
            if (imgTick.getVisibility() == View.VISIBLE) {
                imgTick.setVisibility(View.GONE);
                isShownRule = 1;
            } else {
                imgTick.setVisibility(View.VISIBLE);
                isShownRule = 0;
            }
        }
    }

    private void findViews(View rootView) {
        btnStart = (TextView) rootView.findViewById(R.id.btnStart);
        layoutNotShowAgain = (LinearLayout) rootView.findViewById(R.id.layoutNotShowAgain);
        imgTick = (ImageView) rootView.findViewById(R.id.imgTick);

        btnStart.setOnClickListener(this);
        layoutNotShowAgain.setOnClickListener(this);
    }
}
