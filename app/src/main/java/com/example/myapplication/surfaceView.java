package com.example.myapplication;

import static com.example.myapplication.AppConstant.currPowers;
import static com.example.myapplication.AppConstant.startingPositionX;
import static com.example.myapplication.AppConstant.startingPositionY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class surfaceView extends SurfaceView implements Runnable {

    boolean threadRunning = true;
    boolean isRunning = true;
    Context context;
    SurfaceHolder holder;
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap figure1;
    Bitmap figure2;
    Bitmap figure3;
    Bitmap figure4;
    Bitmap figure5;
    Bitmap powerArrows;
    Bitmap powerBoomb;
    Bitmap powerRocket;
    Bitmap powerSnowball;
    Bitmap powerFireball;
    Bitmap powerBoomerang;
    Bitmap powerRanDanker;
    Bitmap powerRock;
    Bitmap powerSnowflake;
    Bitmap powerWoodenlog;




    boolean init = true;
    private PowerBar powerBar;


    public surfaceView(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();



        temporaryConfiguration();

    }

    private void temporaryConfiguration() {

        AppConstant.currPowers[0] = new Powers(10,4,powerRocket);
        AppConstant.currPowers[1] = new Powers(25,5,powerSnowball);
        AppConstant.currPowers[2] = new Powers(15,3,powerBoomb);
        AppConstant.currPowers[3] = new Powers(10,2,powerArrows);

    }


    @Override
    public void run() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_1);
        figure1 = BitmapFactory.decodeResource(getResources(), R.drawable.figure1);
        figure2 = BitmapFactory.decodeResource(getResources(), R.drawable.figure2);
        figure3 = BitmapFactory.decodeResource(getResources(), R.drawable.figure3);
        figure4 = BitmapFactory.decodeResource(getResources(), R.drawable.figure4);
        figure5 = BitmapFactory.decodeResource(getResources(), R.drawable.figure5);

        powerArrows = BitmapFactory.decodeResource(getResources(), R.drawable.arrows);
        powerRocket = BitmapFactory.decodeResource(getResources(), R.drawable.rocket);
        powerBoomb = BitmapFactory.decodeResource(getResources(), R.drawable.boomb);
        powerSnowball = BitmapFactory.decodeResource(getResources(), R.drawable.snowball);
        powerFireball = BitmapFactory.decodeResource(getResources(), R.drawable.fireball);
        powerBoomerang = BitmapFactory.decodeResource(getResources(), R.drawable.boomerang);
        powerRanDanker = BitmapFactory.decodeResource(getResources(), R.drawable.randanker);
        powerRock = BitmapFactory.decodeResource(getResources(), R.drawable.rock);
        powerSnowflake = BitmapFactory.decodeResource(getResources(), R.drawable.snowflake);
        powerWoodenlog = BitmapFactory.decodeResource(getResources(), R.drawable.woodenlog);


        this.powerBar = new PowerBar();
        this.powerBar.startLoading();
        while (threadRunning) { // כל המשחק ממשיך לפעול


            if (isRunning) { //כשמישהו מנצח.מפסיד
                if (!holder.getSurface().isValid())
                    continue;

                Canvas c = null;
                try {
                    c = this.getHolder().lockCanvas();
                    synchronized (this.getHolder()) {

                        if(init) {

                            AppConstant.IMAGE_WIDTH = c.getWidth()/6;
                            AppConstant.IMAGE_HEIGHT = c.getHeight()/7;
                            startingPositionX = bitmap.getWidth()/20;
                            startingPositionY = bitmap.getHeight() - bitmap.getHeight()/6;
                            bitmap = Bitmap.createScaledBitmap(bitmap,c.getWidth(),c.getHeight() - c.getHeight()/4,false);
                            bitmap2 = Bitmap.createScaledBitmap(bitmap2,c.getWidth(),c.getHeight() - (int)((c.getHeight()/3.0)*4),false);
                            figure1 = Bitmap.createScaledBitmap(figure1, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);

                         init = false;
                        }
                        //כאן יהיהה המשחק

                        c.drawBitmap(bitmap,0,0,null);
                        c.drawBitmap(bitmap2,0,c.getHeight()-c.getHeight()/4,null);
                        c.drawBitmap(figure1, startingPositionX , startingPositionY, null);

                        float deltax = 10;
                        startingPositionX += deltax;

                        if (startingPositionX < 0 || startingPositionX > c.getWidth() - figure1.getWidth())
                            deltax = -deltax;


                        drawPowerBar(c);
                        drawPowers(c);
                        SystemClock.sleep(200);


                    }

                } catch (Exception e) {
                    Log.d("WHY WHY", "run: " + e.getMessage());

                } finally {
                    if (c != null) {
                        this.getHolder().unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }

    private void drawPowerBar(Canvas c)
    {
        int x = 0;
        int y = c.getHeight() - c.getHeight()/12;
        int deltaX = c.getWidth()/10;
        int deltaY =deltaX;

        Paint p = new Paint();
        Paint drawPaint = new Paint();
        drawPaint.setTextAlign(Paint.Align.CENTER);
        drawPaint.setTextSize(40);  //set text size



        for (int i = 0; i < powerBar.getLoad() ; i++) {
            p.setColor(getResources().getColor(R.color.purple_200));
            Rect rect = new Rect(x, y, x+deltaX, y+deltaY);
            c.drawRect(rect, p);
            c.drawText("" + (i+1), rect.centerX(),rect.centerY() + 10,drawPaint);
            x+=deltaX;
        }
        for (int i = powerBar.getLoad(); i <AppConstant.MAX_NUM_OF_BARS ; i++) {
            p.setColor(getResources().getColor(R.color.purple));
            c.drawRect(x, y, x+deltaX, y+deltaY, p);
            x+=deltaX;
        }
    }


    private void drawPowers(Canvas c)
    {
        float deltaX = (c.getWidth()/13)*2;
        float x = deltaX;
        float y = c.getHeight() - (c.getHeight()/33)*8;
        float deltaY =deltaX*1.65f;

        for (int i = 0; i < AppConstant.NUM_OF_POWERS; i++)
        {
            Paint p = new Paint();
            p.setColor(getResources().getColor(R.color.powersBar));
            c.drawRect(x, y, x+deltaX, y+deltaY, p);
            x+=deltaX*1.1f ;
        }

        deltaX = (c.getWidth()/13)*2;
        x = deltaX;
        y = c.getHeight() - (c.getHeight()/33)*8;
        deltaY =deltaX*1.3f;

        float powersRight = (c.getWidth()/13)*2;
        float powersLeft = c.getHeight() - (c.getHeight()/33)*8;



        Powers[] currPowers = AppConstant.currPowers;

        Powers pRocket = new Powers(110, 7, powerRocket);
        currPowers[0] = pRocket;
        Powers pRock = new Powers(54, 3, powerRock);
        currPowers[1] = pRock;
        Powers pFireball = new Powers(89, 4, powerFireball);
        currPowers[2] = pFireball;
        Powers pArrows = new Powers(75, 3, powerArrows);
        currPowers[3] = pArrows;



        for (int i = 0; i < AppConstant.NUM_OF_POWERS; i++)
        {
            Paint p2 = new Paint();
            p2.setColor(getResources().getColor(R.color.white));
            c.drawRect(x + 15, y + 15, x+deltaX - 15, y+deltaY + 15, p2);

            Bitmap b = Bitmap.createScaledBitmap(AppConstant.currPowers[i].getBitmap(),(int)deltaX,(int)deltaY,false);
            c.drawBitmap(b, x+15, y + 15, null);

            x+=deltaX*1.1f ;

        }

    }


}
