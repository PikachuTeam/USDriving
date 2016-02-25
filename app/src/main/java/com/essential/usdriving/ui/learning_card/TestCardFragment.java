package com.essential.usdriving.ui.learning_card;

import android.content.Context;
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
import com.essential.usdriving.entity.Card;

import java.util.ArrayList;

public class TestCardFragment  extends BaseFragment implements ViewPager.OnPageChangeListener {

    ArrayList<Card> listCard;
    private ViewPager viewPager;
    private int currentQuesIndex;
    private TextView tvAnswer,tvNumber;
    private CardAdapter adapter;
    private String getTopic;
    private View viewWhite,viewOrange;
    private CardView cardPrevious;
    private CardView cardNext;
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
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test_card;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
    findViews(rootView);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        adapter = new CardAdapter(getActivity(), listCard);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
    }
    private void findViews(View rootView) {
       //  btnPrevious= (Button) rootView.findViewById(R.id.btnPrevious);
      // btnNext= (Button) rootView.findViewById(R.id.btnNext);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPagerQuestion);
         tvAnswer= (TextView) rootView.findViewById(R.id.definition);
        tvNumber= (TextView) rootView.findViewById(R.id.tv_Number);
        viewWhite= rootView.findViewById(R.id.viewWhite);
        viewOrange= rootView.findViewById(R.id.viewOrange);
        cardPrevious= (CardView) rootView.findViewById(R.id.btnPreviousLayout);
        cardNext =(CardView) rootView.findViewById(R.id.btnNextLayout);
        cardPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentQuesIndex>0){
                    computeSize(currentQuesIndex-1,listCard.size());
                    viewPager.setCurrentItem(currentQuesIndex-1);
                }
            }
        });
        cardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentQuesIndex<listCard.size()){
                    computeSize(currentQuesIndex+1,listCard.size());
                    viewPager.setCurrentItem(currentQuesIndex + 1);
                }
            }
        });
    }
    private void loadData() {
        listCard = new ArrayList<>();
        currentQuesIndex = 0;
        Bundle bundle = this.getArguments();
        getTopic = bundle.getString(LearningCardFragment.KEY_CARD);
        listCard = DataSource.getInstance().getCard(getTopic);

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

            currentQuesIndex = position;
            computeSize((position + 1), listCard.size());
            tvNumber.setText("  " + (currentQuesIndex+1) + "  of  " + listCard.size());
            tvAnswer.setText( listCard.get(position).getCardDefinition());


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void computeSize(int position,int size){
        viewOrange.setLayoutParams(new RelativeLayout.LayoutParams(viewWhite.getMeasuredWidth() * position / size,
                viewWhite.getMeasuredHeight()));
    }
/*
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnPreviousLayout:

                // computeSize(1,listCard.size());
                //viewPager.setCurrentItem(0);
                break;
            case R.id.btnNextLayout:

               //computeSize(listCard.size(),listCard.size());
               // viewPager.setCurrentItem(listCard.size());
                break;
        }

    }
*/

    private class CardAdapter extends PagerAdapter {

        private ArrayList<Card> data;
        private Context context;

        public CardAdapter(Context context, ArrayList<Card> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(this.context, R.layout.item_card, null);

            Card card = data.get(position);
            ImageView questionImage = (ImageView) view.findViewById(R.id.questionImage);
            TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
            if(position==0){
                tvAnswer.setText(listCard.get(0).getCardDefinition());
                tvNumber.setText("  " + (position+1) + "  of  " + listCard.size());
            }
                tvQuestion.setText(card.getCardTerm());

            if (card.image != null) {
                questionImage.setVisibility(View.VISIBLE);
                questionImage.setImageBitmap(card.image);
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
