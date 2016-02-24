package com.essential.usdriving.entity;

import android.graphics.Bitmap;


public class Card  {
    public String cardTerm;
    public String cardDefinition;
    public Bitmap image;
    public boolean check;
    public int myAnswer = BaseEntity.ANSWER_NOT_CHOOSE;
    public Card(){

    }

    public String getCardTerm() {
        return cardTerm;
    }

    public void setCardTerm(String cardTerm) {
        this.cardTerm = cardTerm;
    }

    public String getCardDefinition() {
        return cardDefinition;
    }

    public void setCardDefinition(String cardDefinition) {
        this.cardDefinition = cardDefinition;
    }


    public boolean isChecked() {
        return false;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
