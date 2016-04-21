package layout.testtopic;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import self.yue.usdriving.R;


public class TestTopicActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    ListView lv_Topic;

    ArrayList<TestTopicListItem> list_Topic;
    TestTopicListAdapter adapter;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_topic);

        lv_Topic = (ListView) this.findViewById(R.id.lv_Topic);


        list_Topic = new ArrayList<TestTopicListItem>();
        list_Topic.add(new TestTopicListItem(1, "Alcohol and Other Drugs", 19));
        list_Topic.add(new TestTopicListItem(2,"Alcohol and Other Drugs",29));
        list_Topic.add(new TestTopicListItem(3,"Alcohol and Other Drugs",39));
        list_Topic.add(new TestTopicListItem(10,"Alcohol and Other Drugs",49));


        adapter = new TestTopicListAdapter(TestTopicActivity.this,list_Topic);
        lv_Topic.setAdapter(adapter);

        lv_Topic.setOnItemClickListener(this);

        setUpToolbar();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (list_Topic.get(position).isClose()){
            list_Topic.get(position).setClose(false);
            adapter.notifyDataSetChanged();
        }
        else {
            list_Topic.get(position).setClose(true);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.test_topic_tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private class TestTopicListAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<TestTopicListItem> topic_List;
        LayoutInflater inflater;


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
        public View getView(int position, View convertView, ViewGroup parent) {
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
            if (topic_List.get(position).isClose()){
                mViewHolder.layoutButton.setVisibility(View.GONE);
            }
            else {
                mViewHolder.layoutButton.setVisibility(View.VISIBLE);
            }

            mViewHolder.tvStudyAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,TestTopic_StudyAllInOnePage_Activity.class);
                    mContext.startActivity(intent);
                }
            });
            return convertView;
        }


        private class MyViewHolder {
            TextView tvTopic;
            TextView tvId;
            TextView tvNumberOfQuestion;
            TextView tvPracticeOne;
            TextView tvStudyAll;
            LinearLayout layoutButton;
        }
    }


}
