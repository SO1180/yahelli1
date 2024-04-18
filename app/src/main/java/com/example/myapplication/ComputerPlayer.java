package com.example.myapplication;

import static com.example.myapplication.AppConstant.canvasHeight;
import static com.example.myapplication.AppConstant.canvasWidth;
import static com.example.myapplication.AppConstant.startingPositionX;
import static com.example.myapplication.AppConstant.startingPositionXComputer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ComputerPlayer extends Player{


    public void movePlayer()
    {
       x+=deltaX;
       if(x>= canvasWidth-100)
           deltaX = -deltaX;
       else if(x<=10)
           deltaX = -deltaX;
    }

    @Override
    public void setPlayerDirection(float deltaX) {
        super.setPlayerDirection(deltaX);
    }

    public ComputerPlayer(float x, float y, Bitmap bitmap, Powers[] powers, float lifeSum, int powerValue, Context context) {
        super(x, y);
        this.bitmap = bitmap;
        this.powers = powers;
        this.lifeSum = lifeSum;
        this.powerValue = powerValue;
        this.hilaPaint = new Paint();
        hilaPaint.setColor(context.getResources().getColor(R.color.hila));
        hilaPaint.setStyle(Paint.Style.STROKE);
        hilaPaint.setStrokeWidth(4);

        this.paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35);
        deltaX = 10;
    }

    @Override
    public void draw(Canvas c) {

        // startingPositionX
        c.drawText(""+lifeSum,x + bitmap.getWidth() / 4, (canvasHeight - 8 * canvasHeight / 9) - 20, paint);
        c.drawCircle(x + bitmap.getWidth() / 2,  (canvasHeight - 8 * canvasHeight / 9) + bitmap.getHeight() / 2, bitmap.getWidth() * 1.25f, hilaPaint);

        super.draw(c);
    }


}
