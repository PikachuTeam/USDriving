package com.essential.usdriving.ui.widget;

import android.content.Context;
import android.graphics.Color;
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
    private TextView tvAnswer, tvCheckAnswer,tvExplanation;
    private RelativeLayout layoutInformation;
    private RelativeLayout choiceLayout;
    private AppCompatCheckBox checkBox;
    private OnAnswerChooseListener listener;
    private String EXPLANATION="Explanation:";
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

//    public void setActive(boolean isActive, boolean checkRightAnswer, String explanation,boolean checkNullExplanation) {
//        checkBox.setChecked(isActive);
//        if(isActive){
//            if(checkRightAnswer){
//                tvCheckAnswer.setText(context.getString(R.string.written_test_correct_answer));
//                if(!checkNullExplanation){
//                    tvExplanation.setVisibility(View.VISIBLE);
//                    tvExplanation.setText(EXPLANATION+explanation);
//                }else{
//                    tvExplanation.setVisibility(View.GONE);
//                }
//                layoutInformation.setBackgroundColor(context.getResources().getColor(R.color.right_answer_color));
//            }else{
//                tvCheckAnswer.setText(context.getString(R.string.written_test_wrong_answer));
//                layoutInformation.setBackgroundColor(context.getResources().getColor(R.color.wrong_answer_color));
//            }
//            layoutInformation.setVisibility(View.VISIBLE);
//        }else{
//            layoutInformation.setVisibility(View.GONE);
//        }
//    }

    public void setCorrectChoice(boolean isActive, boolean isCorrect, String explanation,boolean checkNullExplanation) {
        if(isActive){
            if (isCorrect) {
                if (!checkNullExplanation) {
                    setVisible(tvExplanation,true);
                    setExplanation(explanation);
                } else {
                    setVisible(tvExplanation,false);
                }
                setColorLayout(context.getString(R.string.written_test_correct_answer),
                        context.getResources().getColor(R.color.right_answer_color));
                setVisible(layoutInformation,true);
            } else {
                setColorLayout(context.getString(R.string.written_test_wrong_answer),
                        context.getResources().getColor(R.color.wrong_answer_color));
                setVisible(layoutInformation,true);
            }
        }else{
            setVisible(layoutInformation,false);
        }

    }
    public void setVisible(View v, boolean isVisible){
        if(isVisible){
            v.setVisibility(View.VISIBLE);
        }else{
            v.setVisibility(View.GONE);
        }
    }

    public void setColorLayout(String answer, int color){
        tvCheckAnswer.setText(answer);
        layoutInformation.setBackgroundColor(color);
    }
    public void setExplanation(String explanation){
        tvExplanation.setText(explanation);
    }
    public void setActive(boolean isActive) {
        checkBox.setChecked(isActive);

    }

    private void findViews() {
        tvAnswer = (TextView) view.findViewById(R.id.textViewAnswer);
        tvCheckAnswer=(TextView) view.findViewById(R.id.text_checkAnswer);
        tvExplanation=(TextView) view.findViewById(R.id.text_Explanation);
        choiceLayout = (RelativeLayout) view.findViewById(R.id.choiceLayout);
        checkBox = (AppCompatCheckBox) view.findViewById(R.id.checkBox);
        layoutInformation= (RelativeLayout) view.findViewById(R.id.layout_information);

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