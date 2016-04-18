package com.essential.usdriving.ui.written_test;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.DebugUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import com.essential.usdriving.ui.exam_simulator.ExamSimulatorTestResultFragment;
import com.essential.usdriving.ui.home.HomeFragment;
import com.essential.usdriving.ui.widget.AnswerChoicesItem;
import com.essential.usdriving.ui.widget.QuestionDialogFragment;
import com.essential.usdriving.ui.widget.QuestionNoItemWrapper;
import com.essential.usdriving.ui.widget.WarningDialog;

import java.util.ArrayList;

public class DMVWrittenTestFragment extends BaseFragment implements ViewPager.OnPageChangeListener, QuestionNoItemWrapper.OnItemQuestionClickListener, View.OnClickListener, OnQuestionPagerClickListener {

    private LinearLayout layoutScrollContent;
    private ArrayList<Question> questions;
    private ArrayList<QuestionNoItemWrapper> listItemQues;
    private ViewPager viewPager;
    private HorizontalScrollView horizontalScrollView;
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
        menuToolbar = menu.findItem(R.id.Result);
        menuToolbar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                warningDialog = new WarningDialog(getActivity(), 1);
                warningDialog.setOnDialogItemClickListener(new WarningDialog.OnDialogItemClickListener() {
                    @Override
                    public void onDialogItemClick(int code) {
                        if (code == WarningDialog.OK) {
                            warningDialog.dismiss();
                            DMVWrittenTestResultFragment fragment = new DMVWrittenTestResultFragment();
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("Questions", questions);
                            fragment.setArguments(bundle);
                            replaceFragment(fragment, getString(R.string.written_test_result_title));
                        } else {
                            warningDialog.dismiss();
                        }
                    }
                });
                warningDialog.show();


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
        adapter = new QuestionPagerAdapter(getActivity(), questions);
        adapter.setOnQuestionPagerClickListener(this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

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

        currentQuesIndex = index;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setAllQuesNoItemInActive();
        listItemQues.get(position).setActive(true);
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
        return getString(R.string.title_dmv_written_test);
    }


    private void findViews(View view) {
        layoutScrollContent = (LinearLayout) view.findViewById(R.id.layoutScrollContent);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);

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


    private void loadData(Bundle bundle) {
        if (bundle != null) {
            questions = bundle.getParcelableArrayList("Questions");
        } else {
            if (questions == null) {
                questions = DataSource.getInstance().getQuestions();
            }
        }
        listItemQues = new ArrayList<>();
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

    @Override
    public void OnPagerItemClick(AnswerChoicesItem item) {
        if (questions.get(currentQuesIndex).myAnswer == DataSource.ANSWER_NOT_CHOSEN) {
            questions.get(currentQuesIndex).myAnswer = item.getIndex();
            QuestionNoItemWrapper wrapper = listItemQues.get(currentQuesIndex);
            if (!wrapper.isHighlight) {
                wrapper.setHighlight();
                horizontalScrollView.invalidate();
            }
            if (currentQuesIndex < 29) {
                currentQuesIndex++;
                viewPager.setCurrentItem(currentQuesIndex, true);
                scrollToCenter(wrapper);
            }

        } else {
            questions.get(currentQuesIndex).myAnswer = item.getIndex();
            QuestionNoItemWrapper wrapper = listItemQues.get(currentQuesIndex);
            if (!wrapper.isHighlight) {
                wrapper.setHighlight();
                horizontalScrollView.invalidate();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private class QuestionPagerAdapter extends PagerAdapter implements AnswerChoicesItem.OnAnswerChooseListener {

        private ArrayList<Question> data;
        private Context context;
        private OnQuestionPagerClickListener listener;

        public void setOnQuestionPagerClickListener(OnQuestionPagerClickListener listener) {
            this.listener = listener;
        }

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
             Question ques = data.get(position);
             ImageView questionImage = (ImageView) view.findViewById(R.id.questionImage);
            TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
            ImageView imgZoom = (ImageView) view.findViewById(R.id.buttonZoomIn);
            LinearLayout layoutChoice = (LinearLayout) view.findViewById(R.id.layoutAnswerChoiceContent);
            tvQuestion.setText(ques.question);
            if (ques.image != null) {
                questionImage.setVisibility(View.VISIBLE);
                imgZoom.setVisibility(View.VISIBLE);
                questionImage.setImageBitmap(ques.image);
                questionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(KEY_DIALOG, data.get(position).image);
                        QuestionDialogFragment dialogFragment = new QuestionDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "Question");
                    }

                });
                imgZoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(KEY_DIALOG, data.get(position).image);
                        QuestionDialogFragment dialogFragment = new QuestionDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "Question");
                    }

                });
            } else {
                questionImage.setVisibility(View.GONE);
                imgZoom.setVisibility(View.GONE);
            }
            ArrayList<AnswerChoicesItem> arrayList= makeChoices(ques);
            for(int i=0;i<arrayList.size();i++){
                layoutChoice.addView(arrayList.get(i).getView());
                arrayList.get(i).setOnAnswerChooseListener(this);
            }

            layoutChoice.invalidate();
            container.addView(view);
            return view;
        }

        public ArrayList<AnswerChoicesItem> makeChoices(Question question) {
            ArrayList<AnswerChoicesItem> arrayList = new ArrayList<>();
            if (question.choiceA != null) {
                AnswerChoicesItem answerChoicesItemA = new AnswerChoicesItem(context, DataSource.ANSWER_A);
                answerChoicesItemA.setAnswer(question.choiceA);
                arrayList.add(answerChoicesItemA);
            }
            if (question.choiceB != null) {
                AnswerChoicesItem answerChoicesItemB = new AnswerChoicesItem(context, DataSource.ANSWER_B);
                answerChoicesItemB.setAnswer(question.choiceB);
                arrayList.add(answerChoicesItemB);
            }
            if (question.choiceC != null) {
                AnswerChoicesItem answerChoicesItemC = new AnswerChoicesItem(context, DataSource.ANSWER_C);
                answerChoicesItemC.setAnswer(question.choiceC);
                arrayList.add(answerChoicesItemC);
            }
            if (question.choiceD != null) {
                AnswerChoicesItem answerChoicesItemD = new AnswerChoicesItem(context, DataSource.ANSWER_D);
                answerChoicesItemD.setAnswer(question.choiceD);
                arrayList.add(answerChoicesItemD);
            }
            resetAllChoices(arrayList);
            if(question.myAnswer!=DataSource.ANSWER_NOT_CHOSEN){
                arrayList.get(question.myAnswer).setActive(true);
            }

            return arrayList;
        }

        public void resetAllChoices(ArrayList<AnswerChoicesItem> list) {
            for (int i = 0; i < list.size(); i++) {
                    list.get(i).setActive(false);
            }
        }

        @Override
        public void onAnswerChoose(AnswerChoicesItem item) {
                listener.OnPagerItemClick(item);

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
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


    private WarningDialog warningDialog;
    @Override
    public void onBackPressed() {
        warningDialog = new WarningDialog(getActivity(), 3);
        warningDialog.setOnDialogItemClickListener(new WarningDialog.OnDialogItemClickListener() {
            @Override
            public void onDialogItemClick(int code) {
                if (code == WarningDialog.OK) {
                    warningDialog.dismiss();
                    getFragmentManager().popBackStack(HomeFragment.HOME_TRANSACTION, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    warningDialog.dismiss();
                }
            }
        });
        warningDialog.show();
    }
}
