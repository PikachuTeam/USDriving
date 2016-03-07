package com.essential.usdriving.ui.learning_card;

import android.content.Context;
import android.content.SharedPreferences;
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
    private int type;

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
        currentQuesIndex = loadState();
        if (currentQuesIndex == 0) {
            setData(0);
        } else {
            progressBar.setProgress(currentQuesIndex + 1);
            setData(currentQuesIndex);
        }


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
        type = bundle.getInt(LearningCardFragment.KEY_CARD_TOPIC);
        listCard = DataSource.getInstance().getCard(getTopic);
        progressBar.setMax(listCard.size());
        progressBar.setProgress(currentQuesIndex + 1);
        progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float rate = event.getX() / progressBar.getWidth();
                    float tmp = listCard.size() * rate;
                    currentQuesIndex = (int) tmp;

                    setData(currentQuesIndex);
                }
                return false;
            }
        });
        cardPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuesIndex > 0) {
                    computeSize(1);

                } else {
                }
            }
        });
        cardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuesIndex < listCard.size() - 1) {
                    computeSize(2);
                } else {
                }
            }
        });
    }

    private void setData(int position) {
        if (imageQuestion != null) {
            imageQuestion.setVisibility(View.VISIBLE);
            imageQuestion.setImageBitmap(listCard.get(currentQuesIndex).image);
        } else {
            imageQuestion.setVisibility(View.GONE);
        }
        tvQuestion.setText(listCard.get(position).getCardTerm());
        tvAnswer.setText(listCard.get(position).getCardDefinition());
        tvNumber.setText("  " + (position + 1) + "  of  " + listCard.size());
        progressBar.setProgress(position + 1);
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
        setData(currentQuesIndex);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveState();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (type) {
            case 22:
                editor.putInt(getString(R.string.test_card_position_22), currentQuesIndex);
                break;
            case 23:
                editor.putInt(getString(R.string.test_card_position_23), currentQuesIndex);
                break;
            case 24:
                editor.putInt(getString(R.string.test_card_position_24), currentQuesIndex);
                break;
            case 25:
                editor.putInt(getString(R.string.test_card_position_25), currentQuesIndex);
                break;
            case 26:
                editor.putInt(getString(R.string.test_card_position_26), currentQuesIndex);
                break;
            case 27:
                editor.putInt(getString(R.string.test_card_position_27), currentQuesIndex);
                break;
            case 28:
                editor.putInt(getString(R.string.test_card_position_28), currentQuesIndex);
                break;
            case 29:
                editor.putInt(getString(R.string.test_card_position_29), currentQuesIndex);
                break;
            case 30:
                editor.putInt(getString(R.string.test_card_position_30), currentQuesIndex);
                break;
            case 31:
                editor.putInt(getString(R.string.test_card_position_31), currentQuesIndex);
                break;
            case 32:
                editor.putInt(getString(R.string.test_card_position_32), currentQuesIndex);
                break;
            case 33:
                editor.putInt(getString(R.string.test_card_position_33), currentQuesIndex);
                break;
            case 34:
                editor.putInt(getString(R.string.test_card_position_34), currentQuesIndex);
                break;
            case 35:
                editor.putInt(getString(R.string.test_card_position_35), currentQuesIndex);
                break;
            case 36:
                editor.putInt(getString(R.string.test_card_position_36), currentQuesIndex);
                break;
            case 37:
                editor.putInt(getString(R.string.test_card_position_37), currentQuesIndex);
                break;
            case 38:
                editor.putInt(getString(R.string.test_card_position_38), currentQuesIndex);
                break;
            case 39:
                editor.putInt(getString(R.string.test_card_position_39), currentQuesIndex);
                break;
            case 40:
                editor.putInt(getString(R.string.test_card_position_40), currentQuesIndex);
                break;
            case 41:
                editor.putInt(getString(R.string.test_card_position_41), currentQuesIndex);
                break;
            case 42:
                editor.putInt(getString(R.string.test_card_position_42), currentQuesIndex);
                break;
        }
        editor.commit();

    }

    private int loadState() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        switch (type) {
            case 22:
                return sharedPreferences.getInt(getString(R.string.test_card_position_22), 0);

            case 23:
                return sharedPreferences.getInt(getString(R.string.test_card_position_23), 0);

            case 24:
                return sharedPreferences.getInt(getString(R.string.test_card_position_24), 0);

            case 25:
                return sharedPreferences.getInt(getString(R.string.test_card_position_25), 0);

            case 26:
                return sharedPreferences.getInt(getString(R.string.test_card_position_26), 0);

            case 27:
                return sharedPreferences.getInt(getString(R.string.test_card_position_27), 0);

            case 28:
                return sharedPreferences.getInt(getString(R.string.test_card_position_28), 0);

            case 29:
                return sharedPreferences.getInt(getString(R.string.test_card_position_29), 0);

            case 30:
                return sharedPreferences.getInt(getString(R.string.test_card_position_30), 0);

            case 31:
                return sharedPreferences.getInt(getString(R.string.test_card_position_31), 0);

            case 32:
                return sharedPreferences.getInt(getString(R.string.test_card_position_32), 0);

            case 33:
                return sharedPreferences.getInt(getString(R.string.test_card_position_33), 0);

            case 34:
                return sharedPreferences.getInt(getString(R.string.test_card_position_34), 0);

            case 35:
                return sharedPreferences.getInt(getString(R.string.test_card_position_35), 0);

            case 36:
                return sharedPreferences.getInt(getString(R.string.test_card_position_36), 0);

            case 37:
                return sharedPreferences.getInt(getString(R.string.test_card_position_37), 0);

            case 38:
                return sharedPreferences.getInt(getString(R.string.test_card_position_38), 0);

            case 39:
                return sharedPreferences.getInt(getString(R.string.test_card_position_39), 0);

            case 40:
                return sharedPreferences.getInt(getString(R.string.test_card_position_40), 0);

            case 41:
                return sharedPreferences.getInt(getString(R.string.test_card_position_41), 0);

            case 42:
                return sharedPreferences.getInt(getString(R.string.test_card_position_42), 0);

            default:
                return 0;

        }


    }
}
