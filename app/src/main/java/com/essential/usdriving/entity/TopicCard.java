package com.essential.usdriving.entity;

/**
 * Created by VULAN on 11/15/2015.
 */
public class TopicCard {
    public String topic;
    private int ID;
    public int number;

    public TopicCard(){

    }
    public TopicCard(String topic, int number,int ID){
        this.setTopic(topic);
        this.setNumber(number);
        this.setID(ID);
    }
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
