package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.SystemClock;

public class Powers {

    protected int damage;
    protected int reloading;
    protected boolean clicked;
    protected Bitmap bitmap;

    public Powers(int damage, int reloading){
        this.damage = damage;
        this.reloading  = reloading;
        this.clicked = false;
    }

    public Powers(int damage, int reloading, Bitmap bitmap) {
        this.damage = damage;
        this.reloading = reloading;
        this.bitmap = bitmap;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setReloading(int reloading) {
        this.reloading = reloading;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getReloading() {
        return reloading;
    }


}
