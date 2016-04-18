package com.essential.usdriving.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;


import com.essential.usdriving.entity.Card;
import com.essential.usdriving.entity.Question;
import com.essential.usdriving.entity.TopicCard;
import com.essential.usdriving.ui.test_topic.TestTopicListItem;

import java.util.ArrayList;

import tatteam.com.app_common.sqlite.BaseDataSource;


public class DataSource extends BaseDataSource {

    public final static String DB_STATE_NAME = "StateName";
    public final static int ANSWER_A = 0, ANSWER_B = 1, ANSWER_C = 2, ANSWER_D = 3, ANSWER_NOT_CHOSEN = -1;

    private static DataSource instance;
    private Context context;

    private DataSource() {
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public ArrayList<TopicCard> getTopicCard() {
        ArrayList<TopicCard> Topic = new ArrayList<>();
        SQLiteDatabase sqLite = openConnection();
        Cursor cursor = sqLite.rawQuery("select USA_LearningTopics.Name,count(USA_LearningCards.TopicID),USA_LearningCards.TopicID" +
                " from USA_LearningTopics , USA_LearningCards " +
                "where USA_LearningTopics.ID=USA_LearningCards.TopicID" +
                " group by USA_LearningCards.TopicID", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TopicCard tc = new TopicCard();
            tc.setTopic(cursor.getString(0));
            tc.setNumber(cursor.getInt(1));
            tc.setID(cursor.getInt(2));
            cursor.moveToNext();
            Topic.add(tc);
        }
        cursor.close();
        closeConnection();
        return Topic;
    }

    public ArrayList<Card> getCard(String term) {
        ArrayList<Card> Topic = new ArrayList<>();
        SQLiteDatabase sqLite = openConnection();
        Cursor cursor = sqLite.rawQuery("select Term, Definition,ImageData from USA_LearningCards where TopicID=?", new String[]{term});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Card tc = new Card();
            tc.setCardTerm(cursor.getString(0));
            tc.setCardDefinition(cursor.getString(1));
            byte[] imageData = cursor.getBlob(2);
            if (imageData != null) {
                tc.image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            }
            cursor.moveToNext();
            Topic.add(tc);
        }
        cursor.close();
        closeConnection();
        return Topic;
    }

    public ArrayList<TestTopicListItem> getTopic() {
        ArrayList<TestTopicListItem> Topic = new ArrayList<>();
        SQLiteDatabase sqLite = openConnection();
        Cursor cursor = sqLite.rawQuery("select ID,Name, NumberofQuestion from USA_TestTopics ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TestTopicListItem tc = new TestTopicListItem();
            tc.setId(cursor.getInt(0));
            tc.setTopicName(cursor.getString(1));
            tc.setNumberOfQuestion(cursor.getInt(2));
            cursor.moveToNext();
            Topic.add(tc);
        }
        cursor.close();
        closeConnection();
        return Topic;
    }

    //TEST TOPIC
    public ArrayList<Question> getTopicItem(String ID) {
        ArrayList<Question> questions = new ArrayList<>();
        SQLiteDatabase sqLite = openConnection();
        Cursor cursor = sqLite.rawQuery("select * from USA_TestQuestions where TopicID=?", new String[]{ID});
        cursor.moveToFirst();
        int tmp = 1;
        while (!cursor.isAfterLast()) {
            Question question = new Question();
            question.questionNo = tmp;
            question.question = cursor.getString(2);
            byte[] imageData = cursor.getBlob(4);
            if (imageData != null) {
                question.image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            }
            question.choiceA = cursor.getString(5);
            question.choiceB = cursor.getString(6);
            question.choiceC = cursor.getString(7);
            question.choiceD = cursor.getString(8);
            switch (cursor.getString(9)) {
                case "A":
                    question.correctAnswer = 0;
                    break;
                case "B":
                    question.correctAnswer = 1;
                    break;
                case "C":
                    question.correctAnswer = 2;
                    break;
                case "D":
                    question.correctAnswer = 3;
                    break;
            }
            question.explanation = cursor.getString(10);
            questions.add(question);
            tmp++;
            cursor.moveToNext();
        }
        cursor.close();
        closeConnection();
        return questions;
    }

    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        SQLiteDatabase sqLite = openConnection();
        Cursor cursor = sqLite.rawQuery("select * from USA_TestQuestions order by Random() limit 30", null);
        cursor.moveToFirst();
        int tmp = 1;
        while (!cursor.isAfterLast()) {
            Question question = new Question();
            question.questionNo = tmp;
            question.question = cursor.getString(2);
            byte[] imageData = cursor.getBlob(4);
            if (imageData != null) {
                question.image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            }
            question.choiceA = cursor.getString(5);
            question.choiceB = cursor.getString(6);
            question.choiceC = cursor.getString(7);
            question.choiceD = cursor.getString(8);
            switch (cursor.getString(9)) {
                case "A":
                    question.correctAnswer = 0;
                    break;
                case "B":
                    question.correctAnswer = 1;
                    break;
                case "C":
                    question.correctAnswer = 2;
                    break;
                case "D":
                    question.correctAnswer = 3;
                    break;
            }
            question.explanation = cursor.getString(10);
            questions.add(question);
            tmp++;
            cursor.moveToNext();
        }
        cursor.close();
        closeConnection();
        return questions;
    }

    public ArrayList<String> getStates() {
        ArrayList<String> states = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = openConnection();
        String query = "select StateName from USA_States";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int stateNameIndex = cursor.getColumnIndex(DB_STATE_NAME);
            while (!cursor.isAfterLast()) {
                states.add(cursor.getString(stateNameIndex).toUpperCase());
                cursor.moveToNext();
            }
        }
        return states;
    }
}