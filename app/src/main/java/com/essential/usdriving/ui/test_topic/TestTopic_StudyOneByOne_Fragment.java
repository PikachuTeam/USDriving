package com.essential.usdriving.ui.test_topic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.entity.Question;
import com.essential.usdriving.ui.widget.QuestionDialogFragment;
import com.essential.usdriving.ui.written_test.DMVWrittenTestFragment;

import java.util.ArrayList;


public class TestTopic_StudyOneByOne_Fragment extends BaseFragment {


    ArrayList<Question> list;
    private int currentQuesIndex = 0;
    private TextView tvChoiceA, tvChoiceB, tvChoiceC, tvChoiceD, tvExplanation, tvNumber, tvQuestion;
    private String getTopic;
    private ColorStateList oldColors;
    private CardView cardPrevious;
    private CardView cardNext;
    private ProgressBar progressBar;
    private ImageView imageQuestion, imageZoom;
    private int type;
    public final static int  TEST_TOPIC_1 = 1,TEST_TOPIC_2 = 2,TEST_TOPIC_3 = 3,TEST_TOPIC_4 = 4,TEST_TOPIC_5 = 5,TEST_TOPIC_6 = 6,
            TEST_TOPIC_7 = 7,TEST_TOPIC_8 = 8,TEST_TOPIC_9 = 9,TEST_TOPIC_10 = 10,TEST_TOPIC_11 = 11;
    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test_topic__study_one_by_one_;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        oldColors = tvNumber.getTextColors();
        loadData();
      //  currentQuesIndex = loadState();
        SetData(0);
         /*
        if(currentQuesIndex==0){


        }else{
            SetData(currentQuesIndex);
        }
*/


        // SetData(0);

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
        progressBar = (ProgressBar) rootView.findViewById(R.id.readingProgress);
        imageQuestion = (ImageView) rootView.findViewById(R.id.image_question_test_topic);
        //imageZoom = (ImageView) rootView.findViewById(R.id.buttonZoomIn);

    }

    private void loadData() {
        list = new ArrayList<>();
        currentQuesIndex = 0;
        Bundle bundle = this.getArguments();
        getTopic = bundle.getString(TestTopicFragment.KEY_TESTTOPIC);
        list = DataSource.getInstance().getTopicItem(getTopic);
        progressBar.setMax(list.size());
        progressBar.setProgress(currentQuesIndex + 1);
        progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float rate = event.getX() / progressBar.getWidth();
                    float tmp = list.size() * rate;
                    currentQuesIndex = (int) tmp;
                    progressBar.setProgress(currentQuesIndex + 1);
                    SetData(currentQuesIndex);
                }
                return false;
            }
        });
        cardPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuesIndex > 0) {
                    computeSize(1);

                } else {
                }
            }
        });
        cardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuesIndex < list.size() - 1) {

                       // cardNext.setEnabled(true);
                        computeSize(2);


                } else {
                   // cardNext.setEnabled(false);
                }
            }
        });
        /*
        imageZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(DMVWrittenTestFragment.KEY_DIALOG, list.get(currentQuesIndex).image);
                QuestionDialogFragment dialogFragment = new QuestionDialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "Question");
            }
        });
        */
    }


    private void computeSize(int id) {
        //viewOrange.setLayoutParams(new RelativeLayout.LayoutParams(viewWhite.getMeasuredWidth() * position / size,
        //viewWhite.getMeasuredHeight()));
        switch (id) {
            case 1:
                currentQuesIndex = currentQuesIndex - 1;
                break;
            case 2:
                currentQuesIndex = currentQuesIndex + 1;
                break;
        }
        SetData(currentQuesIndex);
       // tvQuestion.setText();
        //tvNumber.setText("  " + (currentQuesIndex + 1) + "  of  " + list.size());
        progressBar.setProgress(currentQuesIndex + 1);
    }

    public void SetData(int position) {
        if (imageQuestion != null) {
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
            case TEST_TOPIC_1:
                editor.putInt("Position 1", currentQuesIndex);
                break;
            case TEST_TOPIC_2:
                editor.putInt("Position 2", currentQuesIndex);
                break;
            case TEST_TOPIC_3:
                editor.putInt("Position 3", currentQuesIndex);
                break;
            case TEST_TOPIC_4:
                editor.putInt("Position 4", currentQuesIndex);
                break;
            case TEST_TOPIC_5:
                editor.putInt("Position 5", currentQuesIndex);
                break;
            case TEST_TOPIC_6:
                editor.putInt("Position 6", currentQuesIndex);
                break;
            case TEST_TOPIC_7:
                editor.putInt("Position 7", currentQuesIndex);
                break;
            case TEST_TOPIC_8:
                editor.putInt("Position 8", currentQuesIndex);
                break;
            case TEST_TOPIC_9:
                editor.putInt("Position 9", currentQuesIndex);
                break;
            case TEST_TOPIC_10:
                editor.putInt("Position 10", currentQuesIndex);
                break;
            case TEST_TOPIC_11:
                editor.putInt("Position 11", currentQuesIndex);
                break;
        }
        editor.commit();

    }

    private int loadState() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        switch (type) {
            case TEST_TOPIC_1:
                return sharedPreferences.getInt("Position 1", 0);

            case TEST_TOPIC_2:
                return sharedPreferences.getInt("Position 2", 0);

            case TEST_TOPIC_3:
                return sharedPreferences.getInt("Position 3", 0);

            case TEST_TOPIC_4:
                return sharedPreferences.getInt("Position 4", 0);

            case TEST_TOPIC_5:
                return sharedPreferences.getInt("Position 5", 0);

            case TEST_TOPIC_6:
                return sharedPreferences.getInt("Position 6", 0);

            case TEST_TOPIC_7:
                return sharedPreferences.getInt("Position 7", 0);

            case TEST_TOPIC_8:
                return sharedPreferences.getInt("Position 8", 0);

            case TEST_TOPIC_9:
                return sharedPreferences.getInt("Position 9", 0);

            case TEST_TOPIC_10:
                return sharedPreferences.getInt("Position 10", 0);

            case TEST_TOPIC_11:
                return sharedPreferences.getInt("Position 11", 0);

            default:
                return 0;
        }


    }
}



