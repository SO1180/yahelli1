package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MovingPower extends Shape{

   private Bitmap bitmap;
   private float ratio;

    public int getDamage() {
        return damage;
    }

    private int damage;

   private boolean isActive;

    public MovingPower(float x, float y, Bitmap bitmap, float ratio, int damage) {
        super(x, y);
        this.ratio = ratio;
        this.damage = damage;
        isActive = true;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int)AppConstant.IMAGE_WIDTH, (int) AppConstant.IMAGE_HEIGHT, false);

    }

    // 0- normal advance
    // 1 - collision
    // 2 - out of canvas - remove from array
    // receiving the target image!
    public int advance(Canvas c)
    {
        x += ratio * 20;
        y-=20;
        c.drawBitmap(this.bitmap, x, y, null);
        // handle shape
        return 0;
    }


    public boolean checkCollision(float targetX, float targetY, float radius) {

        //x,y
        float v = (float) Math.pow((x+ bitmap.getWidth()/2)- targetX, 2);
        float w = (float) Math.pow((y + bitmap.getHeight()/2) -targetY, 2);
        float distance = ((float) Math.sqrt(v + w));

        return distance<radius;


    }
}
