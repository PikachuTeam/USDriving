package com.essential.usdriving.ui.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.usdriving.R;

/**
 * Created by the_e_000 on 8/5/2015.
 */
public class AnswerChoicesItem implements View.OnClickListener {
    private Context context;
    private View view;

    private int position;
    private TextView tvAnswer, tvExplanation;
    private View btnActive;
    private LinearLayout layoutCorrectAnswer, layoutWrongAnswer;
    private LinearLayout btnChoose;
    private View layoutExplanation;
    private ViewGroup layoutChoiceContent;

    private OnAnswerChooseListener listener;

    public AnswerChoicesItem(Context context) {
        this.context = context;
        view = View.inflate(this.context, R.layout.written_test_list_item, null);
        findViews();
    }

    public void setOnAnswerChooseListener(OnAnswerChooseListener listener) {
        this.listener = listener;
    }

    public View getView() {
        return this.view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAnswer(String answer) {
        tvAnswer.setText(answer);
    }

    public String getAnswer() {
        return tvAnswer.getText().toString();
    }

    public void setActive(int state) {
        btnActive.setAlpha(state);
    }

    public void setCorrectAnswer(boolean isCorrect, boolean hasExplanation) {
        if (isCorrect) {
            if (hasExplanation) {
                layoutExplanation.setVisibility(View.VISIBLE);
            } else {
                layoutExplanation.setVisibility(View.GONE);
            }
            layoutCorrectAnswer.setVisibility(View.VISIBLE);
            layoutWrongAnswer.setVisibility(View.GONE);
        } else {
            layoutCorrectAnswer.setVisibility(View.GONE);
            layoutWrongAnswer.setVisibility(View.VISIBLE);
        }
    }

    public void setDefault() {
        layoutCorrectAnswer.setVisibility(View.GONE);
        layoutWrongAnswer.setVisibility(View.GONE);
    }

    public void addLayoutTransition(){
        layoutChoiceContent.setLayoutTransition(new LayoutTransition());
    }

    public void setExplanation(String explanation) {
        tvExplanation.setText(explanation);
    }

    private void findViews() {
        tvAnswer = (TextView) view.findViewById(R.id.tvAnswer);
        tvExplanation = (TextView) view.findViewById(R.id.tvExplanation);
        btnActive = view.findViewById(R.id.btnActive);
        layoutCorrectAnswer = (LinearLayout) view.findViewById(R.id.layoutCorrectAnswer);
        layoutWrongAnswer = (LinearLayout) view.findViewById(R.id.layoutWrongAnswer);
        btnChoose = (LinearLayout) view.findViewById(R.id.btnChoose);
        layoutExplanation = view.findViewById(R.id.layoutExplanation);
        layoutChoiceContent= (ViewGroup) view.findViewById(R.id.layoutChoiceContent);

        view.findViewById(R.id.layoutChoice).findViewById(R.id.view_highlight).setOnClickListener(this);
        btnChoose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (this.listener != null) {
            listener.onAnswerChoose(this);
        }
    }

    public interface OnAnswerChooseListener {
        void onAnswerChoose(AnswerChoicesItem item);
    }
}
