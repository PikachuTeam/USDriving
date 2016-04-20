package com.essential.usdriving.ui.test_topic;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.ui.utility.LockItemUtil;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;


public class TestTopicFragment extends BaseFragment implements OnTopicListItemClickListener {
    ListView lv_Topic;

    ArrayList<TestTopicListItem> list_Topic;
    TestTopicListAdapter adapter;
    public static final String KEY_TEST_TOPIC_1 = "Name", KEY_TEST_TOPIC_2 = "Id";
    private boolean close = true;

    @Override
    protected String setTitle() {
        return getString(R.string.title_test_topic);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_Topic = new ArrayList<TestTopicListItem>();
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
        return R.layout.fragment_test_topic;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {

        lv_Topic = (ListView) rootView.findViewById(R.id.lv_Topic);
        loadDataGuide();
        adapter = new TestTopicListAdapter(getActivity(), list_Topic);
        adapter.setOnTopicListItemClickListener(this);
        lv_Topic.setAdapter(adapter);
    }

    private void loadDataGuide() {
        list_Topic = DataSource.getInstance().getTopic();
    }

    @Override
    public void onTopicListItemClick(int position) {
        if (list_Topic.get(position).isClose()) {
            list_Topic.get(position).setClose(false);
            adapter.notifyDataSetChanged();

        } else {
            list_Topic.get(position).setClose(true);
            adapter.notifyDataSetChanged();
        }
    }


    private class TestTopicListAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<TestTopicListItem> topic_List;
        LayoutInflater inflater;
        private OnTopicListItemClickListener mListener;

        public TestTopicListAdapter(Context context, ArrayList<TestTopicListItem> objects) {
            this.mContext = context;
            this.topic_List = objects;
            inflater = LayoutInflater.from(this.mContext);
        }

        @Override
        public int getCount() {
            return topic_List.size();
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
            MyViewHolder mViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.test_topic_list_item, null);
                mViewHolder = new MyViewHolder();
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }
            mViewHolder.tvTopic = (TextView) convertView.findViewById(R.id.tv_TopicName);
            mViewHolder.tvId = (TextView) convertView.findViewById(R.id.tv_Id);
            mViewHolder.tvNumberOfQuestion = (TextView) convertView.findViewById(R.id.tv_NumberOfQuestions);
            mViewHolder.tvPracticeOne = (TextView) convertView.findViewById(R.id.tvPracticeOne);
            mViewHolder.tvStudyAll = (TextView) convertView.findViewById(R.id.tvStudyAll);
            mViewHolder.tvTopic.setText(topic_List.get(position).getTopicName());
            mViewHolder.tvNumberOfQuestion.setText(topic_List.get(position).getNumberOfQuestion() + "");
            if (topic_List.get(position).getId() < 10) {
                mViewHolder.tvId.setText("0" + topic_List.get(position).getId());
            } else {
                mViewHolder.tvId.setText(topic_List.get(position).getId() + "");
            }

            mViewHolder.layoutButton = (LinearLayout) convertView.findViewById(R.id.layout_Button);
            mViewHolder.layoutButton.setVisibility(View.GONE);
            if (topic_List.get(position).isClose()) {
                mViewHolder.layoutButton.setVisibility(View.GONE);

            } else {
                mViewHolder.layoutButton.setVisibility(View.VISIBLE);
                for (int i = 0; i < list_Topic.size(); i++) {
                    if (i != position) {
                        list_Topic.get(position).setClose(true);
                    }
                }
            }

            mViewHolder.tvStudyAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_TEST_TOPIC_1, String.valueOf(list_Topic.get(position).getId()));
                    TestTopic_StudyAllInOnePage_Fragment fragment = new TestTopic_StudyAllInOnePage_Fragment();
                    fragment.setArguments(bundle);
                    replaceFragment(fragment, getString(R.string.title_test_topic));
                }
            });
            mViewHolder.tvPracticeOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_TEST_TOPIC_1, String.valueOf(list_Topic.get(position).getId()));
                    bundle.putInt(KEY_TEST_TOPIC_2, list_Topic.get(position).getId());
                    TestTopic_StudyOneByOne_Fragment fragment = new TestTopic_StudyOneByOne_Fragment();
                    fragment.setArguments(bundle);
                    replaceFragment(fragment, getString(R.string.title_test_topic));
                }
            });

            mViewHolder.layoutTestTopic = (RelativeLayout) convertView.findViewById(R.id.layout_test_topic);
            mViewHolder.imgLock = (ImageView) convertView.findViewById(R.id.image_lock);
            LockItemUtil.getInstance(getActivity()).lockOrUnlock(position, mViewHolder.imgLock, mViewHolder.layoutTestTopic);

            mViewHolder.layoutTestTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getTag().toString()) {
                        case LockItemUtil.UNLOCK:
                            if (mListener != null) {
                                mListener.onTopicListItemClick(position);
                            }
                            break;
                        case LockItemUtil.LOCKED:
                            LockItemUtil.getInstance(getActivity()).showToast();
                            break;
                    }
                }
            });

            return convertView;
        }

        public void setOnTopicListItemClickListener(OnTopicListItemClickListener onTopicListItemClickListener) {
            mListener = onTopicListItemClickListener;
        }

        private class MyViewHolder {
            TextView tvTopic;
            TextView tvId;
            TextView tvNumberOfQuestion;
            TextView tvPracticeOne;
            TextView tvStudyAll;
            LinearLayout layoutButton;
            RelativeLayout layoutTestTopic;
            ImageView imgLock;
        }
    }
}
