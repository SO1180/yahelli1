package com.example.myapplication;

import static com.example.myapplication.AppConstant.AllPowers;
import static com.example.myapplication.AppConstant.POWERS_Y;
import static com.example.myapplication.AppConstant.POWERX_DELTA_X;
import static com.example.myapplication.AppConstant.POWERY_DELTA_Y;
import static com.example.myapplication.AppConstant.currPowers;
import static com.example.myapplication.AppConstant.startingPositionX;
import static com.example.myapplication.AppConstant.startingPositionY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
    TextView textView;
    float sum = 1000;

    Paint hilaPaint = new Paint();
    Paint radiusColor = new Paint();

    float ratio;
    float x, y;
    int done = 0;
    int powerIndexChoice = 0;
    float yWithFifteen;
    float xWithFifteen;
    float xMinusFifteen;


    float movingXDelta = 0;
    float movingYDelta = 0;


    private float[] powerX = new float[AppConstant.NUM_OF_POWERS];

    boolean init = true;
    private PowerBar powerBar;
    private float endPositionX=0;

    private float endPositionY=0;

    public surfaceView(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();


        temporaryConfiguration();

    }

    private void temporaryConfiguration() {

        AppConstant.currPowers[0] = new Powers(10, 4, powerRocket);
        AppConstant.currPowers[1] = new Powers(25, 5, powerSnowball);
        AppConstant.currPowers[2] = new Powers(15, 3, powerBoomb);
        AppConstant.currPowers[3] = new Powers(10, 10, powerArrows);

    }


    @Override
    public void run() {

        float deltax = 10;

        initUIElements();

        while (threadRunning) { // כל המשחק ממשיך לפעול

            if (isRunning) { //כשמישהו מנצח.מפסיד
                if (!holder.getSurface().isValid())
                    continue;

                Canvas c = null;
                try {
                    c = this.getHolder().lockCanvas();
                    synchronized (this.getHolder())
                    {
                        if (init)
                            initGameConsts(c);
                    }

                    //כאן יהיהה המשחק

                    drawImages(c);
                    startingPositionX += deltax;

                    if (startingPositionX < 0 || startingPositionX + figure1.getWidth() > c.getWidth() - c.getWidth() / 6)
                        deltax = -deltax;


                    drawPowerBar(c);
                    drawPowers(c);
                    SystemClock.sleep(200);

                    float movingPowerX = AppConstant.MIDDLE_X;
                    float movingPowerY = AppConstant.IMAGE_TOP;

                    // to move the power in a way
                    // how to use the power
                    // POWERX_DELTA_X + 15, POWERS_Y + 15

                        Bitmap useingNowBitmap;
                        if (done == 1)
                        {

                            useingNowBitmap = Bitmap.createScaledBitmap(AppConstant.currPowers[powerIndexChoice].getBitmap(), (int)AppConstant.IMAGE_WIDTH, (int) AppConstant.IMAGE_HEIGHT, false);
                            c.drawBitmap(useingNowBitmap, movingPowerX - 100 + movingXDelta, movingPowerY+movingYDelta, null);
                            c.drawCircle(endPositionX, endPositionY , useingNowBitmap.getWidth() * 1.25f, radiusColor);



                            // check collision
                            //  distance upper right corner
                            // distance upper left coprner
                            // radius = Math.sqrt(

                            float v = (float) Math.pow((endPositionX - (movingPowerX - 100 + movingXDelta)), 2);
                            float w = (float) Math.pow((endPositionY - (movingPowerY + movingYDelta)), 2);
                            float distance = (float) Math.sqrt(v + w);

                            if(distance > useingNowBitmap.getWidth() * 1.25f && distance > useingNowBitmap.getWidth() * 1.25f){
                                movingXDelta += ratio * 20;
                                movingYDelta -= 20;
                            }
                        }

                        // endPositionX -> all power radious

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

    private void drawImages(Canvas c) {

        c.drawBitmap(bitmap, 0, 0, null);
        c.drawBitmap(bitmap2, 0, c.getHeight() - c.getHeight() / 4, null);
        c.drawBitmap(figure1, startingPositionX, startingPositionY, null);
        //c.drawText(sum, startingPositionX, startingPositionY + startingPositionY /20, null);

        c.drawCircle(startingPositionX + figure1.getWidth() / 2, startingPositionY + figure1.getHeight() / 2, figure1.getWidth() * 1.25f, hilaPaint);

    }

    private void initGameConsts(Canvas c) {

        AppConstant.IMAGE_WIDTH = c.getWidth() / 6; // 6
        AppConstant.IMAGE_HEIGHT = c.getHeight() / 7; // 7

        AppConstant.MIDDLE_X = c.getWidth()/2;
        AppConstant.POWERS_Y = c.getHeight() - (c.getHeight() / 33) * 8;
        AppConstant.IMAGE_TOP = c.getHeight() - (c.getHeight() / 5) * 2;

        AppConstant.POWERX_DELTA_X= (c.getWidth() / 13) * 2;


        setPowerX(c);
        startingPositionX = bitmap.getWidth() / 20;
        startingPositionY = bitmap.getHeight() - bitmap.getHeight() / 6;
        bitmap = Bitmap.createScaledBitmap(bitmap, c.getWidth(), c.getHeight() - c.getHeight() / 4, false);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, c.getWidth(), c.getHeight() - (int) ((c.getHeight() / 3.0) * 4), false);
        figure1 = Bitmap.createScaledBitmap(figure1, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);
        figure2 = Bitmap.createScaledBitmap(figure2, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);
        figure3 = Bitmap.createScaledBitmap(figure3, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);
        figure4 = Bitmap.createScaledBitmap(figure4, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);
        figure5 = Bitmap.createScaledBitmap(figure5, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);


        // טיימר
                     /*       CountDownTimer timer = new CountDownTimer(60000, 1000) {
                                @Override
                                public void onTick(long l) {

                                }

                                @Override
                                public void onFinish() {

                                }
                            };

                      */

        init = false;

    }

    private void initUIElements() {

        hilaPaint.setColor(getResources().getColor(R.color.hila));
        hilaPaint.setStyle(Paint.Style.STROKE);
        hilaPaint.setStrokeWidth(4);

        radiusColor.setColor(getResources().getColor(R.color.radius));
        radiusColor.setStyle(Paint.Style.STROKE);
        radiusColor.setStrokeWidth(2);

        drawBitmaps();

        this.powerBar = new PowerBar();
        this.powerBar.startLoading();
    }

    private void drawBitmaps() {

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

    }

    private void setPowerX(Canvas c) {
        float deltaX = POWERX_DELTA_X;
        float x = deltaX;
        for (int i = 0; i < AppConstant.NUM_OF_POWERS; i++) {
            powerX[i] = x;
            x += deltaX * 1.1f;
        }
    }



    // טיימר
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        public void onTick(long millisUntilFinished) {
            // Used for formatting digit to be in 2 digits only
            NumberFormat f = new DecimalFormat("00");
            long hour = (millisUntilFinished / 3600000) % 24;
            long min = (millisUntilFinished / 60000) % 60;
            long sec = (millisUntilFinished / 1000) % 60;
         //   textView.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
        }

        // When the task is over it will print 00:00:00 there
        public void onFinish() {
      //      textView.setText("00:00:00");
        }
    }.start();

    private void drawPowerBar(Canvas c) {
        int x = 0;
        int y = c.getHeight() - c.getHeight() / 12;
        int deltaX = c.getWidth() / 10;
        int deltaY = deltaX;

        Paint p = new Paint();
        Paint drawPaint = new Paint();
        drawPaint.setTextAlign(Paint.Align.CENTER);
        drawPaint.setTextSize(40);  //set text size


        for (int i = 0; i < powerBar.getLoad(); i++) {
            p.setColor(getResources().getColor(R.color.purple_200));
            Rect rect = new Rect(x, y, x + deltaX, y + deltaY);
            c.drawRect(rect, p);
            c.drawText("" + (i + 1), rect.centerX(), rect.centerY() + 10, drawPaint);



            x += deltaX;
        }
        for (int i = powerBar.getLoad(); i < AppConstant.MAX_NUM_OF_BARS; i++) {
            p.setColor(getResources().getColor(R.color.purple));
            c.drawRect(x, y, x + deltaX, y + deltaY, p);
            x += deltaX;
        }
    }


    private void drawPowers(Canvas c) {
        float deltaX = POWERX_DELTA_X;
        float y = POWERS_Y;
        float deltaY = deltaX * 1.65f;

        for (int i = 0; i < AppConstant.NUM_OF_POWERS; i++) {
            Paint p = new Paint();
            p.setColor(getResources().getColor(R.color.powersBar));
            Rect rect = new Rect((int)powerX[i], (int)y, (int)(powerX[i] + deltaX), (int)(y + deltaY));


            c.drawRect(rect, p);

            // לעשות את המספרים של הכוחות (טעינה) todo

            Paint paint2 = new Paint();
            paint2.setColor(getResources().getColor(R.color.radius));
            paint2.setTextSize(50);
            paint2.setTextAlign(Paint.Align.CENTER);



            c.drawText(String.valueOf(currPowers[i].reloading), rect.centerX(),rect.bottom+10, paint2);

            //
        }

        deltaX = POWERX_DELTA_X;
        x = deltaX;
        y = POWERS_Y;
        deltaY = deltaX * 1.3f;
        yWithFifteen = y + 15;
        xWithFifteen = x + 15;
        xMinusFifteen = x - 15;



        Powers[] currPowers = AppConstant.currPowers;

        Powers pRocket = new Powers(110, 7, powerRocket);
        currPowers[0] = pRocket;
        Powers pRock = new Powers(54, 3, powerRock);
        currPowers[1] = pRock;
        Powers pFireball = new Powers(89, 4, powerFireball);
        currPowers[2] = pFireball;
        Powers pArrows = new Powers(75, 3, powerArrows);
        currPowers[3] = pArrows;


        for (int i = 0; i < AppConstant.NUM_OF_POWERS; i++) {
            Paint p2 = new Paint();
            p2.setColor(getResources().getColor(R.color.white));
            c.drawRect(x + 15, y + 15, x - 15 + deltaX, y + 15 + deltaY, p2);

            Bitmap b = Bitmap.createScaledBitmap(AppConstant.currPowers[i].getBitmap(), (int) deltaX - 25, (int) deltaY, false);
            c.drawBitmap(b, x + 15, y + 15, null);

            x += deltaX * 1.1f;

        }

    }


    //moving powers

    private int clickCounter = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);


        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:

                // todo לכתוב בצד אחד שבחרת באותו כוח (מה שמיכל אמרה לשים בצד)

                if(done == 1)
                {
                    done = 0;
                }


                float x1 = event.getX();
                float y1 = event.getY();



                 // incase click is odd ->  we use Array of X
                int index = -1;

                for (int i = 0; i < AppConstant.NUM_OF_POWERS; i++) {
                    // if clicked onm one of the powers
                    if(x1 >= powerX[i] && x1 <= powerX[i] + POWERX_DELTA_X && y1 >= yWithFifteen  && y1 <= yWithFifteen + POWERX_DELTA_X)
                    {
                        // if there is enough load
                        if(currPowers[i].reloading <= powerBar.getLoad())
                        {
                            index = i;

                            clickCounter=1;
                            startingPositionY = y1;
                            startingPositionX = x1;

                        }
                        else

                          Toast.makeText(getContext(), "You don't have enough powerLoad for this power", Toast.LENGTH_SHORT).show();

                    }

                }

                // starting position - fixed
                // end position

                // KEEP IT SIMPLE!

                if(index!=-1)
                    powerIndexChoice = index;
                    // did not select a power


                // if index =-1  this means not power selected
                // if clickCounter == 0 -> first clicked not on power ->
                // if clickCounter == 1-> this means that they selected power already
                // and this is second clicked

                if(index==-1) {
                    if (clickCounter == 1) {
                        endPositionX = x1;
                        endPositionY = y1;

                        // finmd ration between source and target

                        // deltaX   delatY

                        ratio = (endPositionX-AppConstant.MIDDLE_X)/(AppConstant.POWERS_Y-endPositionY);
                        done = 1;

                        powerBar.setLoad(powerBar.getLoad()-currPowers[powerIndexChoice].getReloading());
                    }
                }

                break;

        }
        return true;
    }


}
