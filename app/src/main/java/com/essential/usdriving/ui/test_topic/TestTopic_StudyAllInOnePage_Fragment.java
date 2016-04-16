package com.essential.usdriving.ui.test_topic;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;
import com.essential.usdriving.database.DataSource;
import com.essential.usdriving.entity.Question;

import java.util.ArrayList;

public class TestTopic_StudyAllInOnePage_Fragment extends BaseFragment {
    ListView lv_StudyInOnePage;
    ArrayList<Question> list_Question;
    TestTopic_StudyInOnePage_Adapter adapter;
     String getTopic;
    public static final String KEY_DIALOG="keydialog";

    @Override
    protected boolean enableIndicator() {
        return true;
    }

    @Override
    protected boolean enableBackButton() {
        return  true;
    }

    @Override
    public void defineButtonResult() {

    }

    @Override
    protected String setTitle() {
        return "Test Topic";
    }
    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test_topic_study_all_in_one_page_;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        lv_StudyInOnePage = (ListView) rootView.findViewById(R.id.lv_StudyInOnePage);
        LoadDataGuide();
        adapter = new TestTopic_StudyInOnePage_Adapter(getActivity(),list_Question);
        lv_StudyInOnePage.setAdapter(adapter);
        lv_StudyInOnePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_DIALOG, list_Question.get(position));
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getBaseActivity().getSupportFragmentManager(), "Question");
            }
        });
    }

    private void LoadDataGuide() {
        list_Question = new ArrayList<>();
        Bundle bundle = this.getArguments();
        getTopic = bundle.getString(TestTopicFragment.KEY_TESTTOPIC_1);
        list_Question = DataSource.getInstance().getTopicItem(getTopic);
    }

    private class TestTopic_StudyInOnePage_Adapter extends BaseAdapter {
        Context mContext;
        ArrayList<Question> question_List;
        LayoutInflater inflater;

        public TestTopic_StudyInOnePage_Adapter(Context context, ArrayList<Question> objects) {
            this.mContext = context;
            this.question_List = objects;
            inflater = LayoutInflater.from(this.mContext);
        }

        @Override
        public int getCount() {
            return question_List.size();
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
            if(convertView == null){
                convertView = inflater.inflate(R.layout.test_topic_study_in_one_page_list_item,null);
                mViewHolder = new MyViewHolder();
                convertView.setTag(mViewHolder);
            }
            else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }
            mViewHolder.tv_Id = (TextView) convertView.findViewById(R.id.tv_IdQuestion);
            mViewHolder.tv_Question = (TextView) convertView.findViewById(R.id.tv_Question);
            mViewHolder.tv_Id.setText(""+(position+1));
            mViewHolder.tv_Question.setText(""+question_List.get(position).question);
            return convertView;
        }

        private class MyViewHolder{
            TextView tv_Id;
            TextView tv_Question;

        }
    }
}
