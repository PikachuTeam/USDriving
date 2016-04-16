package com.essential.usdriving.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essential.usdriving.R;

/**
 * Created by the_e_000 on 8/5/2015.
 */
public class AnswerChoicesItem implements View.OnClickListener {
    private Context context;
    private View view;

    private int position;
    private TextView tvAnswer;

    private RelativeLayout choiceLayout;
    private AppCompatCheckBox checkBox;
    private OnAnswerChooseListener listener;
    private int index;

    public AnswerChoicesItem(Context context, int index) {
        this.context = context;
        this.index = index;
        view = View.inflate(this.context, R.layout.written_test_list_item, null);
        findViews();
    }

    public void setOnAnswerChooseListener(OnAnswerChooseListener listener) {
        this.listener = listener;
    }
    public OnAnswerChooseListener getListener(){
        return  listener;
    }
    public View getView() {
        return this.view;
    }

    public int getPosition() {
        return position;
    }

    public int getIndex() {
        return index;
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

    public void setActive(boolean isActive) {
        checkBox.setChecked(isActive);
    }


    private void findViews() {
        tvAnswer = (TextView) view.findViewById(R.id.textViewAnswer);
        choiceLayout = (RelativeLayout) view.findViewById(R.id.choiceLayout);
        checkBox = (AppCompatCheckBox) view.findViewById(R.id.checkBox);
        choiceLayout.setOnClickListener(this);
        checkBox.setOnClickListener(this);
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
