package com.essential.usdriving.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.entity.HomeEntity;
import com.essential.usdriving.ui.exam_simulator.ExamSimulatorDoExamFragment;
import com.essential.usdriving.ui.exam_simulator.ExamSimulatorStartFragment;
import com.essential.usdriving.ui.learning_card.LearningCardFragment;
import com.essential.usdriving.ui.test_topic.TestTopicFragment;
import com.essential.usdriving.ui.videotip.VideoTipsFragment;
import com.essential.usdriving.ui.written_test.DMVWrittenTestFragment;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment {

    private ListView chooseList;
    private HomeAdapter homeAdapter;
    private ArrayList<HomeEntity> listHome;

    @Override
    protected boolean enableBackButton() {
        return false;
    }


    @Override
    public void defineButtonResult() {

    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        listHome=new ArrayList<>();
        chooseList = (ListView) rootView.findViewById(R.id.Chooselist);
        getItemHome(listHome);
        homeAdapter=new HomeAdapter(getActivity(),listHome);
        chooseList.setAdapter(homeAdapter);

    }

    public void getItemHome(ArrayList<HomeEntity> list) {
        list.add(new HomeEntity(getString(R.string.title_dmv_written_test),ContextCompat.getDrawable(getActivity(), R.drawable.ic_dmv_written_test)));
        list.add(new HomeEntity(getString(R.string.title_exam_simulator),ContextCompat.getDrawable(getActivity(),R.drawable.ic_exam_simulator) ));
        list.add(new HomeEntity(getString(R.string.title_test_topic),ContextCompat.getDrawable(getActivity(),R.drawable.ic_dmv_test_topics )));
        list.add(new HomeEntity(getString(R.string.title_learning_card),ContextCompat.getDrawable(getActivity(),R.drawable.ic_learning_cards) ));
        list.add(new HomeEntity(getString(R.string.title_video_tips), ContextCompat.getDrawable(getActivity(),R.drawable.ic_driving_tips)));
    }

    private class HomeAdapter extends BaseAdapter {
        Context context;
        ArrayList<HomeEntity> list;
        LayoutInflater inflater;

        public HomeAdapter(Context context, ArrayList<HomeEntity> listHome_Cards) {
            this.context = context;
            this.list = listHome_Cards;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return listHome.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyViewHolder myViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_home_list, null);
                myViewHolder = new MyViewHolder();
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.cardView = (CardView) convertView.findViewById(R.id.cardView);
            myViewHolder.textView = (TextView) convertView.findViewById(R.id.tvChooseList);
            myViewHolder.image = (ImageView) convertView.findViewById(R.id.imageChooseList);

            myViewHolder.textView.setText(list.get(position).getTextHome());
            myViewHolder.image.setImageDrawable(list.get(position).geticonHome());

            myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (position){
                        case 0:replaceFragment(new DMVWrittenTestFragment(),getString(R.string.written_test_result_title));break;
                        case 1:replaceFragment(new ExamSimulatorStartFragment(),getString(R.string.written_test_result_title));break;
                        case 2:replaceFragment(new TestTopicFragment(),getString(R.string.title_learning_card));break;
                        case 3:replaceFragment(new LearningCardFragment(),getString(R.string.title_learning_card));break;
                        case 4: replaceFragment(new VideoTipsFragment(),getString(R.string.title_video_tips));break;

                    }

                }
            });
            myViewHolder.cardView.setTag(position);
            return convertView;
        }

        private class MyViewHolder {
            CardView cardView;
            TextView textView;
            ImageView image;
        }
    }
}
