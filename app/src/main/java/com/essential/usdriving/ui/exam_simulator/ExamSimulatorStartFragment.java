package com.essential.usdriving.ui.exam_simulator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.ui.home.HomeFragment;

/**
 * Created by the_e_000 on 8/14/2015.
 */
public class ExamSimulatorStartFragment extends BaseFragment implements View.OnClickListener {

    public final static String TRANSACTION_NAME = "Start Exam";
    public final static String TAG = "Start Fragment";

    private LinearLayout layoutRule;
    private Button btnStart;
    private LinearLayout layoutNotShowAgain;
    private LinearLayout checkboxNotShowAgain;
    private ImageView imgTick;

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
        isShownRule();

    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
//        replaceFragment(new HomeFragment(), getString(R.string.topic));
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
                editor.putInt(getString(R.string.is_shown_again), 0);
                editor.commit();
            }
            replaceFragment(new ExamSimulatorDoExamFragment(), TAG, TRANSACTION_NAME);
        } else if (v == checkboxNotShowAgain) {
            if (imgTick.getVisibility() == View.VISIBLE) {
                imgTick.setVisibility(View.GONE);
            } else {
                imgTick.setVisibility(View.VISIBLE);
            }
        }
    }

    private void findViews(View rootView) {
        layoutRule = (LinearLayout) rootView.findViewById(R.id.layoutRule);
        btnStart = (Button) rootView.findViewById(R.id.btnStart);
        layoutNotShowAgain = (LinearLayout) rootView.findViewById(R.id.layoutNotShowAgain);
        checkboxNotShowAgain = (LinearLayout) rootView.findViewById(R.id.checkboxNotShowAgain);
        imgTick = (ImageView) rootView.findViewById(R.id.imgTick);

        btnStart.setOnClickListener(this);
        checkboxNotShowAgain.setOnClickListener(this);
    }

    private void isShownRule() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 1;
        int isShown = sharedPref.getInt(getString(R.string.is_shown_again), defaultValue);
        if (isShown == 1) {
            layoutNotShowAgain.setVisibility(View.VISIBLE);
            layoutRule.setVisibility(View.VISIBLE);
        } else {
            layoutNotShowAgain.setVisibility(View.GONE);
            layoutRule.setVisibility(View.GONE);
        }
    }
}
