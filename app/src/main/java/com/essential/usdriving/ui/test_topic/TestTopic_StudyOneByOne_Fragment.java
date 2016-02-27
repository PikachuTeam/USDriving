package com.essential.usdriving.ui.test_topic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.entity.CustomViewPager;
import com.essential.usdriving.entity.Question;

import java.util.ArrayList;


public class TestTopic_StudyOneByOne_Fragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {


    ArrayList<Question> list;
    private CustomViewPager viewPager;
    private int currentQuesIndex;
    private TextView tvChoiceA,tvChoiceB,tvChoiceC,tvChoiceD,tvExplanation,tvNumber;
    private CardAdapter adapter;
    private String getTopic;
    private View viewWhite,viewOrange;
    private  ColorStateList oldColors;
    private CardView cardPrevious;
    private CardView cardNext;
    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test_topic__study_one_by_one_;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        oldColors =  tvNumber.getTextColors();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        adapter = new CardAdapter(getActivity(), list);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    protected String setTitle() {
        return "Test Topic";
    }
    private void findViews(View rootView) {
        cardPrevious= (CardView) rootView.findViewById(R.id.btnPreviousLayout);
        cardNext =(CardView) rootView.findViewById(R.id.btnNextLayout);
        viewPager = (CustomViewPager) rootView.findViewById(R.id.viewPagerQuestion);
        tvChoiceA= (TextView) rootView.findViewById(R.id.tvChoiceA);
        tvChoiceB= (TextView) rootView.findViewById(R.id.tvChoiceB);
        tvChoiceC= (TextView) rootView.findViewById(R.id.tvChoiceC);
        tvChoiceD= (TextView) rootView.findViewById(R.id.tvChoiceD);
        tvExplanation= (TextView) rootView.findViewById(R.id.tvExplanation);
        tvNumber= (TextView) rootView.findViewById(R.id.tv_Number);
        viewWhite= rootView.findViewById(R.id.viewWhite);
        viewOrange= rootView.findViewById(R.id.viewOrange);
        cardPrevious.setOnClickListener(this);
        cardNext.setOnClickListener(this);
        viewPager.setPagingEnabled(false);
    }
    private void loadData() {
        list = new ArrayList<>();
        currentQuesIndex = 0;
        Bundle bundle = this.getArguments();
        getTopic = bundle.getString(TestTopicFragment.KEY_TESTTOPIC);
        list = DataSource.getInstance().getTopicItem(getTopic);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        currentQuesIndex = position;
        computeSize((position + 1), list.size());
        tvNumber.setText("  " + (currentQuesIndex + 1) + "  of  " + list.size());
        SetData(position);
/*
        tvChoiceD.setText("" + listCard.get(position).choiceD);
        tvExplanation.setText("" + listCard.get(position).explanation);
        */
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void computeSize(int position,int size){
        viewOrange.setLayoutParams(new RelativeLayout.LayoutParams(viewWhite.getMeasuredWidth() * position / size,
                viewWhite.getMeasuredHeight()));
    }
    public void SetData(int position ){

        if(list.get(position).choiceD==null){
            tvChoiceD.setVisibility(View.GONE);
        }else {
            tvChoiceD.setText("D."+list.get(position).choiceD);
            tvChoiceD.setVisibility(View.VISIBLE);
        }
        tvChoiceA.setText("A." + list.get(position).choiceA);
        tvChoiceB.setText("B." + list.get(position).choiceB);
        tvChoiceC.setText("C." + list.get(position).choiceC);

        if(list.get(position).explanation!=null){
            tvExplanation.setText("Explanation:" + list.get(position).explanation);
        }else{
            tvExplanation.setText("");
        }

        switch (list.get(position).correctAnswer){
            case 0:
                tvChoiceA.setTextColor(getResources().getColor(R.color.right_answer_color));
                tvChoiceB.setTextColor(oldColors);
                tvChoiceC.setTextColor(oldColors);
                tvChoiceD.setTextColor(oldColors);
                break;
            case 1:
                tvChoiceB.setTextColor(getResources().getColor(R.color.right_answer_color));
                tvChoiceA.setTextColor(oldColors);
                tvChoiceC.setTextColor(oldColors);
                tvChoiceD.setTextColor(oldColors);

                break;
            case 2:
                tvChoiceC.setTextColor(getResources().getColor(R.color.right_answer_color));
                tvChoiceA.setTextColor(oldColors);
                tvChoiceB.setTextColor(oldColors);
                tvChoiceD.setTextColor(oldColors);
                break;
            case 3:
                tvChoiceD.setTextColor(getResources().getColor(R.color.right_answer_color));
                tvChoiceA.setTextColor(oldColors);
                tvChoiceC.setTextColor(oldColors);
                tvChoiceB.setTextColor(oldColors);
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnPreviousLayout:
                if(currentQuesIndex>0){
                    computeSize(currentQuesIndex-1,list.size());
                    viewPager.setCurrentItem(currentQuesIndex-1);
                }
                break;
            case R.id.btnNextLayout:
                if(currentQuesIndex<list.size()){
                    computeSize(currentQuesIndex+1,list.size());
                    viewPager.setCurrentItem(currentQuesIndex + 1);
                }
                break;
        }

    }



    private class CardAdapter extends PagerAdapter {

        private ArrayList<Question> data;
        private Context context;

        public CardAdapter(Context context, ArrayList<Question> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(this.context, R.layout.card_item, null);

            Question question = data.get(position);
            ImageView questionImage = (ImageView) view.findViewById(R.id.questionImage);
            TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
            //tvAnswer.setText(""+position+ "  "+card.getCardDefinition());
            if(position==0){
                /*
                tvChoiceA.setText("" + list.get(0).choiceA);
                tvChoiceB.setText("" + list.get(0).choiceB);
                tvChoiceC.setText("" + list.get(0).choiceC);
                tvChoiceD.setText("" + list.get(0).choiceD);
                tvExplanation.setText("" + list.get(0).explanation);

                */
                SetData(0);
                tvNumber.setText("  " + (position + 1) + "  of  " + list.size());
            }
            tvQuestion.setText(question.question);

            if (question.image != null) {
                questionImage.setVisibility(View.VISIBLE);
                questionImage.setImageBitmap(question.image);
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
