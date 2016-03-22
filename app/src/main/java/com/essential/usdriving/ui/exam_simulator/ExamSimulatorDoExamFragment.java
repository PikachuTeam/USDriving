
package com.essential.usdriving.ui.exam_simulator;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.essential.usdriving.ui.widget.WarningDialog;
import com.essential.usdriving.ui.written_test.DMVWrittenTestFragment;

import java.util.ArrayList;

/**
 * Created by the_e_000 on 8/14/2015.
 */
public class ExamSimulatorDoExamFragment extends BaseFragment implements ViewPager.OnPageChangeListener, QuestionNoItemWrapper.OnItemQuestionClickListener, View.OnClickListener, WarningDialog.OnDialogItemClickListener {

    private HorizontalScrollView horizontalScrollView;
    private LinearLayout layoutScrollContent;
    private ViewPager viewPager;

    private TextView minute1;
    private TextView minute2;

    private TextView second1;
    private TextView second2;

    private ArrayList<Question> questions;
    private ArrayList<AnswerChoicesItem> answerChoices;
    private ArrayList<QuestionNoItemWrapper> listItemQues;
    private int currentQuesIndex;
    private QuestionPagerAdapter adapter;
    private CountDownTimer timer;
    private int min1, min2, sec1, sec2;
    private WarningDialog dialog, warningDialog;
    private final static int INTERVAL = 1000, LIMIT_TIME = 3600000;
    private MenuItem menuToolbarResult;


    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_exam_simulator_do_exam;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        warningDialog = new WarningDialog(getActivity(), 3);
        warningDialog.setOnDialogItemClickListener(new WarningDialog.OnDialogItemClickListener() {
            @Override
            public void onDialogItemClick(int code) {
                if (code == WarningDialog.OK) {
                    warningDialog.dismiss();
                    timer.cancel();
//                    ExamSimulatorStartFragment startFragment = new ExamSimulatorStartFragment();
//                    replaceFragment(startFragment, getString(R.string.title_exam_simulator));
                    // getFragmentManager().popBackStack("", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getFragmentManager().popBackStack();
                } else {
                    timer.start();
                    warningDialog.dismiss();
                }
            }
        });
        warningDialog.show();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.Result).setVisible(true);
        menuToolbarResult = menu.findItem(R.id.Result);
        menuToolbarResult.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                timer.cancel();
                dialog = new WarningDialog(getActivity(), 1);
                dialog.setOnDialogItemClickListener(new WarningDialog.OnDialogItemClickListener() {
                    @Override
                    public void onDialogItemClick(int code) {
                        if (code == WarningDialog.OK) {
                            dialog.dismiss();
                            ExamSimulatorTestResultFragment fragment = new ExamSimulatorTestResultFragment();
                            Bundle bundle = new Bundle();
                            String time = totalTime();
                            bundle.putParcelableArrayList("Questions", questions);
                            bundle.putString("Total Time", time);
                            timer.cancel();
                            fragment.setArguments(bundle);
                            replaceFragment(fragment, getString(R.string.title_exam_simulator));
                        } else {
                            timer.start();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
        super.onPrepareOptionsMenu(menu);


    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        min1 = 6;
        min2 = 0;
        sec1 = 0;
        sec2 = 0;
        startTimer();
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
        addQuestionList();
       // showChoices(questions.get(currentQuesIndex));

        adapter = new QuestionPagerAdapter(getActivity(), questions);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Questions", questions);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected String setTitle() {
        return getString(R.string.title_activity_exam_simulator);
    }


    @Override
    protected boolean enableToolbar() {
        return true;
    }

    @Override
    public void defineButtonResult() {

    }



    @Override
    public void onItemQuestionClick(QuestionNoItemWrapper item) {
        setAllQuesNoItemInActive();
        item.setActive(true);
        int index = listItemQues.indexOf(item);
        scrollToCenter(item);
        viewPager.setCurrentItem(index, true);
        //showChoices(questions.get(index));
        currentQuesIndex = index;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setAllQuesNoItemInActive();
        listItemQues.get(position).setActive(true);
        //showChoices(questions.get(position));
        scrollToCenter(listItemQues.get(position));
        currentQuesIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onDialogItemClick(int code) {
        if (code == WarningDialog.OK) {
            dialog.dismiss();
            ExamSimulatorTestResultFragment fragment = new ExamSimulatorTestResultFragment();
            Bundle bundle = new Bundle();
            String time = totalTime();
            bundle.putParcelableArrayList("Questions", questions);
            bundle.putString("Total Time", time);
            timer.cancel();
            fragment.setArguments(bundle);
            replaceFragment(fragment, getString(R.string.title_exam_simulator));
        } else {
            timer.start();
            dialog.dismiss();
        }
    }

    private void findViews(View rootView) {
        horizontalScrollView = (HorizontalScrollView) rootView.findViewById(R.id.horizontalScrollView);
        layoutScrollContent = (LinearLayout) rootView.findViewById(R.id.layoutScrollContent);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
     //   layoutAnswerChoiceContent = (LinearLayout) rootView.findViewById(R.id.layoutAnswerChoiceContent);
        minute1 = (TextView) rootView.findViewById(R.id.minute_1);
        minute2 = (TextView) rootView.findViewById(R.id.minute_2);

        second1 = (TextView) rootView.findViewById(R.id.second_1);
        second2 = (TextView) rootView.findViewById(R.id.second_2);


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

    private void loadData() {
        if (questions == null) {
            questions = DataSource.getInstance().getQuestions();
        } else {
            for (int i = 0; i < questions.size(); i++) {
                questions.get(i).myAnswer = BaseEntity.ANSWER_NOT_CHOOSE;
            }
        }
        listItemQues = new ArrayList<>();
        answerChoices = new ArrayList<>();
        currentQuesIndex = 0;
    }

    private void makeTime() {
        sec2--;
        if (sec2 == -1) {
            sec2 = 9;
            sec1--;
            if (sec1 == -1) {
                sec1 = 5;
                min2--;
                if (min2 == -1) {
                    min2 = 9;
                    min1--;
                }
            }
        }
        minute1.setText("" + min1);
        minute2.setText("" + min2);
        second1.setText("" + sec1);
        second2.setText("" + sec2);
        // timeLeft++;
    }

    private void startTimer() {

        timer = new CountDownTimer(LIMIT_TIME, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {

                makeTime();
            }

            @Override
            public void onFinish() {
                ExamSimulatorTestResultFragment fragment = new ExamSimulatorTestResultFragment();
                Bundle bundle = new Bundle();
                String time = totalTime();
                bundle.putParcelableArrayList("Questions", questions);
                bundle.putString("Total Time", time);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getString(R.string.title_exam_simulator));
            }
        };
        timer.start();
    }


    private String totalTime() {
        String time = "";

        int tmp1, tmp2;
        tmp1 = 10 - sec2;
        if (sec2 > 0) {
            tmp2 = 1;
        } else {
            tmp2 = 0;
        }
        if (tmp1 == 10) {
            tmp1 = 0;
        }
        time += "" + tmp1;

        tmp1 = 6 - sec1 - tmp2;
        if (sec1 > 0) {
            tmp2 = 1;
        } else {
            tmp2 = 0;
        }
        if (tmp1 == 6) {
            tmp1 = 0;
        }
        time += "" + tmp1;

        time += ":";

        tmp1 = 10 - min2 - tmp2;
        if (min2 > 0) {
            tmp2 = 1;
        } else {
            tmp2 = 0;
        }
        if (tmp1 == 10) {
            tmp1 = 0;
        }
        time += "" + tmp1;

        tmp1 = 6 - min1 - tmp2;
        time += "" + tmp1;

        time = revertString(time);

        return time;
    }

    private String revertString(String str) {
        String tmp = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            tmp += str.charAt(i);
        }
        return tmp;
    }

    private class QuestionPagerAdapter extends PagerAdapter implements  AnswerChoicesItem.OnAnswerChooseListener{

        private ArrayList<Question> data;
        private Context context;
        private LinearLayout layoutAnswerChoiceContent;
        public QuestionPagerAdapter(Context context, ArrayList<Question> data) {
            this.context = context;
            this.data = data;
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
            layoutAnswerChoiceContent= (LinearLayout) view.findViewById(R.id.layoutAnswerChoiceContent);
            tvQuestion.setText(ques.question);
            if (ques.image != null) {
                questionImage.setVisibility(View.VISIBLE);
                imgZoom.setVisibility(View.VISIBLE);
                questionImage.setImageBitmap(ques.image);
                questionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(DMVWrittenTestFragment.KEY_DIALOG, data.get(position).image);
                        QuestionDialogFragment dialogFragment = new QuestionDialogFragment();
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "Question");
                    }

                });
            } else {
                questionImage.setVisibility(View.GONE);
                imgZoom.setVisibility(View.GONE);
            }
            imgZoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DMVWrittenTestFragment.KEY_DIALOG, data.get(position).image);
                    QuestionDialogFragment dialogFragment = new QuestionDialogFragment();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "Question");
                }
            });

            container.addView(view);
            return view;
        }
        @Override
        public void onAnswerChoose(AnswerChoicesItem item) {
            item.setActive(true);

            questions.get(currentQuesIndex).myAnswer = item.getPosition();
            listItemQues.get(currentQuesIndex).setHighlight();
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
