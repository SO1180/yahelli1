package com.example.myapplication;

public class WalkingCharacter extends Shape {

    protected int speed;
    protected int radius;

    public WalkingCharacter() {
        this.speed = AppConstant.SPEEDX;
    }

    public void advance()
    {
        this.x +=speed;
    }

    public void changeDirection()
    {
        speed*=-1;
    }

}
