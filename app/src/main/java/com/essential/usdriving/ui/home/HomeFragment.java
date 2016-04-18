package com.essential.usdriving.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.ui.exam_simulator.ExamSimulatorDoExamFragment;
import com.essential.usdriving.ui.exam_simulator.ExamSimulatorStartFragment;
import com.essential.usdriving.ui.learning_card.LearningCardFragment;
import com.essential.usdriving.ui.test_topic.TestTopicFragment;
import com.essential.usdriving.ui.ultility.SharedPreferenceUtil;
import com.essential.usdriving.ui.videotip.VideoTipsFragment;
import com.essential.usdriving.ui.written_test.DMVWrittenTestFragment;

/**
 * Created by dongc_000 on 4/9/2016.
 */
public class HomeFragment extends BaseFragment {

    public final static String HOME_TRANSACTION = "home transaction";
    public final static String PREF_CURRENT_STATE = "current state";

    private LinearLayout itemWrittenTest;
    private LinearLayout itemExamSimulator;
    private LinearLayout itemLearningCard;
    private LinearLayout itemTestTopic;
    private LinearLayout itemVideoTips;
    private AppCompatSpinner mSpinner;

    private int mCurrentState;
    private int mTmp;

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        populateSpinner();
        mSpinner.setOnItemSelectedListener(spinnerItemSelectedListener);
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    protected boolean enableFloatingActionButton() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadState();
        mSpinner.setSelection(mCurrentState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCurrentState != mTmp) {
            saveState();
        }
    }

    private void findViews(View rootView) {
        itemWrittenTest = (LinearLayout) rootView.findViewById(R.id.itemWrittenTest);
        itemExamSimulator = (LinearLayout) rootView.findViewById(R.id.itemExamSimulator);
        itemLearningCard = (LinearLayout) rootView.findViewById(R.id.itemLearningCard);
        itemTestTopic = (LinearLayout) rootView.findViewById(R.id.itemTestTopic);
        itemVideoTips = (LinearLayout) rootView.findViewById(R.id.itemVideoTips);
        mSpinner = (AppCompatSpinner) rootView.findViewById(R.id.spinner_state);

        itemWrittenTest.setOnClickListener(listItemClickListener);
        itemExamSimulator.setOnClickListener(listItemClickListener);
        itemLearningCard.setOnClickListener(listItemClickListener);
        itemTestTopic.setOnClickListener(listItemClickListener);
        itemVideoTips.setOnClickListener(listItemClickListener);
    }

    private void loadState() {
        mCurrentState = SharedPreferenceUtil.getInstance(getActivity()).getSharedPreferences().getInt(PREF_CURRENT_STATE, 0);
        mTmp = mCurrentState;
    }

    private void saveState() {
        SharedPreferenceUtil.getInstance(getActivity()).getEditor().putInt(PREF_CURRENT_STATE, mCurrentState);
        SharedPreferenceUtil.getInstance(getActivity()).getEditor().commit();
    }

    private void populateSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner_state, DataSource.getInstance().getStates());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
    }

    private View.OnClickListener listItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == itemWrittenTest) {
                replaceFragment(new DMVWrittenTestFragment(), HOME_TRANSACTION);
            } else if (v == itemExamSimulator) {
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                if (sharedPreferences.getInt(getString(R.string.is_shown_again), 1) == 1) {
                    replaceFragment(new ExamSimulatorStartFragment(), HOME_TRANSACTION);
                } else {
                    replaceFragment(new ExamSimulatorDoExamFragment(), HOME_TRANSACTION, HOME_TRANSACTION);
                }
            } else if (v == itemTestTopic) {
                replaceFragment(new TestTopicFragment(), getString(R.string.title_test_topic));
            } else if (v == itemLearningCard) {
                replaceFragment(new LearningCardFragment(), getString(R.string.title_learning_card));
            } else if (v == itemVideoTips) {
                replaceFragment(new VideoTipsFragment(), getString(R.string.title_video_tips));
            }
        }
    };

    private AdapterView.OnItemSelectedListener spinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mCurrentState = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
