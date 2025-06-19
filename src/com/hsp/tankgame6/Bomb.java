package com.hsp.tankgame6;

public class Bomb {
    int x,y;
    int life = 9;//life cycle
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //reduce life
    public void lifeDown(){
        if(life>0){
            life--;
        }else{
            isLive = false;
        }


    }
}
