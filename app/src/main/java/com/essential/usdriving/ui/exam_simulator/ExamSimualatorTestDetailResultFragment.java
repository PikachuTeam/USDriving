package com.essential.usdriving.ui.exam_simulator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.entity.Question;
import com.essential.usdriving.ui.widget.CustomDialog;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by yue on 25/01/2016.
 */
public class ExamSimualatorTestDetailResultFragment extends BaseFragment {
    private ArrayList<Question> questions;
    private int state;
    private TextView tvType;
    private ListView listItem;
    private WrittenTestResultDetailListAdapter adapter;


    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }
    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test_detail_result;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        switch (state) {
            case 0:
                tvType.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.right_answer_color));
                tvType.setText("Correct Answers");
                break;
            case 1:
                tvType.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.wrong_answer_color));
                tvType.setText("Wrong Answers");
                break;
            case 2:
                tvType.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.result_not_answer));
                tvType.setText("Not Answered Questions");
                break;
        }

        adapter = new WrittenTestResultDetailListAdapter(this.getActivity(), questions);
        listItem.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected String setTitle() {
        return getString(R.string.detail);
    }

    @Override
    public void defineButtonResult() {

    }

    private void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            questions = bundle.getParcelableArrayList("Questions");
            state = bundle.getInt("State");
        }
    }

    private void findViews(View view) {
        tvType = (TextView) view.findViewById(R.id.tvType);
        listItem = (ListView) view.findViewById(R.id.dmvWrittenTestList);
    }

    private static class WrittenTestResultDetailListAdapter extends BaseAdapter {

        private ArrayList<Question> questions;
        private Context context;

        public WrittenTestResultDetailListAdapter(Context context, ArrayList<Question> questions) {
            this.context = context;
            this.questions = questions;
        }

        @Override
        public int getCount() {
            return questions.size();
        }

        @Override
        public Object getItem(int position) {
            return questions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return questions.get(position).questionNo;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_test_detail_result, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvQuestionNo.setText(MessageFormat.format(context.getString(R.string.written_test_question_no), "" + questions.get(position).questionNo));
            viewHolder.tvResultQuestion.setText(questions.get(position).question);
            switch (questions.get(position).correctAnswer) {
                case 0:
                    viewHolder.tvResultAnswer.setText(questions.get(position).choiceA);
                    break;
                case 1:
                    viewHolder.tvResultAnswer.setText(questions.get(position).choiceB);
                    break;
                case 2:
                    viewHolder.tvResultAnswer.setText(questions.get(position).choiceC);
                    break;
                case 3:
                    viewHolder.tvResultAnswer.setText(questions.get(position).choiceD);
                    break;
            }
            if (questions.get(position).image != null) {
                viewHolder.imgQuestion.setVisibility(View.VISIBLE);
                viewHolder.imgQuestion.setImageBitmap(questions.get(position).image);
            } else {
                viewHolder.imgQuestion.setVisibility(View.GONE);
            }

            viewHolder.cv.setTag(questions.get(position));
            viewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Question question = (Question) v.getTag();
                    CustomDialog dialog = new CustomDialog(context, question);
                    dialog.show();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            public TextView tvQuestionNo, tvResultQuestion, tvResultAnswer;
            public ImageView imgQuestion;

            public CardView cv;

            public ViewHolder(View view) {
                tvQuestionNo = (TextView) view.findViewById(R.id.tvQuestionNo);
                tvResultQuestion = (TextView) view.findViewById(R.id.tvResultQuestion);
                tvResultAnswer = (TextView) view.findViewById(R.id.tvResultAnswer);
                imgQuestion = (ImageView) view.findViewById(R.id.imgQuestion);
                cv = (CardView) view.findViewById(R.id.cardView);
            }

        }
    }
}
