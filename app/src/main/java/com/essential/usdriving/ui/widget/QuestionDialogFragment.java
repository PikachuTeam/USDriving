package com.essential.usdriving.ui.widget;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.essential.usdriving.R;
import com.essential.usdriving.ui.written_test.DMVWrittenTestFragment;

public class QuestionDialogFragment extends DialogFragment {

    ImageView img_DialogImageQuestion;
    QuestionDialogFragment question;
    Bitmap bm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question_dialog, container, false);
        findViewDialogId(rootView);
        Bundle bundle = this.getArguments();
/*
        for (String key : bundle.keySet()) {
            if (key.equals(DMVWrittenTestFragment.KEY_DIALOG)) {

            } else {
                bm = bundle.getParcelable(DMVWrittenTestFragment.KEY_DIALOG);
            }
        }
*/
        bm = bundle.getParcelable(DMVWrittenTestFragment.KEY_DIALOG);
        img_DialogImageQuestion.setImageBitmap(bm);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ;
        return rootView;
    }


    private void findViewDialogId(View rootview) {

        img_DialogImageQuestion = (ImageView) rootview.findViewById(R.id.image_question_dialog);


    }

    private QuestionDialogFragment getQuestionDialog() {

        return question;
    }

}