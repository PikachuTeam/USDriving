package com.essential.usdriving.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.essential.usdriving.database.DataSource;

/**
 * Created by the_e_000 on 8/5/2015.
 */
public class Question implements Parcelable {
    public int questionNo;
    public String question, choiceA, choiceB, choiceC, choiceD;
    public String explanation;
    public Bitmap image;
    public int myAnswer ;
    public int correctAnswer;

    public Question() {
        myAnswer=-1;
    }

    protected Question(Parcel in) {
        questionNo = in.readInt();
        question = in.readString();
        choiceA = in.readString();
        choiceB = in.readString();
        choiceC = in.readString();
        choiceD = in.readString();
        explanation = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
        myAnswer = in.readInt();
        correctAnswer = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionNo);
        dest.writeString(question);
        dest.writeString(choiceA);
        dest.writeString(choiceB);
        dest.writeString(choiceC);
        dest.writeString(choiceD);
        dest.writeString(explanation);
        dest.writeParcelable(image, flags);
        dest.writeInt(myAnswer);
        dest.writeInt(correctAnswer);
    }
}
