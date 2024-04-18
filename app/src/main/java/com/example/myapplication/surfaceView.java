package com.example.myapplication;

import static com.example.myapplication.AppConstant.IMAGE_TOP;
import static com.example.myapplication.AppConstant.MIDDLE_X;
import static com.example.myapplication.AppConstant.POWERS_Y;
import static com.example.myapplication.AppConstant.POWERX_DELTA_X;
import static com.example.myapplication.AppConstant.canvasHeight;
import static com.example.myapplication.AppConstant.canvasWidth;
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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    Bitmap left;
    Bitmap right;

    TextView textView;
    float sum = 1000;
    boolean gotIntoTheRadius = false;

    Paint hilaPaint = new Paint();
    Paint radiusColor = new Paint();
    Paint paint = new Paint();


    float ratio;
    float x, y;
    int done = 0;
    int powerIndexChoice = 0;
    float yWithFifteen;
    float xWithFifteen;
    float xMinusFifteen;

    float figureIsMovingX;
    float figureIsMovingY;

    float startingPositionXComputer;

    float movingXDelta = 0;
    float movingYDelta = 0;


    private float[] powerX = new float[AppConstant.NUM_OF_POWERS];

    boolean init = true;
    private PowerBar powerBar;

    private ArrayList<MovingPower> movingPowers =  new ArrayList<>();

    private float endPositionX=0;
    private float endPositionY=0;
    private float startingPositionYOfPower;
    private float startingPositionXOfPower;
    private Player player;
    private ComputerPlayer computerPlayer;

    public surfaceView(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();

        temporaryConfiguration();

    }

    // רק לבנתיים מערך של כוחות

    private void temporaryConfiguration() {

        AppConstant.currPowers[0] = new Powers(10, 4, powerRocket);
        AppConstant.currPowers[1] = new Powers(25, 5, powerSnowball);
        AppConstant.currPowers[2] = new Powers(15, 3, powerBoomb);
        AppConstant.currPowers[3] = new Powers(10, 10, powerArrows);

    }

    float deltax = 0;
    float deltaxComputer = 0;

    @Override
    public void run() {


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

                    //כאן יהיה המשחק

                    drawImages(c);
                    startingPositionX += deltax;
                    startingPositionXComputer += deltaxComputer;
                    drawComputerImages(c);

                    computerPlayer.movePlayer();

                    player.setPlayerDirection(deltax);

                    figureIsMovingX = deltax;


                    if (startingPositionX < 0 || startingPositionX + figure1.getWidth() > c.getWidth())
                        deltax = 0;
                    if (startingPositionXComputer < 0 || startingPositionXComputer + figure1.getWidth() > c.getWidth())
                        deltaxComputer = 0;



                    drawPowerBar(c);
                    drawPowers(c);
                    drawTimer(c);
                    SystemClock.sleep(200);

                        if(movingPowers.size() > 0)
                        {
                            // handle moving images
                            thePowerIsMovingOnTheCanvas(c);
                            /*
                            for (int i = 0; i < movingPowers.size(); i++) {
                                movingPowers.get(i).advance(c);
                            }

                             */
                        }
                }

                catch (Exception e) {
                    Log.d("WHY WHY", "run: " + e.getMessage());
                }

                finally {
                    if (c != null) {
                        this.getHolder().unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }

    private int counterTimer = 0;
    private int timerValue = 60;


    // timer
    private void drawTimer(Canvas c) {
        Paint textTimer = new Paint();
        textTimer.setColor(Color.BLACK);
        textTimer.setTextSize(65);
        c.drawText(""+timerValue,canvasWidth - canvasWidth / 8, 100, textTimer);

        counterTimer++;
        if(counterTimer%4==0 && timerValue != 0)// seconds has passed
        {
            timerValue--;
        }


        // if timer ==0 GAMEOVER -> DIALOG OR IMAGE
        // put GAMEOVER in AN IMAGE and show winner

        if (timerValue == 0){
            if(player.getLifeSum() > computerPlayer.getLifeSum()){
                Toast.makeText(getContext(), "YOU WON!! good job!", Toast.LENGTH_SHORT).show();
            }
            else if(player.getLifeSum() < computerPlayer.getLifeSum()){
                Toast.makeText(getContext(), "YOU LOSE..", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), "non of you won this time.. :(", Toast.LENGTH_SHORT).show();

        }

    }


    // מחסיר כמות חיים לדמות ממול בזמן פגיעה
    private void thePowerIsMovingOnTheCanvas(Canvas c) {

        // todo זה עובד רק אם הדיסטנס בדיוק מהאמצע!!!!(לפעמים כן ולפעמים לא)
        // TODO וגם עוצר את כל המסך!!

        for(MovingPower mp :movingPowers)
        {
            mp.advance(c);
            if(mp.checkCollision(computerPlayer.getX(),computerPlayer.getY(),computerPlayer.getRadius()))
            {
                Log.d("Collision", "thePowerIsMovingOnTheCanvas: ");

                computerPlayer.setLifeSum( computerPlayer.getLifeSum() - mp.getDamage());
                gotIntoTheRadius = true;
                SystemClock.sleep(2000);
                movingPowers.remove(mp);

            }
        }
    }


    // drawimg the bitmaps on canvas

    private void drawImages(Canvas c) {

        c.drawBitmap(bitmap, 0, 0, null);
        c.drawBitmap(bitmap2, 0, c.getHeight() - canvasHeight /4, null);
        c.drawBitmap(left, 50, IMAGE_TOP - 100, null);
        c.drawBitmap(right, c.getWidth() - 200, IMAGE_TOP - 100, null);

        player.draw(c);

    }

    private void drawComputerImages(Canvas c) {

        computerPlayer.draw(c);
    }


    // creation of sizes

    private void initGameConsts(Canvas c) {

        // todo - all figures should be in the same size
        AppConstant.IMAGE_WIDTH = c.getWidth() / 6; // 6
        AppConstant.IMAGE_HEIGHT = c.getHeight() / 7; // 7

        AppConstant.MIDDLE_X = c.getWidth()/2;
        AppConstant.POWERS_Y = c.getHeight() - (c.getHeight() / 33) * 8;
        AppConstant.IMAGE_TOP = c.getHeight() - (c.getHeight() / 5) * 2;

        AppConstant.POWERX_DELTA_X= (c.getWidth() / 13) * 2;
        startingPositionX = MIDDLE_X - 100;
        startingPositionXComputer = MIDDLE_X - 100;


        canvasWidth = c.getWidth();
        canvasHeight = c.getWidth();


        setPowerX(c);
        figureIsMovingX = bitmap.getWidth() / 20;
        figureIsMovingY = bitmap.getHeight() - bitmap.getHeight() / 6;

        // startingPositionX = bitmap.getWidth() / 20;
        startingPositionY = bitmap.getHeight() - bitmap.getHeight() / 6;
        bitmap = Bitmap.createScaledBitmap(bitmap, c.getWidth(), c.getHeight() - c.getHeight() / 4, false);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, c.getWidth(), c.getHeight() - (int) ((c.getHeight() / 3.0) * 4), false);
        figure1 = Bitmap.createScaledBitmap(figure1, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);
        figure2 = Bitmap.createScaledBitmap(figure2, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);
        figure3 = Bitmap.createScaledBitmap(figure3, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);
        figure4 = Bitmap.createScaledBitmap(figure4, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);
        figure5 = Bitmap.createScaledBitmap(figure5, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);

        left = Bitmap.createScaledBitmap(left, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT - 100, false);
        right = Bitmap.createScaledBitmap(right,AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT - 100, false);


        init = false;

        player = new Player(startingPositionX, figureIsMovingY, figure1, currPowers, sum, powerBar.getLoad(),getContext());
        computerPlayer = new ComputerPlayer(MIDDLE_X-100, canvasHeight - 8 * canvasHeight / 9, figure1, currPowers, sum, powerBar.getLoad(), getContext());



    }


    // creation of colors, bitmaps and powerBar

    private void initUIElements() {

        hilaPaint.setColor(getResources().getColor(R.color.hila));
        hilaPaint.setStyle(Paint.Style.STROKE);
        hilaPaint.setStrokeWidth(4);

        radiusColor.setColor(getResources().getColor(R.color.radius));
        radiusColor.setStyle(Paint.Style.STROKE);
        radiusColor.setStrokeWidth(5);

        paint.setColor(Color.BLACK);
        paint.setTextSize(35);


        this.powerBar = new PowerBar();
        this.powerBar.startLoading();
        drawBitmaps();

    }


    // creation of powers and figures

    private void drawBitmaps() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.canvas);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.continue_c);
        figure1 = BitmapFactory.decodeResource(getResources(), R.drawable.figure1);
        figure2 = BitmapFactory.decodeResource(getResources(), R.drawable.figure2);
        figure3 = BitmapFactory.decodeResource(getResources(), R.drawable.figure3);
        figure4 = BitmapFactory.decodeResource(getResources(), R.drawable.figure4);
        figure5 = BitmapFactory.decodeResource(getResources(), R.drawable.figure5);

        left = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        right = BitmapFactory.decodeResource(getResources(), R.drawable.right);

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


    // מעדכנים מערך במקום של האיקסים של הכוחות

    private void setPowerX(Canvas c) {
        float deltaX = POWERX_DELTA_X;
        float x = deltaX;
        for (int i = 0; i < AppConstant.NUM_OF_POWERS; i++) {
            powerX[i] = x;
            x += deltaX * 1.1f;
        }
    }


    // יצירת הפווארבר
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


    // יצירת הכוחות והמספרים של הטעינה שלהם
    private void drawPowers(Canvas c) {
        float deltaX = POWERX_DELTA_X;
        float y = POWERS_Y;
        float deltaY = deltaX * 1.65f;

        for (int i = 0; i < AppConstant.NUM_OF_POWERS; i++) {
            Paint p = new Paint();
            p.setColor(getResources().getColor(R.color.powersBar));
            Rect rect = new Rect((int)powerX[i], (int)y, (int)(powerX[i] + deltaX), (int)(y + deltaY));


            c.drawRect(rect, p);

            // הוספת מספרים לכוחות - טעינה

            Paint paint2 = new Paint();
            paint2.setColor(getResources().getColor(R.color.purple));
            paint2.setTextSize(40);
            paint2.setTextAlign(Paint.Align.CENTER);

            c.drawText(String.valueOf(currPowers[i].reloading), rect.centerX(),rect.bottom , paint2);

        }

        deltaX = POWERX_DELTA_X;
        x = deltaX;
        y = POWERS_Y;
        deltaY = deltaX * 1.3f;
        yWithFifteen = y + 15;
        xWithFifteen = x + 15;
        xMinusFifteen = x - 15;



        Powers[] currPowers = AppConstant.currPowers;

        Powers pRocket = new Powers(10, 4, powerRocket);
        currPowers[0] = pRocket;
        Powers pSnowball = new Powers(25, 5, powerSnowball);
        currPowers[1] = pSnowball;
        Powers pBoomb = new Powers(15, 3, powerBoomb);
        currPowers[2] = pBoomb;
        Powers pArrows = new Powers(10, 10, powerArrows);
        currPowers[3] = pArrows;

        /*
        Powers pRocket = new Powers(110, 7, powerRocket);
        currPowers[0] = pRocket;
        Powers pRock = new Powers(54, 3, powerRock);
        currPowers[1] = pRock;
        Powers pFireball = new Powers(89, 4, powerFireball);
        currPowers[2] = pFireball;
        Powers pArrows = new Powers(75, 3, powerArrows);
        currPowers[3] = pArrows;
*/

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


            // touch - start the deltaX
            case MotionEvent.ACTION_DOWN:
                // check if one of the buttons
                if(handleRightLeftClick(event.getX(),event.getY(),0))
                    return true;
                break;

                // always stop deltaX
            case MotionEvent.ACTION_UP:
                if(handleRightLeftClick(event.getX(),event.getY(),1))
                    return true;

                // this way in case it is right left buttons
                // no need for powers logic


                if (done == 1) {
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
                        // todo today red
                        // if there is enough load
                        if(currPowers[i].reloading <= powerBar.getLoad())
                        {
                            index = i;

                            clickCounter=1;
                            startingPositionYOfPower = y1;
                            startingPositionXOfPower = x1;

                        }
                        else
                            // optional shake phone...
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

                        MovingPower m = new MovingPower(MIDDLE_X-100,IMAGE_TOP,currPowers[powerIndexChoice].getBitmap(),ratio,currPowers[powerIndexChoice].getDamage());
                        movingPowers.add(m);


                        done = 1;
                        powerBar.setLoad(powerBar.getLoad()-currPowers[powerIndexChoice].getReloading());
                        clickCounter=0;
                    }
                }


                break;

        }
        return true;
    }

    /*
    hi please make a logo for game named "Jesse Striker" with this text on it
and put smiley strong woman with fun colors  figure (shooting)
     */

    private boolean handleRightLeftClick(float x1, float y1,int upDown) {

        // if it is down - set deltaX
        if(upDown == 0) // DOWN
        {
            if (x1 < canvasWidth - (canvasWidth - left.getWidth() - 50) && x1 > 50 && y1 > IMAGE_TOP - 100 && y1 < IMAGE_TOP){//left.getHeight() + (IMAGE_TOP - 100)) {
                    deltax = -10;

                    return true;
            }
            else if (x1 > canvasWidth - 200 && x1 < canvasWidth - 200 + right.getWidth() && y1 > IMAGE_TOP-100 && y1 < IMAGE_TOP){//right.getHeight() + (IMAGE_TOP - 100)) {
                    deltax = 10;
                  return true;

                // todo לעצור את הדמות כשלא נוגעים במסך יותר או שכשהיא מגיעה לסוף
            }
        }
        else //UP
        {
            if (x1 < canvasWidth - (canvasWidth - left.getWidth() - 50) && x1 > 50 && y1 > left.getHeight() && y1 < left.getHeight() + (IMAGE_TOP - 100)) {
                deltax =0;
                return true;
            }
            else if (x1 > canvasWidth - 200 && x1 < canvasWidth - 200 + right.getWidth() && y1 > right.getHeight() && y1 < right.getHeight() + (IMAGE_TOP - 100)) {
                deltax =0;
                return true;

                // todo לעצור את הדמות כשלא נוגעים במסך יותר או שכשהיא מגיעה לסוף
            }


        }
        return false;

        // if it is up -> set Deltax to zero
    }

}
