package com.hsp.tankgame5;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();
    Vector<EnemyTank> enemyTanks = new Vector<>();//this vector can get all enemy tanks
    boolean islive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //provide a method to get all enemy tanks for this vector
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks){
        this.enemyTanks = enemyTanks;
    }

    //determine if enemy tanks overlap each other
    public boolean isTouchEnemyTank(){
        switch(this.getDirect()){
            case 0:
                //compare this tank with other tank
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        //when other tank towards up or down
                        //enemy's position's range:X,X+40,Y,Y+60
                        //this tank's position:
                        //top left:x,y
                        //top right: x+40,y
                        if(enemyTank.getDirect()==0 || enemyTank.getDirect()==2){
                            if(this.getX()>= enemyTank.getX()
                                    &&this.getX()<= enemyTank.getX()+40
                                    &&this.getY()>= enemyTank.getY()
                                    && this.getY()<=enemyTank.getY()+60){
                                return true;
                            }
                            if(this.getX()+40>= enemyTank.getX()
                                    &&this.getX()+40<= enemyTank.getX()+40
                                    &&this.getY()>= enemyTank.getY()
                                    && this.getY()<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //when other tank towards left or right
                        //enemy's position's range:X,X+60,Y,Y+40
                        if(enemyTank.getDirect()==1 || enemyTank.getDirect()==3){
                            if(this.getX()>= enemyTank.getX()
                                    &&this.getX()<= enemyTank.getX()+60
                                    &&this.getY()>= enemyTank.getY()
                                    && this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                            if(this.getX()+40>= enemyTank.getX()
                                    &&this.getX()+40<= enemyTank.getX()+60
                                    &&this.getY()>= enemyTank.getY()
                                    && this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://towards right
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        //when other tank towards up or down
                        //enemy's position's range:X,X+40,Y,Y+60
                        //this tank's position:
                        //top right:x+60,y
                        if(enemyTank.getDirect()==0 || enemyTank.getDirect()==2){
                            if(this.getX()+60>= enemyTank.getX()
                                    &&this.getX()+60<= enemyTank.getX()+40
                                    &&this.getY()>= enemyTank.getY()
                                    && this.getY()<=enemyTank.getY()+60){
                                return true;
                            }
                            //bottom right: x+60,y+40
                            if(this.getX()+60>= enemyTank.getX()
                                    &&this.getX()+60<= enemyTank.getX()+40
                                    &&this.getY()+40>= enemyTank.getY()
                                    && this.getY()+40<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //when other tank towards left or right
                        //enemy's position's range:X,X+60,Y,Y+40
                        if(enemyTank.getDirect()==1 || enemyTank.getDirect()==3){
                            //top right:x+60,y
                            if(this.getX()+60>= enemyTank.getX()
                                    &&this.getX()+60<= enemyTank.getX()+60
                                    &&this.getY()>= enemyTank.getY()
                                    && this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                            //bottom right: x+60,y+40
                            if(this.getX()+60>= enemyTank.getX()
                                    &&this.getX()+60<= enemyTank.getX()+60
                                    &&this.getY()+40>= enemyTank.getY()
                                    && this.getY()+40<=enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://when this tank towards down
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        //when other tank towards up or down
                        //enemy's position's range:X,X+40,Y,Y+60
                        //this tank's position:
                        if(enemyTank.getDirect()==0 || enemyTank.getDirect()==2){
                            //bottom left:x,y+60
                            if(this.getX()>= enemyTank.getX()
                                    &&this.getX()<= enemyTank.getX()+40
                                    &&this.getY()+60>= enemyTank.getY()
                                    && this.getY()+60<=enemyTank.getY()+60){
                                return true;
                            }
                            //bottom right: x+40,y+60
                            if(this.getX()+40>= enemyTank.getX()
                                    &&this.getX()+40<= enemyTank.getX()+40
                                    &&this.getY()+60>= enemyTank.getY()
                                    && this.getY()+60<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //when other tank towards left or right
                        //enemy's position's range:X,X+60,Y,Y+40
                        if(enemyTank.getDirect()==1 || enemyTank.getDirect()==3){
                            //bottom left:x,y+60
                            if(this.getX()>= enemyTank.getX()
                                    &&this.getX()<= enemyTank.getX()+60
                                    &&this.getY()+60>= enemyTank.getY()
                                    && this.getY()+60<=enemyTank.getY()+40){
                                return true;
                            }
                            //bottom right: x+40,y+60
                            if(this.getX()+40>= enemyTank.getX()
                                    &&this.getX()+40<= enemyTank.getX()+60
                                    &&this.getY()+60>= enemyTank.getY()
                                    && this.getY()+60<=enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://when this tank towards left
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        //when other tank towards up or down
                        //enemy's position's range:X,X+40,Y,Y+60
                        //this tank's position:
                        if(enemyTank.getDirect()==0 || enemyTank.getDirect()==2){
                            //top left:x,y
                            if(this.getX()>= enemyTank.getX()
                                    &&this.getX()<= enemyTank.getX()+40
                                    &&this.getY()>= enemyTank.getY()
                                    && this.getY()<=enemyTank.getY()+60){
                                return true;
                            }
                            //bottom left: x,y+40
                            if(this.getX()>= enemyTank.getX()
                                    &&this.getX()<= enemyTank.getX()+40
                                    &&this.getY()+40>= enemyTank.getY()
                                    && this.getY()+40<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //when other tank towards left or right
                        //enemy's position's range:X,X+60,Y,Y+40
                        if(enemyTank.getDirect()==1 || enemyTank.getDirect()==3){
                            //top left:x,y
                            if(this.getX()>= enemyTank.getX()
                                    &&this.getX()<= enemyTank.getX()+60
                                    &&this.getY()>= enemyTank.getY()
                                    && this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                            //bottom left: x,y+40
                            if(this.getX()>= enemyTank.getX()
                                    &&this.getX()<= enemyTank.getX()+60
                                    &&this.getY()+40>= enemyTank.getY()
                                    && this.getY()+40<=enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;

        }
        return false;
    }

    @Override
    public void run() {
        while(true){
            //make enemy's tank can have another shot
            if(islive && shots.size() < 1){
                Shot s = null;
                switch(getDirect()){
                    case 0:
                        s = new Shot(getX()+20,getY(),0);
                        break;
                    case 1:
                        s= new Shot(getX()+60,getY()+20,1);
                        break;
                    case 2:
                        s = new Shot(getX()+20,getY()+60,2);
                        break;
                    case 3:
                        s = new Shot(getX(),getY()+20,3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }

            switch (getDirect() ){
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if(getY()>0&&!isTouchEnemyTank()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 1:
                    for (int i = 0; i < 30; i++) {
                        if(getX()+60<1000&&!isTouchEnemyTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if(getY()+60<750&&!isTouchEnemyTank()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if(getX()>0&&!isTouchEnemyTank()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            //randomly change enemy's direction
            setDirect((int)(Math.random()*4));
            if(!islive){
                break;
            }
        }
    }
}
