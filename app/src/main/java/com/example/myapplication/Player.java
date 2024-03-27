package com.example.myapplication;

import android.graphics.Bitmap;

public class Player extends Shape {

    protected Bitmap bitmap;
    protected Powers[] powers;
    protected float lifeSum;
    protected int powerValue;
    protected int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Powers[] getPowers() {
        return powers;
    }

    public void setPowers(Powers[] powers) {
        this.powers = powers;
    }

    public float getLifeSum() {
        return lifeSum;
    }

    public void setLifeSum(float lifeSum) {
        this.lifeSum = lifeSum;
    }

    public int getPowerValue() {
        return powerValue;
    }

    public void setPowerValue(int powerValue) {
        this.powerValue = powerValue;
    }

    public Player() {
    }

    public Player(Bitmap bitmap, Powers[] powers, float lifeSum, int powerValue) {
        this.bitmap = bitmap;
        this.powers = powers;
        this.lifeSum = lifeSum;
        this.powerValue = powerValue;
    }

    public Player(float x, float y, Bitmap bitmap, Powers[] powers, float lifeSum, int powerValue) {
        super(x, y);
        this.bitmap = bitmap;
        this.powers = powers;
        this.lifeSum = lifeSum;
        this.powerValue = powerValue;
    }

    public Player(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.powers = new Powers[AppConstant.NUM_OF_POWERS];
    }

    public Player(Powers[] powers, float lifeSum) {
        this.powers = powers;
        this.lifeSum = lifeSum;
    }


    public Player(float x, float y, Bitmap bitmap, Powers[] powers, float lifeSum) {
        super(x, y);
        this.bitmap = bitmap;
        this.powers = powers;
        this.lifeSum = lifeSum;
    }


}
