package com.example.myapplication;

import android.os.SystemClock;

public class Powers {

    protected int damage;
    protected int reloading;
    protected boolean clicked;

    public Powers(int damage, int reloading){
        this.damage = damage;
        this.reloading  = reloading;
        this.clicked = false;
    }


    public int getReloading() {
        return reloading;
    }


}
