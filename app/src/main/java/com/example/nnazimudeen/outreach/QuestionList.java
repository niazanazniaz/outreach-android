package com.example.nnazimudeen.outreach;

import android.graphics.drawable.Drawable;

import org.json.JSONArray;

/**
 * Created by nnazimudeen on 4/17/15.
 */
public final class QuestionList {

    boolean populated =false;
    static JSONArray quesList = null;

    public boolean isPopulated()
    {
        return populated;
    }
    public void setPopulated(boolean populated)
    {
        this.populated = populated;
    }
    public JSONArray getQuesList()
    {
        return quesList;
    }
    public void setQuesList(JSONArray quesList)
    {
        this.quesList = quesList;
    }
}
