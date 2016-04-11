package com.essential.usdriving.ui.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.LinearLayout;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.ui.exam_simulator.ExamSimulatorStartFragment;
import com.essential.usdriving.ui.learning_card.LearningCardFragment;
import com.essential.usdriving.ui.test_topic.TestTopicFragment;
import com.essential.usdriving.ui.videotip.VideoTipsFragment;
import com.essential.usdriving.ui.written_test.DMVWrittenTestFragment;

/**
 * Created by dongc_000 on 4/9/2016.
 */
public class HomeFragment2 extends BaseFragment {

    private LinearLayout itemWrittenTest;
    private LinearLayout itemExamSimulator;
    private LinearLayout itemLearningCard;
    private LinearLayout itemTestTopic;
    private LinearLayout itemVideoTips;

    public static String HOME_TRANSACTION = "home transaction";

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_home_2;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
    }

    @Override
    public void defineButtonResult() {

    }

    private void findViews(View rootView) {
        itemWrittenTest = (LinearLayout) rootView.findViewById(R.id.itemWrittenTest);
        itemExamSimulator = (LinearLayout) rootView.findViewById(R.id.itemExamSimulator);
        itemLearningCard = (LinearLayout) rootView.findViewById(R.id.itemLearningCard);
        itemTestTopic = (LinearLayout) rootView.findViewById(R.id.itemTestTopic);
        itemVideoTips = (LinearLayout) rootView.findViewById(R.id.itemVideoTips);

        itemWrittenTest.setOnClickListener(listItemClickListener);
        itemExamSimulator.setOnClickListener(listItemClickListener);
        itemLearningCard.setOnClickListener(listItemClickListener);
        itemTestTopic.setOnClickListener(listItemClickListener);
        itemVideoTips.setOnClickListener(listItemClickListener);
    }

    private View.OnClickListener listItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == itemWrittenTest) {
                replaceFragment(new DMVWrittenTestFragment(), getString(R.string.written_test_result_title));
            } else if (v == itemExamSimulator) {
                replaceFragment(new ExamSimulatorStartFragment(), HOME_TRANSACTION);
            } else if (v == itemTestTopic) {
                replaceFragment(new TestTopicFragment(), getString(R.string.title_learning_card));
            } else if (v == itemLearningCard) {
                replaceFragment(new LearningCardFragment(), getString(R.string.title_learning_card));
            } else if (v == itemVideoTips) {
                replaceFragment(new VideoTipsFragment(), getString(R.string.title_video_tips));
            }
        }
    };

    @Override
    protected boolean enableFloatingActionButton() {
        return true;
    }
}
