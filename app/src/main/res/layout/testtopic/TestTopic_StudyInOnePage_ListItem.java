package layout.testtopic;

import android.graphics.Bitmap;

/**
 * Created by Nguyen Huu Thanh on 8/2/2015.
 */
public class TestTopic_StudyInOnePage_ListItem {
    private int id;
    private int topicID;
    private String question;
    private String imageName;
    private Bitmap imageData;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;
    private String explain;


    public TestTopic_StudyInOnePage_ListItem(int id,int topicID,String question,String imageName,Bitmap imageData,String A,String B,String C
            ,String D,String correctAnswer,String explain){
        this.id = id;
        this.topicID = topicID;
        this.question = question;
        this.imageName = imageName;
        this.imageData = imageData;
        this.answerA = A;
        this.answerB = B;
        this.answerC = C;
        this.answerD = D;
        this.correctAnswer = correctAnswer;
        this.explain = explain;

    }

    public int getId() {
        return id;
    }

    public int getTopicID() {
        return topicID;
    }

    public String getQuestion() {
        return question;
    }

    public String getImageName() {
        return imageName;
    }

    public Bitmap getImageData() {
        return imageData;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplain() {
        return explain;
    }
}
