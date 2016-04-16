package com.essential.usdriving.ui.exam_simulator;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.entity.BaseEntity;
import com.essential.usdriving.entity.Question;
import com.essential.usdriving.ui.home.HomeFragment;
import com.essential.usdriving.ui.widget.WarningDialog;
import com.essential.usdriving.ui.written_test.TestDetailResultFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by the_e_000 on 8/14/2015.
 */
public class ExamSimulatorTestResultFragment extends BaseFragment implements View.OnClickListener, WarningDialog.OnDialogItemClickListener {

    private TextView tvState;
    private TextView tvCorrectAnswer;
    private TextView tvWrongAnswer;
    private TextView tvNotAnswered;
    private LinearLayout chartContainer;
    private TextView btnNewTest;
    private ArrayList<Question> questions;
    private int state = 0;
    private String totalTime;
    private TextView tvTotalTime;
    private WarningDialog dialog;
    private PieChart pieChart;
    private int totalCorrectAnswer;
    private int totalWrongAnswer;
    private int totalNotAnswered;
    private float[] yData;
    private int button = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_exam_simulator_test_result;
    }

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack(HomeFragment.HOME_TRANSACTION, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        tvTotalTime.setText(totalTime);

        init();
        chartContainer.addView(pieChart);
        pieChart.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));



        if (totalCorrectAnswer >= 21) {
            tvState.setText(getString(R.string.pass_exam));
            tvState.setTextColor(ContextCompat.getColor(getActivity(), R.color.right_answer_color));
        } else {
            tvState.setText(getString(R.string.fail_exam));
            tvState.setTextColor(ContextCompat.getColor(getActivity(), R.color.wrong_answer_color));
        }

        tvCorrectAnswer.setText(MessageFormat.format(getString(R.string.written_test_total_answer), "" + totalCorrectAnswer));
        tvWrongAnswer.setText(MessageFormat.format(getString(R.string.written_test_total_answer), "" + totalWrongAnswer));
        tvNotAnswered.setText(MessageFormat.format(getString(R.string.written_test_total_answer), "" + totalNotAnswered));

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
        if (v == btnNewTest) {
            getFragmentManager().popBackStack(ExamSimulatorStartFragment.TRANSACTION_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onDialogItemClick(int code) {
        if (code == WarningDialog.OK) {
            dialog.dismiss();
            TestDetailResultFragment fragment = new TestDetailResultFragment();
            Bundle bundle = new Bundle();
            ArrayList<Question> tmp = null;
            switch (button) {
                case 1:
                    tmp = getCorrectAnswers();
                    bundle.putInt("State", 0);
                    break;
                case 2:
                    tmp = getWrongAnswers();
                    bundle.putInt("State", 1);
                    break;
                case 3:
                    tmp = getNotAnsweredQuestions();
                    bundle.putInt("State", 2);
                    break;
            }
            bundle.putParcelableArrayList("Questions", tmp);
            fragment.setArguments(bundle);
            replaceFragment(fragment, getString(R.string.title_exam_simulator));
            state = 1;
        } else {
            dialog.dismiss();
        }
    }

    private void init() {
        totalCorrectAnswer = totalCorrectAnswer();
        totalWrongAnswer = totalWrongAnswer();
        totalNotAnswered = totalNotAnswered();

        float tmp = 100 / 30f;
        yData = new float[]{totalCorrectAnswer * tmp, totalWrongAnswer * tmp, totalNotAnswered * tmp};

        initChart();
    }

    private void initChart() {
        pieChart = new PieChart(getActivity());
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(getResources().getDimension(R.dimen.pie_chart_hole_radius));
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setRotationEnabled(false);
        pieChart.setClickable(false);

        addData();

        pieChart.getLegend().setEnabled(false);
        pieChart.getData().setDrawValues(false);
        pieChart.setDescription("");
    }

    private void addData() {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (yData[0] > 0) {
            yVals.add(new Entry(yData[0], 0));
            colors.add(new Integer(ContextCompat.getColor(getActivity(), R.color.right_answer_color)));
        }
        if (yData[1] > 0) {
            yVals.add(new Entry(yData[1], 1));
            colors.add(new Integer(ContextCompat.getColor(getActivity(), R.color.wrong_answer_color)));
        }
        if (yData[2] > 0) {
            yVals.add(new Entry(yData[2], 2));
            colors.add(new Integer(ContextCompat.getColor(getActivity(), R.color.result_not_answer)));
        }

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < yVals.size(); i++) {
            xVals.add("");
        }

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(4);
        dataSet.setSelectionShift(5);
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(9f);
        data.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            questions = bundle.getParcelableArrayList("Questions");
            totalTime = bundle.getString("Total Time");
        }
    }

    private void findViews(View rootView) {
        tvState = (TextView) rootView.findViewById(R.id.tvState);
        tvCorrectAnswer = (TextView) rootView.findViewById(R.id.tvCorrectAnswer);
        tvWrongAnswer = (TextView) rootView.findViewById(R.id.tvWrongAnswer);
        tvNotAnswered = (TextView) rootView.findViewById(R.id.tvNotAnswered);
        btnNewTest = (TextView) rootView.findViewById(R.id.btnNewTest);

        tvTotalTime = (TextView) rootView.findViewById(R.id.tvTotalTime);
        chartContainer = (LinearLayout) rootView.findViewById(R.id.chartContainer);

        btnNewTest.setOnClickListener(this);


        rootView.findViewById(R.id.btnCorrectAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExamSimualatorTestDetailResultFragment fragment = new ExamSimualatorTestDetailResultFragment();
                Bundle bundle = new Bundle();
                ArrayList<Question> tmp;
                tmp = getCorrectAnswers();
                bundle.putParcelableArrayList("Questions", tmp);
                bundle.putInt("State", 0);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getString(R.string.title_exam_simulator));
                state = 1;
            }
        });

        rootView.findViewById(R.id.btnWrongAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExamSimualatorTestDetailResultFragment fragment = new ExamSimualatorTestDetailResultFragment();
                Bundle bundle = new Bundle();
                ArrayList<Question> tmp;
                tmp = getWrongAnswers();
                bundle.putParcelableArrayList("Questions", tmp);
                bundle.putInt("State", 1);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getString(R.string.title_exam_simulator));
                state = 1;
            }
        });

        rootView.findViewById(R.id.btnNotAnswered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExamSimualatorTestDetailResultFragment fragment = new ExamSimualatorTestDetailResultFragment();
                Bundle bundle = new Bundle();
                ArrayList<Question> tmp;
                tmp = getNotAnsweredQuestions();
                bundle.putParcelableArrayList("Questions", tmp);
                bundle.putInt("State", 2);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getString(R.string.title_exam_simulator));
                state = 1;
            }
        });
    }

    private int totalCorrectAnswer() {
        int tmp = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).myAnswer == questions.get(i).correctAnswer) {
                tmp++;
            }
        }
        return tmp;
    }

    private int totalWrongAnswer() {
        int tmp = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).myAnswer != questions.get(i).correctAnswer && questions.get(i).myAnswer != BaseEntity.ANSWER_NOT_CHOOSE) {
                tmp++;
            }
        }
        return tmp;
    }

    private int totalNotAnswered() {
        int tmp = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).myAnswer == BaseEntity.ANSWER_NOT_CHOOSE) {
                tmp++;
            }
        }
        return tmp;
    }

    private ArrayList<Question> getCorrectAnswers() {
        ArrayList<Question> tmp = new ArrayList<>();
        for (Question ques : questions) {
            if (ques.myAnswer == ques.correctAnswer) {
                tmp.add(ques);
            }
        }
        return tmp;
    }

    private ArrayList<Question> getWrongAnswers() {
        ArrayList<Question> tmp = new ArrayList<>();
        for (Question ques : questions) {
            if (ques.myAnswer != ques.correctAnswer && ques.myAnswer != BaseEntity.ANSWER_NOT_CHOOSE) {
                tmp.add(ques);
            }
        }
        return tmp;
    }

    private ArrayList<Question> getNotAnsweredQuestions() {
        ArrayList<Question> tmp = new ArrayList<>();
        for (Question ques : questions) {
            if (ques.myAnswer == BaseEntity.ANSWER_NOT_CHOOSE) {
                tmp.add(ques);
            }
        }
        return tmp;
    }
}
