package com.essential.usdriving.ui.test_topic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.entity.Question;
import com.essential.usdriving.ui.widget.EssentialProgressBar;
import com.essential.usdriving.ui.widget.ZoomDialog;

import java.util.ArrayList;


public class TestTopic_StudyOneByOne_Fragment extends BaseFragment implements View.OnClickListener {

    ArrayList<Question> list;
    private int currentQuesIndex = 0;
    private TextView tvChoiceA, tvChoiceB, tvChoiceC, tvChoiceD, tvExplanation, tvQuestion;
    private String getTopic;
    private CardView cardPrevious;
    private CardView cardNext;
    private ImageView imageQuestion, imageZoom, btnPrevious, btnNext;
    private int type;
    private EssentialProgressBar mEssentialProgressBar;
    private int START_ID = 0, PREVIOUS_ID = 1, NEXT_ID = 2;
    private final static String PREF_POSITION = "Position";

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test_topic_study_one_by_one;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        loadData();
        currentQuesIndex = loadState();
        if (currentQuesIndex == 0) {
            disableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
        }
        if (currentQuesIndex == list.size() - 1) {
            disableButton(cardNext, btnNext, R.drawable.ic_right);
        }
        setData(currentQuesIndex);
        mEssentialProgressBar.setProgress(currentQuesIndex);
    }

    @Override
    protected String setTitle() {
        return getString(R.string.title_test_topic);
    }

    private void findViews(View rootView) {
        cardPrevious = (CardView) rootView.findViewById(R.id.btnPreviousLayout);
        cardNext = (CardView) rootView.findViewById(R.id.btnNextLayout);
        tvChoiceA = (TextView) rootView.findViewById(R.id.tvChoiceA);
        tvChoiceB = (TextView) rootView.findViewById(R.id.tvChoiceB);
        tvChoiceC = (TextView) rootView.findViewById(R.id.tvChoiceC);
        tvChoiceD = (TextView) rootView.findViewById(R.id.tvChoiceD);
        tvExplanation = (TextView) rootView.findViewById(R.id.tvExplanation);
        tvQuestion = (TextView) rootView.findViewById(R.id.tvQuestionPage);
        imageQuestion = (ImageView) rootView.findViewById(R.id.image_question_test_topic);
        imageZoom = (ImageView) rootView.findViewById(R.id.buttonZoomIn);
        imageZoom.setVisibility(View.GONE);
        btnPrevious = (ImageView) rootView.findViewById(R.id.btnPrevious);
        btnNext = (ImageView) rootView.findViewById(R.id.btnNext);
        mEssentialProgressBar = (EssentialProgressBar) rootView.findViewById(R.id.essential_progress_bar);
    }

    private void loadData() {
        list = new ArrayList<>();
        Bundle bundle = this.getArguments();
        getTopic = bundle.getString(TestTopicFragment.KEY_TEST_TOPIC_1);
        type = bundle.getInt(TestTopicFragment.KEY_TEST_TOPIC_2);
        list = DataSource.getInstance().getTopicItem(getTopic);
        computeSize(START_ID);
        mEssentialProgressBar.setMaxProgress(list.size());
        mEssentialProgressBar.setProgress(currentQuesIndex);
        mEssentialProgressBar.setOnProgressBarInteractListener(interactListener);
        cardPrevious.setOnClickListener(this);
        cardNext.setOnClickListener(this);
        imageZoom.setOnClickListener(this);
        imageQuestion.setOnClickListener(this);
    }

    private void enableButton(CardView cv, ImageView button, int image) {
        cv.setEnabled(true);
        button.setEnabled(true);
        button.setImageResource(image);
        button.setColorFilter(ContextCompat.getColor(getActivity(), R.color.enable_button), PorterDuff.Mode.SRC_ATOP);
    }

    private void disableButton(CardView cv, ImageView button, int image) {
        cv.setEnabled(false);
        button.setEnabled(false);
        button.setImageResource(image);
        button.setColorFilter(ContextCompat.getColor(getActivity(), R.color.disable_button), PorterDuff.Mode.SRC_ATOP);
    }

    private void computeSize(int id) {
        switch (id) {
            case 0:
                break;
            case 1:
                currentQuesIndex = currentQuesIndex - 1;
                break;
            case 2:
                currentQuesIndex = currentQuesIndex + 1;
                break;
        }
        setData(currentQuesIndex);
        mEssentialProgressBar.setProgress(currentQuesIndex);
    }

    public void setData(int position) {
        if (list.get(position).image != null) {
            imageQuestion.setVisibility(View.VISIBLE);
            imageQuestion.setImageBitmap(list.get(currentQuesIndex).image);
            imageZoom.setVisibility(View.VISIBLE);
        } else {
            imageQuestion.setVisibility(View.GONE);
            imageZoom.setVisibility(View.GONE);
        }
        tvQuestion.setText("" + list.get(currentQuesIndex).question);
        if (list.get(position).choiceD == null) {
            tvChoiceD.setVisibility(View.GONE);
        } else {
            tvChoiceD.setText("D." + list.get(position).choiceD);
            tvChoiceD.setVisibility(View.VISIBLE);
        }
        tvChoiceA.setText("A." + list.get(position).choiceA);
        tvChoiceB.setText("B." + list.get(position).choiceB);
        tvChoiceC.setText("C." + list.get(position).choiceC);

        if (list.get(position).explanation != null) {
            tvExplanation.setText("Explanation:" + list.get(position).explanation);
        } else {
            tvExplanation.setText("");
        }

        switch (list.get(position).correctAnswer) {
            case 0:
                tvChoiceA.setTextColor(ContextCompat.getColor(getActivity(), R.color.right_answer_color));
                tvChoiceB.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                tvChoiceC.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                tvChoiceD.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                break;
            case 1:
                tvChoiceB.setTextColor(ContextCompat.getColor(getActivity(), R.color.right_answer_color));
                tvChoiceA.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                tvChoiceC.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                tvChoiceD.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                break;
            case 2:
                tvChoiceC.setTextColor(ContextCompat.getColor(getActivity(), R.color.right_answer_color));
                tvChoiceA.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                tvChoiceB.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                tvChoiceD.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                break;
            case 3:
                tvChoiceD.setTextColor(ContextCompat.getColor(getActivity(), R.color.right_answer_color));
                tvChoiceA.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                tvChoiceC.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                tvChoiceB.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveState();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_POSITION + " " + type, currentQuesIndex);
        editor.commit();
    }

    private int loadState() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREF_POSITION + " " + type, 0);
    }

    private EssentialProgressBar.OnProgressBarInteractListener interactListener = new EssentialProgressBar.OnProgressBarInteractListener() {
        @Override
        public void onSeekTo(int progress) {
            setData(progress);
            currentQuesIndex = progress;
            if (currentQuesIndex == 0) {
                disableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
            } else {
                enableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
            }
            if (currentQuesIndex == list.size() - 1) {
                disableButton(cardNext, btnNext, R.drawable.ic_right);
            } else {
                enableButton(cardNext, btnNext, R.drawable.ic_right);
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextLayout:
                if (currentQuesIndex < list.size() - 1) {
                    computeSize(NEXT_ID);
                    if (currentQuesIndex == 1) {
                        enableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
                    }
                    if (currentQuesIndex < list.size() - 1) {
                        if (!cardNext.isEnabled()) {
                            enableButton(cardNext, btnNext, R.drawable.ic_right);
                        }
                    } else {
                        disableButton(cardNext, btnNext, R.drawable.ic_right);
                    }
                }
                break;
            case R.id.btnPreviousLayout:
                if (currentQuesIndex > 0) {
                    computeSize(PREVIOUS_ID);
                    if (currentQuesIndex == list.size() - 2) {
                        enableButton(cardNext, btnNext, R.drawable.ic_right);
                    }
                    if (currentQuesIndex > 0) {
                        if (!cardPrevious.isEnabled()) {
                            enableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
                        }
                    } else {
                        disableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
                    }
                }
                break;
            case R.id.buttonZoomIn:
            case R.id.image_question_test_topic:
                Bundle bundle = new Bundle();
                bundle.putParcelable(TestTopic_StudyAllInOnePage_Fragment.KEY_DIALOG, list.get(currentQuesIndex).image);
                ZoomDialog dialogFragment = new ZoomDialog();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "");
                break;
        }
    }
}



