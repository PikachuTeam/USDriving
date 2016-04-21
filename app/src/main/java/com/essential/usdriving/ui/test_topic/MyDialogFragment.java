package com.essential.usdriving.ui.test_topic;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.entity.Question;

public class MyDialogFragment extends DialogFragment {
    TextView tv_DialogContentQuestion;
    ImageView img_DialogImageQuestion;
    TextView tv_Note;
    TextView tv_AnswerA;
    TextView tv_AnswerB;
    TextView tv_AnswerC;
    TextView tv_AnswerD;
     Question  Question;
    LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.test_topic_question_dialog, container, false);
        findViewDialogId(rootView);
        GetandSetData();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);;
        return rootView;
    }

    public void GetandSetData(){
        Bundle bundle =this.getArguments();
        Question=bundle.getParcelable(TestTopic_StudyAllInOnePage_Fragment.KEY_DIALOG);
        if(Question.image==null){
            img_DialogImageQuestion.setVisibility(View.GONE);
        }else {
            img_DialogImageQuestion.setImageBitmap(Question.image);
            img_DialogImageQuestion.setVisibility(View.VISIBLE);
        }
        if(Question.choiceD==null){
            tv_AnswerD.setVisibility(View.GONE);
        }else {
            tv_AnswerD.setText("D."+Question.choiceD);
            tv_AnswerD.setVisibility(View.VISIBLE);
        }
        tv_DialogContentQuestion.setText(Question.question);
        tv_AnswerA.setText("A."+Question.choiceA);
        tv_AnswerB.setText("B."+Question.choiceB);
        tv_AnswerC.setText("C."+Question.choiceC);

        if(Question.explanation!=null){
            tv_Note.setText("Explanation:"+Question.explanation);
        }else{
            tv_Note.setText("");
        }

        switch (Question.correctAnswer){
            case 0:
                tv_AnswerA.setTextColor(getResources().getColor(R.color.right_answer_color));
                break;
            case 1:
                tv_AnswerB.setTextColor(getResources().getColor(R.color.right_answer_color));
                ;
                break;
            case 2:
                tv_AnswerC.setTextColor(getResources().getColor(R.color.right_answer_color));
                ;
                break;
            case 3:
                tv_AnswerD.setTextColor(getResources().getColor(R.color.right_answer_color));
                ;
                break;
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void findViewDialogId(View rootview){
        tv_DialogContentQuestion = (TextView) rootview.findViewById(R.id.tv_DialogContentQuestion);
        img_DialogImageQuestion = (ImageView) rootview.findViewById(R.id.img_DialogImageQuestion);
        tv_Note = (TextView)  rootview.findViewById(R.id.tv_Explain);
        tv_AnswerA = (TextView)  rootview.findViewById(R.id.tv_AnswerA);
        tv_AnswerB = (TextView) rootview.findViewById(R.id.tv_AnswerB);
        tv_AnswerC = (TextView) rootview. findViewById(R.id.tv_AnswerC);
        tv_AnswerD = (TextView) rootview. findViewById(R.id.tv_AnswerD);
        layout= (LinearLayout) rootview. findViewById(R.id.linearLayoutDialog);

    }


}