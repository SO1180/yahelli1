package com.example.myapplication;

import android.graphics.Bitmap;

public class MovingPower extends Powers{

    protected float x;
    protected float y;

    public MovingPower(Powers p, float x, float y) {
        super(p.damage, p.reloading, p.bitmap);
    }
}
