package com.essential.usdriving.ui.widget;



import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.essential.usdriving.R;
import com.essential.usdriving.ui.test_topic.TestTopic_StudyAllInOnePage_Fragment;


public class ZoomDialog extends android.support.v4.app.DialogFragment {
    ImageView imageZoom;
    Bitmap bm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question_dialog, container, false);
        findViewDialogId(rootView);
        Bundle bundle = this.getArguments();


        bm = bundle.getParcelable(TestTopic_StudyAllInOnePage_Fragment.KEY_DIALOG);
        imageZoom.setImageBitmap(bm);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        imageZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }


    private void findViewDialogId(View rootview) {
        imageZoom = (ImageView) rootview.findViewById(R.id.image_question_dialog);
    }
}
