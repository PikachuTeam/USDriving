package com.essential.usdriving.entity;


import android.graphics.drawable.Drawable;

/**
 * Created by VULAN on 2/19/2016.
 */
public class HomeEntity {
    private String textHome;
    private Drawable iconHome;

    public String getTextHome() {
        return textHome;
    }

    public void setTextHome(String textHome) {
        this.textHome = textHome;
    }

    public Drawable geticonHome() {
        return iconHome;
    }

    public void setIconHome(Drawable iconHome) {
        this.iconHome = iconHome;
    }
    public HomeEntity(){

    }
    public HomeEntity(String textHome, Drawable iconHome){
        setTextHome(textHome);
        setIconHome(iconHome);
    }
}
