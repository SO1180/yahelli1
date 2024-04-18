package com.example.myapplication;

import static com.example.myapplication.AppConstant.canvasHeight;
import static com.example.myapplication.AppConstant.startingPositionX;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends Shape {

    protected Bitmap bitmap;
    protected Powers[] powers;
    protected float lifeSum;
    protected int powerValue;
    protected int direction;
    Paint hilaPaint ;
    Paint paint;
    protected int deltaX;

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

    public Player(float x, float y) {
        super(x,y);
    }

    public Player(Bitmap bitmap, Powers[] powers, float lifeSum, int powerValue) {
        this.bitmap = bitmap;
        this.powers = powers;
        this.lifeSum = lifeSum;
        this.powerValue = powerValue;
    }

    public Player(float x, float y, Bitmap bitmap, Powers[] powers, float lifeSum, int powerValue, Context context) {
        super(x, y);
        this.bitmap = bitmap;
        this.powers = powers;
        this.lifeSum = lifeSum;
        this.powerValue = powerValue;
        this.hilaPaint = new Paint();
        hilaPaint.setColor(context.getResources().getColor(R.color.hila));
        hilaPaint.setStyle(Paint.Style.STROKE);
        hilaPaint.setStrokeWidth(4);
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


    public void draw(Canvas c)
    {
        c.drawBitmap(bitmap, x, y, null);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35);

        c.drawText(""+lifeSum,x + bitmap.getWidth() / 4, y-20, paint);
        c.drawCircle(x + bitmap.getWidth() / 2,  y + bitmap.getHeight() / 2, bitmap.getWidth() * 1.25f, hilaPaint);
    }


    public void setPlayerDirection(float deltaX)
    {
        x+=deltaX;
    }

    public float getRadius() {
        return bitmap.getWidth() * 1.25f;
    }


}
