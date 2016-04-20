package com.essential.usdriving.ui.test_topic;

/**
 * Created by Nguyen Huu Thanh on 8/1/2015.
 */
public class TestTopicListItem {

    private int id;
    private String topicName;
    private int numberOfQuestion;
    private boolean close = true;
    private boolean mIsLocked = false;

    public TestTopicListItem() {

    }

    public TestTopicListItem(int id, String topicName, int numberOfQuestion) {
        this.id = id;
        this.topicName = topicName;
        this.numberOfQuestion = numberOfQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public boolean isLocked() {
        return mIsLocked;
    }

    public void setLocked(boolean isLocked) {
        this.mIsLocked = isLocked;
    }
}
