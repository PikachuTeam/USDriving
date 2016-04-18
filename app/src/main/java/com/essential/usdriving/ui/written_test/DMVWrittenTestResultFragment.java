package com.essential.usdriving.ui.written_test;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.entity.BaseEntity;
import com.essential.usdriving.entity.Question;
import com.essential.usdriving.ui.home.HomeFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.text.MessageFormat;
import java.util.ArrayList;

public class DMVWrittenTestResultFragment extends BaseFragment {

    private TextView tvCorrectAnswer, tvWrongAnswer, tvNotAnswered;
    private LinearLayout chartContainer;
    private ArrayList<Question> questions;
    private PieChart pieChart;
    private int totalCorrectAnswer;
    private int totalWrongAnswer;
    private int totalNotAnswered;
    private float[] yData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setHasOptionsMenu(true);
    }

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void defineButtonResult() {

    }
    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack(HomeFragment.HOME_TRANSACTION, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_dmv_written_test_result;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        init();
        chartContainer.addView(pieChart);
        pieChart.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        tvCorrectAnswer.setText(MessageFormat.format(getString(R.string.written_test_total_answer), "" + totalCorrectAnswer));
        tvWrongAnswer.setText(MessageFormat.format(getString(R.string.written_test_total_answer), "" + totalWrongAnswer));
        tvNotAnswered.setText(MessageFormat.format(getString(R.string.written_test_total_answer), "" + totalNotAnswered));
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected String setTitle() {
        return getString(R.string.title_dmv_written_test);
    }


    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            questions = bundle.getParcelableArrayList("Questions");
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
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(getResources().getDimension(R.dimen.common_size_13));
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setRotationEnabled(false);
        pieChart.setTouchEnabled(false);
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
        //  data.setValueFormatter(new PercentFormatter());
        // data.setValueTextSize(9f);
        //  data.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void findViews(View view) {
        tvCorrectAnswer = (TextView) view.findViewById(R.id.tvCorrectAnswer);
        tvWrongAnswer = (TextView) view.findViewById(R.id.tvWrongAnswer);
        tvNotAnswered = (TextView) view.findViewById(R.id.tvNotAnswered);
        chartContainer = (LinearLayout) view.findViewById(R.id.chartContainer);

        view.findViewById(R.id.btnCorrectAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestDetailResultFragment fragment = new TestDetailResultFragment();
                Bundle bundle = new Bundle();
                ArrayList<Question> tmp;
                tmp = getCorrectAnswers();
                bundle.putParcelableArrayList("Questions", tmp);
                bundle.putInt("State", 0);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getString(R.string.written_test_result_title));
            }
        });

        view.findViewById(R.id.btnWrongAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestDetailResultFragment fragment = new TestDetailResultFragment();
                Bundle bundle = new Bundle();
                ArrayList<Question> tmp;
                tmp = getWrongAnswers();
                bundle.putParcelableArrayList("Questions", tmp);
                bundle.putInt("State", 1);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getString(R.string.written_test_result_title));
            }
        });

        view.findViewById(R.id.btnNotAnswered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestDetailResultFragment fragment = new TestDetailResultFragment();
                Bundle bundle = new Bundle();
                ArrayList<Question> tmp;
                tmp = getNotAnsweredQuestions();
                bundle.putParcelableArrayList("Questions", tmp);
                bundle.putInt("State", 2);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getString(R.string.written_test_result_title));
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
