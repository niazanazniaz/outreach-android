package com.example.nnazimudeen.outreach;

import android.graphics.drawable.Drawable;

/**
 * Created by nnazimudeen on 4/29/15.
 */
public class Score {
    private String name;
    private int score;


    public Score(String name, int score)
    {
        super();
        this.name = name;
        this.score = score;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getScore()
    {
        return score;
    }
    public void setScore(int score)
    {
        this.score = score;
    }

}