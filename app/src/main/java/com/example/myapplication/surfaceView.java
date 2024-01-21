package com.example.myapplication;

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
    Bitmap figure1;
    Bitmap figure2;
    Bitmap figure3;
    Bitmap figure4;
    Bitmap figure5;
    boolean init = true;
    private PowerBar powerBar;

    public surfaceView(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();

    }


    @Override
    public void run() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_1);
        figure1 = BitmapFactory.decodeResource(getResources(), R.drawable.figure1);
        figure2 = BitmapFactory.decodeResource(getResources(), R.drawable.figure2);
        figure3 = BitmapFactory.decodeResource(getResources(), R.drawable.figure3);
        figure4 = BitmapFactory.decodeResource(getResources(), R.drawable.figure4);
        figure5 = BitmapFactory.decodeResource(getResources(), R.drawable.figure5);
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
                            AppConstant.IMAGE_HEIGHT = c.getHeight()/6;
                            startingPositionX = c.getWidth()/20;
                            startingPositionY = c.getHeight() - c.getHeight()/5;
                            bitmap = Bitmap.createScaledBitmap(bitmap,c.getWidth(),c.getHeight() - c.getHeight()/10,false);
                         //  this.setBackground(bitmap);
                            figure1 = Bitmap.createScaledBitmap(figure1, AppConstant.IMAGE_WIDTH, AppConstant.IMAGE_HEIGHT, false);

                         init = false;
                        }
                  //      c.drawBitmap(bitmap, 0,0, null);
                     //   c.drawBitmap(figure1, 0,0, null);
                        //כאן יהיהה המשחק

                        c.drawBitmap(bitmap,0,0,null);

                        c.drawBitmap(figure1, startingPositionX , startingPositionY, null);

                        drawPower(c);
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

    private void drawPower(Canvas c)
    {
        float x = 0;
        float y = c.getHeight() - c.getHeight()/10;
        float deltaX = c.getWidth()/10;
        float deltaY =deltaX;


        for (int i = 0; i < powerBar.getLoad() ; i++) {
            Paint p = new Paint();
            p.setColor(Color.YELLOW);
            c.drawRect(x, y, x+deltaX, y+deltaY, p);
            x+=deltaX;
        }
        for (int i = powerBar.getLoad(); i <AppConstant.MAX_NUM_OF_BARS ; i++) {
            Paint p = new Paint();
            p.setColor(Color.RED);
            c.drawRect(x, y, x+deltaX, y+deltaY, p);
            x+=deltaX;
        }






    }

    private void setBackground(Bitmap bitmap) {
    }

}
