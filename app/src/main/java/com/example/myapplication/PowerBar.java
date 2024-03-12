package com.example.myapplication;

import android.os.SystemClock;

public class PowerBar {

    private int load = 0;
    private boolean gameOnGoing = true;

    private int image;

    public PowerBar() {
        image = 0;
    }

    public PowerBar(int image) {
        this.image = image;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public boolean isGameOnGoing() {
        return gameOnGoing;
    }


    public void startLoading() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                    while(gameOnGoing) {
                        SystemClock.sleep(AppConstant.TIME_TO_SLEEP);
                        if(load < AppConstant.MAX_NUM_OF_BARS)
                            load++;
                        // todo have changed
                        if(load <= 0){
                            load = 1;
                        }
                    }
                }

            }).start();
    }

    public void usedPower(int power)
    {
        load = load - power;
    }


    public void setGameOnGoing(boolean gameOnGoing) {
        this.gameOnGoing = false;
    }

    public int getLoad() {
        return load;
    }

}
