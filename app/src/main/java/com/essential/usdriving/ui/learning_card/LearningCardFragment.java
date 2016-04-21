package com.essential.usdriving.ui.learning_card;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.entity.TopicCard;
import com.essential.usdriving.ui.utility.LockItemUtil;

import java.util.ArrayList;


public class LearningCardFragment extends BaseFragment {

    ListView lvcard;
    ListCardAdapter cardAdapter;
    ArrayList<TopicCard> arrayListCard;
    public static final String KEY_CARD = "keycard", KEY_CARD_TOPIC = "";
    private int type;

    @Override
    protected String setTitle() {
        return getString(R.string.title_learning_card);
    }

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_learning_card;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        findViews(rootView);
        LoadDataGuide();
        cardAdapter = new ListCardAdapter(getActivity(), arrayListCard);
        lvcard.setAdapter(cardAdapter);
        lvcard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(KEY_CARD, String.valueOf(arrayListCard.get(position).getID()));

            }
        });

    }

    private void findViews(View rootview) {
        lvcard = (ListView) rootview.findViewById(R.id.lvcard);
    }

    private void LoadDataGuide() {
        arrayListCard = DataSource.getInstance().getTopicCard();
    }


    private class ListCardAdapter extends BaseAdapter {
        Context context;
        ArrayList<TopicCard> topicCards;
        LayoutInflater inflater;

        public ListCardAdapter(Context context, ArrayList<TopicCard> _topicCards) {
            this.context = context;
            this.topicCards = _topicCards;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return topicCards.size();
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
            MyViewHolder myViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_learning_card, null);
                myViewHolder = new MyViewHolder();
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.bookImage = (ImageView) convertView.findViewById(R.id.photo);
            myViewHolder.ID = (TextView) convertView.findViewById(R.id.topic_name);
            myViewHolder.number = (TextView) convertView.findViewById(R.id.number);
            myViewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.layout_item_topic_card);
            myViewHolder.ID.setText(topicCards.get(position).getTopic());
            myViewHolder.number.setText(String.valueOf(topicCards.get(position).getNumber()) + " cards");
            myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getTag().toString()) {
                        case LockItemUtil.UNLOCK:
                            Bundle bundle = new Bundle();
                            bundle.putString(KEY_CARD, String.valueOf(topicCards.get(position).getID()));
                            bundle.putInt(KEY_CARD_TOPIC, topicCards.get(position).getID());
                            TestCardFragment testCardFragment = new TestCardFragment();
                            testCardFragment.setArguments(bundle);
                            replaceFragment(testCardFragment, getString(R.string.title_learning_card));
                            break;
                        case LockItemUtil.LOCKED:
                            LockItemUtil.getInstance(getActivity()).showToast();
                            break;
                    }

                }
            });

            myViewHolder.imgLock = (ImageView) convertView.findViewById(R.id.image_lock);
            LockItemUtil.getInstance(getActivity()).lockOrUnlock(position, myViewHolder.imgLock, myViewHolder.relativeLayout);

            return convertView;
        }

        private class MyViewHolder {
            ImageView bookImage;
            TextView ID;
            TextView number;
            RelativeLayout relativeLayout;
            ImageView imgLock;
        }
    }

}
