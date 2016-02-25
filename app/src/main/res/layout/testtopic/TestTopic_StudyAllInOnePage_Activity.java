package layout.testtopic;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import self.yue.usdriving.R;

public class TestTopic_StudyAllInOnePage_Activity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    ListView lv_StudyInOnePage;
    ArrayList<TestTopic_StudyInOnePage_ListItem> list_Question;
    TestTopic_StudyInOnePage_Adapter adapter;
    TextView tv_DialogContentQuestion;
    ImageView img_DialogImageQuestion;
    TextView tv_Note;
    TextView tv_AnswerA;
    TextView tv_AnswerB;
    TextView tv_AnswerC;
    TextView tv_AnswerD;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_topic__study_all_in_one_page_);

        lv_StudyInOnePage = (ListView) this.findViewById(R.id.lv_StudyInOnePage);
        setUpToolbar();
        initData();

        adapter = new TestTopic_StudyInOnePage_Adapter(TestTopic_StudyAllInOnePage_Activity.this,list_Question);
        lv_StudyInOnePage.setAdapter(adapter);

        lv_StudyInOnePage.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Dialog dialog = new Dialog(TestTopic_StudyAllInOnePage_Activity.this);
        dialog.setContentView(R.layout.test_topic_question_dialog);
        findViewDialogId();
//        tv_DialogContentQuestion.setText(list_Question.get(position).getQuestion());
//        img_DialogImageQuestion.setImageResource();
        dialog.show();

    }


    private void findViewDialogId(){
        tv_DialogContentQuestion = (TextView) findViewById(R.id.tv_DialogContentQuestion);
        img_DialogImageQuestion = (ImageView) findViewById(R.id.img_DialogImageQuestion);
        tv_Note = (TextView) findViewById(R.id.tv_Explain);
        tv_AnswerA = (TextView) findViewById(R.id.tv_AnswerA);
        tv_AnswerB = (TextView) findViewById(R.id.tv_AnswerB);
        tv_AnswerC = (TextView) findViewById(R.id.tv_AnswerC);
        tv_AnswerD = (TextView) findViewById(R.id.tv_AnswerD);
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
               onBackPressed();
            }
        });
    }
    private void  initData(){
        list_Question = new ArrayList<TestTopic_StudyInOnePage_ListItem>();
        list_Question.add(new TestTopic_StudyInOnePage_ListItem(1,1,"What kinds of drugs, other than alcohol, can affect your driving ability",
                "test_image",null,"answer A","answer B","answer C","answer D","A","this is my explain"));
        list_Question.add(new TestTopic_StudyInOnePage_ListItem(2,1,"What kinds of drugs, other than alcohol, can affect your driving ability",
                "test_image",null,"answer A","answer B","answer C","answer D","B","this is my explain"));
        list_Question.add(new TestTopic_StudyInOnePage_ListItem(3,1,"What kinds of drugs, other than alcohol, can affect your driving abilityaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "test_image",null,"answer A","answer B","answer C","answer D","C","this is my explain"));
    }

    private class TestTopic_StudyInOnePage_Adapter extends BaseAdapter {
        Context mContext;
        ArrayList<TestTopic_StudyInOnePage_ListItem> question_List;
        LayoutInflater inflater;

        public TestTopic_StudyInOnePage_Adapter(Context context, ArrayList<TestTopic_StudyInOnePage_ListItem> objects) {
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
                convertView = inflater.inflate(R.layout.test_topic_studyinonepage_list_item,null);
                mViewHolder = new MyViewHolder();
                convertView.setTag(mViewHolder);
            }
            else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }
            mViewHolder.tv_Id = (TextView) convertView.findViewById(R.id.tv_IdQuestion);
            mViewHolder.tv_Question = (TextView) convertView.findViewById(R.id.tv_Question);
            mViewHolder.img_ImageQuestion = (ImageView) convertView.findViewById(R.id.img_Question);


            if(question_List.get(position).getId()<10){
                mViewHolder.tv_Id.setText("0"+question_List.get(position).getId());
            }
            else {
                mViewHolder.tv_Id.setText(question_List.get(position).getId()+"");
            }

            mViewHolder.tv_Question.setText(question_List.get(position).getQuestion() + "");



            if (question_List.get(position).getImageData()!=null){
                mViewHolder.img_ImageQuestion.setImageBitmap(question_List.get(position).getImageData());
            }
            else {
                mViewHolder.img_ImageQuestion.setVisibility(View.GONE);

            }
            return convertView;
        }

        private class MyViewHolder{
            TextView tv_Id;
            TextView tv_Question;
            ImageView img_ImageQuestion;
        }
    }
}
