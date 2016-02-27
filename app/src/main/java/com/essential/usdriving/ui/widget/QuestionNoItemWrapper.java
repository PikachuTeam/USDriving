package com.essential.usdriving.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.essential.usdriving.R;

/**
 * Created by the_e_000 on 8/1/2015.
 */
public class QuestionNoItemWrapper implements View.OnClickListener {

    private Context context;
    private View view;

    private TextView textViewQuesNum;
    private View bottomLine;
    private OnItemQuestionClickListener listener;

    public QuestionNoItemWrapper(Context context) {
        this.context = context;
        view = View.inflate(this.context, R.layout.item_written_test_question_num, null);
        findViews();
    }

    private void findViews() {
        textViewQuesNum = (TextView) view.findViewById(R.id.questionNumber);
        bottomLine = view.findViewById(R.id.bottom_line);
        view.findViewById(R.id.view_highlight).setOnClickListener(this);
    }

    public void setOnItemQuestionClickListener(OnItemQuestionClickListener listener) {
        this.listener = listener;
    }

    public void setHighlight() {
        textViewQuesNum.setTextColor(ContextCompat.getColor(context, R.color.written_test_text_highlight));
    }

    public void setActive(boolean active) {
        int color = active ? R.color.written_test_color_2 : R.color.white;
        bottomLine.setBackgroundResource(color);
    }

    public void setText(String questionNumber) {
        textViewQuesNum.setText(questionNumber);
    }

    public View getView() {
        return this.view;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemQuestionClick(this);
        }
    }

    public interface OnItemQuestionClickListener {
        void onItemQuestionClick(QuestionNoItemWrapper item);
    }
}
