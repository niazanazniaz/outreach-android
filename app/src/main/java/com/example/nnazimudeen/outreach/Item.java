package com.example.nnazimudeen.outreach;

import android.graphics.drawable.Drawable;

/**
 * Created by nnazimudeen on 4/10/15.
 */
public class Item
{
    private String name;
    private Drawable image;


    public Item(String name, Drawable image)
    {
        super();
        this.name = name;
        this.image = image;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Drawable getImage()
    {
        return image;
    }
    public void setImage(Drawable image)
    {
        this.image = image;
    }

}