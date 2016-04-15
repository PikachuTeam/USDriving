package com.essential.usdriving.ui.test_topic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
    private TextView tvChoiceA, tvChoiceB, tvChoiceC, tvChoiceD, tvExplanation, tvNumber, tvQuestion;
    private String getTopic;
    private ColorStateList oldColors;
    private CardView cardPrevious;
    private CardView cardNext;
    private ImageView imageQuestion, imageZoom, btnPrevious, btnNext;
    private int type;
    private EssentialProgressBar mEssentialProgressBar;
    private int START_ID = 0, PREVIOUS_ID = 1, NEXT_ID = 2;

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test_topic_study_one_by_one_;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        oldColors = tvNumber.getTextColors();
        loadData();
        currentQuesIndex = loadState();
        if (currentQuesIndex == 0) {
            SetData(0);
        } else {
            SetData(currentQuesIndex);
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        tvNumber = (TextView) rootView.findViewById(R.id.tv_Number);
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
        getTopic = bundle.getString(TestTopicFragment.KEY_TESTTOPIC_1);
        type = bundle.getInt(TestTopicFragment.KEY_TESTTOPIC_2);
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
        SetData(currentQuesIndex);
        float ratio = (float) currentQuesIndex / list.size();
        int tmp = (int) (ratio * mEssentialProgressBar.getmProgressBar().getMax());
        mEssentialProgressBar.setProgress(tmp);
        mEssentialProgressBar.updateLayout(tmp);

    }

    public void SetData(int position) {
        if (list.get(position).image != null) {
            imageQuestion.setVisibility(View.VISIBLE);
            imageQuestion.setImageBitmap(list.get(currentQuesIndex).image);
            imageZoom.setVisibility(View.VISIBLE);
        } else {
            imageQuestion.setVisibility(View.GONE);
            imageZoom.setVisibility(View.GONE);
        }
        tvNumber.setText("  " + (currentQuesIndex + 1) + "  of  " + list.size());
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
                tvChoiceA.setTextColor(getResources().getColor(R.color.right_answer_color));
                tvChoiceB.setTextColor(oldColors);
                tvChoiceC.setTextColor(oldColors);
                tvChoiceD.setTextColor(oldColors);
                break;
            case 1:
                tvChoiceB.setTextColor(getResources().getColor(R.color.right_answer_color));
                tvChoiceA.setTextColor(oldColors);
                tvChoiceC.setTextColor(oldColors);
                tvChoiceD.setTextColor(oldColors);

                break;
            case 2:
                tvChoiceC.setTextColor(getResources().getColor(R.color.right_answer_color));
                tvChoiceA.setTextColor(oldColors);
                tvChoiceB.setTextColor(oldColors);
                tvChoiceD.setTextColor(oldColors);
                break;
            case 3:
                tvChoiceD.setTextColor(getResources().getColor(R.color.right_answer_color));
                tvChoiceA.setTextColor(oldColors);
                tvChoiceC.setTextColor(oldColors);
                tvChoiceB.setTextColor(oldColors);
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
        switch (type) {
            case 1:
                editor.putInt(getString(R.string.position_1), currentQuesIndex);
                break;
            case 2:
                editor.putInt(getString(R.string.position_2), currentQuesIndex);
                break;
            case 3:
                editor.putInt(getString(R.string.position_3), currentQuesIndex);
                break;
            case 4:
                editor.putInt(getString(R.string.position_4), currentQuesIndex);
                break;
            case 5:
                editor.putInt(getString(R.string.position_5), currentQuesIndex);
                break;
            case 6:
                editor.putInt(getString(R.string.position_6), currentQuesIndex);
                break;
            case 7:
                editor.putInt(getString(R.string.position_7), currentQuesIndex);
                break;
            case 8:
                editor.putInt(getString(R.string.position_8), currentQuesIndex);
                break;
            case 9:
                editor.putInt(getString(R.string.position_9), currentQuesIndex);
                break;
            case 10:
                editor.putInt(getString(R.string.position_10), currentQuesIndex);
                break;
            case 11:
                editor.putInt(getString(R.string.position_11), currentQuesIndex);
                break;
        }
        editor.commit();

    }

    private int loadState() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        switch (type) {
            case 1:
                return sharedPreferences.getInt(getString(R.string.position_1), 0);

            case 2:
                return sharedPreferences.getInt(getString(R.string.position_2), 0);

            case 3:
                return sharedPreferences.getInt(getString(R.string.position_3), 0);

            case 4:
                return sharedPreferences.getInt(getString(R.string.position_4), 0);

            case 5:
                return sharedPreferences.getInt(getString(R.string.position_5), 0);

            case 6:
                return sharedPreferences.getInt(getString(R.string.position_6), 0);

            case 7:
                return sharedPreferences.getInt(getString(R.string.position_7), 0);

            case 8:
                return sharedPreferences.getInt(getString(R.string.position_8), 0);

            case 9:
                return sharedPreferences.getInt(getString(R.string.position_9), 0);

            case 10:
                return sharedPreferences.getInt(getString(R.string.position_10), 0);

            case 11:
                return sharedPreferences.getInt(getString(R.string.position_11), 0);

            default:
                return 0;
        }


    }

    private EssentialProgressBar.OnProgressBarInteractListener interactListener = new EssentialProgressBar.OnProgressBarInteractListener() {
        @Override
        public void onSeekTo(int progress) {
            SetData(progress);
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
                if (currentQuesIndex == 0) {
                    enableButton(cardPrevious, btnPrevious, R.drawable.ic_left);

                }
                if (currentQuesIndex < list.size() - 1) {
                    computeSize(NEXT_ID);
                    enableButton(cardNext, btnNext, R.drawable.ic_right);
                }else{
                    disableButton(cardNext, btnNext, R.drawable.ic_right);
                }
                break;
            case R.id.btnPreviousLayout:
                if (currentQuesIndex == list.size() - 1) {
                    enableButton(cardNext, btnNext, R.drawable.ic_right);
                }
                if (currentQuesIndex > 0) {
                    computeSize(PREVIOUS_ID);
                    enableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
                } else {
                    disableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
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



