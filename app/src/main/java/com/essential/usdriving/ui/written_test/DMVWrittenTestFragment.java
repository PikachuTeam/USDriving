package com.essential.usdriving.ui.written_test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.entity.BaseEntity;
import com.essential.usdriving.entity.Question;
import com.essential.usdriving.ui.widget.AnswerChoicesItem;
import com.essential.usdriving.ui.widget.QuestionDialogFragment;
import com.essential.usdriving.ui.widget.QuestionNoItemWrapper;

import java.util.ArrayList;

public class DMVWrittenTestFragment extends BaseFragment implements ViewPager.OnPageChangeListener, AnswerChoicesItem.OnAnswerChooseListener, QuestionNoItemWrapper.OnItemQuestionClickListener, View.OnClickListener {

    private LinearLayout layoutScrollContent, layoutAnswerChoiceContent;
    private ArrayList<Question> questions;
    private ArrayList<AnswerChoicesItem> answerChoices;
    private ArrayList<QuestionNoItemWrapper> listItemQues;
    private ViewPager viewPager;
    private HorizontalScrollView horizontalScrollView;
    private Button btnResult;
    private int currentQuesIndex;
    private QuestionPagerAdapter adapter;
    public static String KEY_DIALOG = "key_dialog";
    private MenuItem menuToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void defineButtonResult() {
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.Result).setVisible(true);
        menuToolbar=menu.findItem(R.id.Result);
        menuToolbar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DMVWrittenTestResultFragment fragment = new DMVWrittenTestResultFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Questions", questions);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getString(R.string.written_test_result_title));
                return true;
            }
        });
        super.onPrepareOptionsMenu(menu);


    }
    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_dmv_written_test;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(savedInstanceState);
        addQuestionList();
        showChoices(questions.get(currentQuesIndex));

        adapter = new QuestionPagerAdapter(getActivity(), questions);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onAnswerChoose(AnswerChoicesItem item) {
        item.setActive(1);
        if (item.getPosition() == questions.get(currentQuesIndex).correctAnswer) {
            item.setCorrectAnswer(true, questions.get(currentQuesIndex).explanation != null);
        } else {
            item.setCorrectAnswer(false, false);
        }
        setOthersToDefault(item);
        questions.get(currentQuesIndex).myAnswer = item.getPosition();
        listItemQues.get(currentQuesIndex).setHighlight();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Questions", questions);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemQuestionClick(QuestionNoItemWrapper item) {
        setAllQuesNoItemInActive();
        item.setActive(true);
        int index = listItemQues.indexOf(item);
        scrollToCenter(item);
        viewPager.setCurrentItem(index, true);
        showChoices(questions.get(index));
        currentQuesIndex = index;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setAllQuesNoItemInActive();
        listItemQues.get(position).setActive(true);
        showChoices(questions.get(position));
        scrollToCenter(listItemQues.get(position));
        currentQuesIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        DMVWrittenTestResultFragment fragment = new DMVWrittenTestResultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Questions", questions);
        fragment.setArguments(bundle);
        replaceFragment(fragment, getString(R.string.written_test_result_title));
    }

    @Override
    protected String setTitle() {
        return getString(R.string.title_activity_dmv_written_test);
    }


    private void findViews(View view) {
        layoutScrollContent = (LinearLayout) view.findViewById(R.id.layoutScrollContent);
        layoutAnswerChoiceContent = (LinearLayout) view.findViewById(R.id.layoutAnswerChoiceContent);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);
        btnResult = (Button) view.findViewById(R.id.btnResult);

        btnResult.setOnClickListener(this);
    }

    private void addQuestionList() {
        for (int i = 0; i < questions.size(); i++) {
            QuestionNoItemWrapper questionNo = new QuestionNoItemWrapper(getActivity());
            questionNo.setText("" + questions.get(i).questionNo);
            layoutScrollContent.addView(questionNo.getView());
            if (i == 0) {
                questionNo.setActive(true);
            }
            if (questions.get(i).myAnswer != BaseEntity.ANSWER_NOT_CHOOSE) {
                questionNo.setHighlight();
            }
            questionNo.setOnItemQuestionClickListener(this);
            listItemQues.add(questionNo);
        }
    }

    private void showChoices(Question ques) {
        layoutAnswerChoiceContent.removeAllViews();
        layoutAnswerChoiceContent.invalidate();

        if (answerChoices != null) {
            answerChoices.removeAll(answerChoices);
        }

        for (int i = 0; i < 4; i++) {
            addItemIntoList(ques, i);
        }

        if (ques.myAnswer != BaseEntity.ANSWER_NOT_CHOOSE) {
            if (ques.myAnswer == ques.correctAnswer) {
                answerChoices.get(ques.myAnswer).setCorrectAnswer(true, ques.explanation != null);
            } else {
                answerChoices.get(ques.myAnswer).setCorrectAnswer(false, false);
            }
            answerChoices.get(ques.myAnswer).setActive(1);
        }

        for (int i = 0; i < answerChoices.size(); i++) {
            answerChoices.get(i).setOnAnswerChooseListener(this);
        }
    }

    private void addItemIntoList(Question ques, int option) {
        AnswerChoicesItem item = new AnswerChoicesItem(getActivity());
        item.setPosition(option);
        switch (option) {
            case 0:
                item.setAnswer(ques.choiceA);
                break;
            case 1:
                item.setAnswer(ques.choiceB);
                break;
            case 2:
                item.setAnswer(ques.choiceC);
                break;
            case 3:
                item.setAnswer(ques.choiceD);
                break;
        }

        if (ques.correctAnswer == option) {
            item.setExplanation(ques.explanation);
        }

        if (item.getAnswer() != "") {
            layoutAnswerChoiceContent.addView(item.getView());
            item.setDefault();
            item.addLayoutTransition();
            answerChoices.add(item);
        }
    }

    private void setOthersToDefault(AnswerChoicesItem item) {
        for (int i = 0; i < answerChoices.size(); i++) {
            if (answerChoices.get(i) != item) {
                answerChoices.get(i).setDefault();
                answerChoices.get(i).setActive(0);
            }
        }
    }

    private void loadData(Bundle bundle) {
        if (bundle != null) {
            questions = bundle.getParcelableArrayList("Questions");
        } else {
            if (questions == null) {
                questions = DataSource.getInstance().getQuestions();
            }
        }
        listItemQues = new ArrayList<>();
        answerChoices = new ArrayList<>();
        currentQuesIndex = 0;
    }

    private void setAllQuesNoItemInActive() {
        for (QuestionNoItemWrapper item : listItemQues) {
            item.setActive(false);
        }
    }

    private void scrollToCenter(QuestionNoItemWrapper questionNoItemWrapper) {
        int centerX = horizontalScrollView.getWidth() / 2;
        int[] itemPos = new int[]{0, 0};
        questionNoItemWrapper.getView().getLocationOnScreen(itemPos);
        int x = itemPos[0];
        int offset = x - centerX + questionNoItemWrapper.getView().getWidth() / 2;
        horizontalScrollView.smoothScrollTo(horizontalScrollView.getScrollX() + offset, 0);
    }

    private class QuestionPagerAdapter extends PagerAdapter {

        private ArrayList<Question> data;
        private Context context;

        public QuestionPagerAdapter(Context context, ArrayList<Question> data) {
            this.context = context;
            this.data = questions;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(this.context, R.layout.written_test_page_item, null);
            final Question ques = data.get(position);
            final ImageView questionImage = (ImageView) view.findViewById(R.id.questionImage);
            TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
            tvQuestion.setText(ques.question);
            if (ques.image != null) {
                questionImage.setVisibility(View.VISIBLE);
                questionImage.setImageBitmap(ques.image);
                questionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(KEY_DIALOG,data.get(position).image );
                        QuestionDialogFragment dialogFragment = new QuestionDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "Question");
                    }

                });
            } else {
                questionImage.setVisibility(View.GONE);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
