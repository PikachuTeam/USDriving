package com.essential.usdriving.ui.learning_card;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.entity.Card;
import com.essential.usdriving.ui.test_topic.TestTopic_StudyAllInOnePage_Fragment;
import com.essential.usdriving.ui.widget.EssentialProgressBar;
import com.essential.usdriving.ui.widget.ZoomDialog;

import java.util.ArrayList;

public class TestCardFragment extends BaseFragment implements View.OnClickListener {

    ArrayList<Card> listCard;
    private int currentQuesIndex = 0;
    private TextView tvAnswer, tvQuestion;
    private String getTopic;
    private CardView cardPrevious;
    private CardView cardNext;
    private ImageView imageQuestion, imageZoom, btnPrevious, btnNext;
    private EssentialProgressBar mEssentialProgressBar;

    private int type;
    private int STAT_ID = 0, PREVIOUS_ID = 1, NEXT_ID = 2;
    private final static String PREF_POSITION = "Position";

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
            disableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
        }
        if (currentQuesIndex == listCard.size() - 1) {
            disableButton(cardNext, btnNext, R.drawable.ic_right);
        }
        mEssentialProgressBar.setProgress(currentQuesIndex);
        setData(currentQuesIndex);
        mEssentialProgressBar.setOnProgressBarInteractListener(mEssentialProgressBarInteractListener);
    }

    private void findViews(View rootView) {
        imageQuestion = (ImageView) rootView.findViewById(R.id.image_question_card);
        tvQuestion = (TextView) rootView.findViewById(R.id.tvQuestionPage);
        tvAnswer = (TextView) rootView.findViewById(R.id.definition);
        cardPrevious = (CardView) rootView.findViewById(R.id.btnPreviousLayout);
        cardNext = (CardView) rootView.findViewById(R.id.btnNextLayout);
        imageZoom = (ImageView) rootView.findViewById(R.id.buttonZoomIn);
        btnNext = (ImageView) rootView.findViewById(R.id.btnNext);
        btnPrevious = (ImageView) rootView.findViewById(R.id.btnPrevious);
        mEssentialProgressBar = (EssentialProgressBar) rootView.findViewById(R.id.essential_progress_bar);
    }

    private void loadData() {
        listCard = new ArrayList<>();
        currentQuesIndex = 0;
        Bundle bundle = this.getArguments();
        getTopic = bundle.getString(LearningCardFragment.KEY_CARD);
        type = bundle.getInt(LearningCardFragment.KEY_CARD_TOPIC);
        listCard = DataSource.getInstance().getCard(getTopic);
        computeSize(STAT_ID);
        mEssentialProgressBar.setMaxProgress(listCard.size());
        cardPrevious.setOnClickListener(this);
        cardNext.setOnClickListener(this);
        imageQuestion.setOnClickListener(this);
        imageZoom.setOnClickListener(this);
    }

    private void enableButton(CardView cv, ImageView button, int image) {
        cv.setEnabled(true);
        button.setEnabled(true);
        button.setImageResource(image);
        button.setColorFilter(ContextCompat.getColor(getActivity(), R.color.enable_button), PorterDuff.Mode.SRC_ATOP);
    }

    private void disableButton(CardView cv, ImageView button, int image) {
        cv.setEnabled(false);
        button.setEnabled(false);
        button.setImageResource(image);
        button.setColorFilter(ContextCompat.getColor(getActivity(), R.color.disable_button), PorterDuff.Mode.SRC_ATOP);
    }

    private void setData(int position) {
        if (listCard.get(position).image != null) {
            imageQuestion.setVisibility(View.VISIBLE);
            imageQuestion.setImageBitmap(listCard.get(currentQuesIndex).image);
            imageZoom.setVisibility(View.VISIBLE);
        } else {
            imageQuestion.setVisibility(View.GONE);
            imageZoom.setVisibility(View.GONE);
        }
        tvQuestion.setText(listCard.get(position).getCardTerm());
        tvAnswer.setText(listCard.get(position).getCardDefinition());
    }

    private void computeSize(int id) {
        switch (id) {
            case 0:
                break;
            case 1:
                currentQuesIndex = currentQuesIndex - 1;
                break;
            case 2:
                currentQuesIndex = currentQuesIndex + 1;
                break;
        }
        setData(currentQuesIndex);
        mEssentialProgressBar.setProgress(currentQuesIndex);
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
        editor.putInt(PREF_POSITION + " " + type, currentQuesIndex);
        editor.commit();
    }

    private int loadState() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREF_POSITION + " " + type, 0);
    }

    private EssentialProgressBar.OnProgressBarInteractListener mEssentialProgressBarInteractListener = new EssentialProgressBar.OnProgressBarInteractListener() {
        @Override
        public void onSeekTo(int progress) {
            currentQuesIndex = progress;
            setData(progress);
            if (currentQuesIndex == 0) {
                disableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
            } else {
                enableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
            }
            if (currentQuesIndex == listCard.size() - 1) {
                disableButton(cardNext, btnNext, R.drawable.ic_right);
            } else {
                enableButton(cardNext, btnNext, R.drawable.ic_right);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextLayout:
                if (currentQuesIndex < listCard.size() - 1) {
                    computeSize(NEXT_ID);
                    if (currentQuesIndex == 1) {
                        enableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
                    }
                    if (currentQuesIndex < listCard.size() - 1) {
                        if (!cardNext.isEnabled()) {
                            enableButton(cardNext, btnNext, R.drawable.ic_right);
                        }
                    } else {
                        disableButton(cardNext, btnNext, R.drawable.ic_right);
                    }
                }
                break;
            case R.id.btnPreviousLayout:
                if (currentQuesIndex > 0) {
                    computeSize(PREVIOUS_ID);
                    if (currentQuesIndex == listCard.size() - 2) {
                        enableButton(cardNext, btnNext, R.drawable.ic_right);
                    }
                    if (currentQuesIndex > 0) {
                        if (!cardPrevious.isEnabled()) {
                            enableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
                        }
                    } else {
                        disableButton(cardPrevious, btnPrevious, R.drawable.ic_left);
                    }
                }
                break;
            case R.id.buttonZoomIn:
            case R.id.image_question_card:
                Bundle bundle = new Bundle();
                bundle.putParcelable(TestTopic_StudyAllInOnePage_Fragment.KEY_DIALOG, listCard.get(currentQuesIndex).image);
                ZoomDialog dialogFragment = new ZoomDialog();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "");
                break;

        }
    }
}
