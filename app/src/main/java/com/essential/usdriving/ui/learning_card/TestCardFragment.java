package com.essential.usdriving.ui.learning_card;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.entity.Card;

import java.util.ArrayList;

public class TestCardFragment extends BaseFragment {

    ArrayList<Card> listCard;
    private int currentQuesIndex = 0;
    private TextView tvAnswer, tvNumber, tvQuestion;

    private String getTopic;
    private CardView cardPrevious;
    private CardView cardNext;
    private ProgressBar progressBar;
    private ImageView imageQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected String setTitle() {
        return getString(R.string.title_learning_card);
    }

    @Override
    protected boolean enableIndicator() {
        return true;
    }


    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test_card;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        loadData();


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void findViews(View rootView) {
        progressBar = (ProgressBar) rootView.findViewById(R.id.readingProgress);
        imageQuestion = (ImageView) rootView.findViewById(R.id.image_question_card);
        tvQuestion = (TextView) rootView.findViewById(R.id.tvQuestionPage);
        tvAnswer = (TextView) rootView.findViewById(R.id.definition);
        tvNumber = (TextView) rootView.findViewById(R.id.tv_Number);
        cardPrevious = (CardView) rootView.findViewById(R.id.btnPreviousLayout);
        cardNext = (CardView) rootView.findViewById(R.id.btnNextLayout);
    }

    private void loadData() {
        listCard = new ArrayList<>();
        currentQuesIndex = 0;
        Bundle bundle = this.getArguments();
        getTopic = bundle.getString(LearningCardFragment.KEY_CARD);
        listCard = DataSource.getInstance().getCard(getTopic);
        tvQuestion.setText("" + listCard.get(0).getCardTerm());
        tvAnswer.setText(listCard.get(0).getCardDefinition());
        tvNumber.setText("  " + (1) + "  of  " + listCard.size());
        progressBar.setMax(listCard.size());
        progressBar.setProgress(currentQuesIndex + 1);
        progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float rate = event.getX() / progressBar.getWidth();
                    float tmp = listCard.size() * rate;
                    currentQuesIndex = (int) tmp;
                    tvNumber.setText("  " + (currentQuesIndex + 1) + "  of  " + listCard.size());
                    tvAnswer.setText(listCard.get(currentQuesIndex).getCardDefinition());
                    progressBar.setProgress(currentQuesIndex);
                    tvQuestion.setText("" + listCard.get(currentQuesIndex).getCardTerm());
                    if (imageQuestion != null) {
                        imageQuestion.setVisibility(View.VISIBLE);
                        imageQuestion.setImageBitmap(listCard.get(currentQuesIndex).image);
                    } else {
                        imageQuestion.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
        cardPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuesIndex > 0) {
                   // cardPrevious.setClickable(true);
                    computeSize(1);

                }else{
                   // cardPrevious.setClickable(false);
                }
            }
        });
        cardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuesIndex < listCard.size()) {
                    //cardNext.setClickable(true);
                    computeSize(2);
                }else{
                  //  cardNext.setClickable(false);
                }
            }
        });
    }


    private void computeSize(int id) {
        switch (id) {
            case 1:
                currentQuesIndex = currentQuesIndex - 1;
                break;
            case 2:
                currentQuesIndex = currentQuesIndex + 1;
                break;
        }
        tvQuestion.setText(listCard.get(currentQuesIndex).getCardTerm());
        tvAnswer.setText(listCard.get(currentQuesIndex).getCardDefinition());
        tvNumber.setText("  " + (currentQuesIndex+1) + "  of  " + listCard.size());
        progressBar.setProgress(currentQuesIndex);
        if (imageQuestion != null) {
            imageQuestion.setVisibility(View.VISIBLE);
            imageQuestion.setImageBitmap(listCard.get(currentQuesIndex).image);
        } else {
            imageQuestion.setVisibility(View.GONE);
        }
    }


}
