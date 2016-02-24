package com.essential.usdriving.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;


import com.essential.usdriving.entity.Card;
import com.essential.usdriving.entity.TopicCard;

import java.util.ArrayList;

import tatteam.com.app_common.sqlite.BaseDataSource;


public class DataSource  extends BaseDataSource{
    private static DataSource instance;
    private Context context;
    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }
    public void init(Context context) {
        this.context = context;
    }

    public static int countExams() {
        //open connection
        SQLiteDatabase sqLite = openConnection();

        Cursor cursor = sqLite.rawQuery("select count(1) from Exams", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        //close cursor
        cursor.close();

        //close connection
        closeConnection();

        return count;
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
            TopicCard tc=new TopicCard();
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
            Card tc=new Card();
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
}