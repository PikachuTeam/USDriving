package com.essential.usdriving.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.entity.BaseEntity;
import com.essential.usdriving.entity.Question;

/**
 * Created by the_e_000 on 8/13/2015.
 */
public class CustomDialog extends Dialog {

    private Context context;
    private View view;

    private ImageView imgQuestion;
    private TextView tvQuestion;
    private TextView tvChoiceA;
    private TextView tvChoiceB;
    private TextView tvChoiceC;
    private TextView tvChoiceD;
    private TextView tvExplanation;
    private TextView tvAnswer;
    private LinearLayout layout;

    private Question question;

    public CustomDialog(Context context, Question question) {
        super(context);
        this.context = context;
        view = View.inflate(this.context, R.layout.dialog_written_test, null);
        this.question = question;
        findViews(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setCanceledOnTouchOutside(true);
        setContentView(view);

        if (question.image != null) {
            imgQuestion.setVisibility(View.VISIBLE);
            imgQuestion.setImageBitmap(question.image);
        } else {
            imgQuestion.setVisibility(View.GONE);
        }

        if (question.explanation == null) {
            tvExplanation.setVisibility(View.GONE);
        } else {
            tvExplanation.setText("Explanation :"+question.explanation);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvQuestion.setText(question.question);
        tvChoiceA.setText("A. " + question.choiceA);
        tvChoiceB.setText("B. " + question.choiceB);
        tvChoiceC.setText("C. " + question.choiceC);
        if (question.choiceD != null) {
            tvChoiceD.setText("D. " + question.choiceD);
        } else {
            tvChoiceD.setVisibility(View.GONE);
        }

        switch (question.correctAnswer) {
            case 0:
                tvChoiceA.setTextColor(this.context.getResources().getColor(R.color.right_answer_color));
                break;
            case 1:
                tvChoiceB.setTextColor(this.context.getResources().getColor(R.color.right_answer_color));
                break;
            case 2:
                tvChoiceC.setTextColor(this.context.getResources().getColor(R.color.right_answer_color));
                break;
            case 3:
                tvChoiceD.setTextColor(this.context.getResources().getColor(R.color.right_answer_color));
                break;
        }

        if (question.myAnswer == BaseEntity.ANSWER_NOT_CHOOSE) {
            tvAnswer.setText("Not answered");
            tvAnswer.setTextColor(this.context.getResources().getColor(R.color.result_not_answer));
        } else if (question.myAnswer == question.correctAnswer) {
            switch (question.correctAnswer) {
                case 0:
                    tvAnswer.setText("A");
                    break;
                case 1:
                    tvAnswer.setText("B");
                    break;
                case 2:
                    tvAnswer.setText("C");
                    break;
                case 3:
                    tvAnswer.setText("D");
                    break;
            }
            tvAnswer.setTextColor(this.context.getResources().getColor(R.color.right_answer_color));
        } else {
            switch (question.myAnswer) {
                case 0:
                    tvAnswer.setText("A");
                    break;
                case 1:
                    tvAnswer.setText("B");
                    break;
                case 2:
                    tvAnswer.setText("C");
                    break;
                case 3:
                    tvAnswer.setText("D");
                    break;
            }
            tvAnswer.setTextColor(ContextCompat.getColor(context, R.color.wrong_answer_color));
        }
    }

    private void findViews(View view) {
        imgQuestion = (ImageView) view.findViewById(R.id.imgQuestion);
        tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        tvChoiceA = (TextView) view.findViewById(R.id.tvChoiceA);
        tvChoiceB = (TextView) view.findViewById(R.id.tvChoiceB);
        tvChoiceC = (TextView) view.findViewById(R.id.tvChoiceC);
        tvChoiceD = (TextView) view.findViewById(R.id.tvChoiceD);
        tvExplanation = (TextView) view.findViewById(R.id.tvExplanation);
        tvAnswer = (TextView) view.findViewById(R.id.tvAnswer);
        layout = (LinearLayout) view.findViewById(R.id.layout_written_dialog);
    }
}
