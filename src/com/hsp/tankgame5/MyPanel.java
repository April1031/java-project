package com.hsp.tankgame5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable{
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector();
    //define a vector to restore enemies in last game
    Vector<Node> nodes = new Vector();
    int enemyTankSize = 3;

    //define a vector to store bomb
    Vector<Bomb> bombs = new Vector();
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    //define a vector to restore enemies in last game


    public MyPanel(String key) {
        //determine if the record txt exists
        File file = new File(Recorder.getRecordFile());
        if(file.exists()){
            nodes = Recorder.getNodesAndEnemyTankRec();
        }else{
            System.out.println("文件不存在");
            key = "1";
        }

        hero = new Hero(500,100);//Initialize my tank

        switch (key){
            case "1":
                Recorder.setAllEnemyTankNum(0); // reset the enemy num
                enemyTanks.clear(); // clear record vector
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100*(i+1),0);
                    //set enemy tank to the vector which avoids touching
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(2);
                    //start enemy thread
                    new Thread(enemyTank).start();
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }//Initialize enemy tank and their bullets
                break;
            case "2":
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = (Node) nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //set enemy tank to the vector which avoids touching
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirect());
                    //start enemy thread
                    new Thread(enemyTank).start();
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }//Initialize enemy tank and their bullets

            default:
                System.out.println("你的输入有误");

        }

        //put vector to record
        Recorder.setEnemyTanks(enemyTanks);
        //Initialize image
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/resources/bomb1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/resources/bomb2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/resources/bomb3.gif"));

        //play the music
        new AePlayWave("src\\tankgamemusic.wav").start();
    }

    //show info about enemy tanks we killed
    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克",1020,30);
        drawTank(1020,60,g,0,0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum()+"",1080,100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        showInfo(g);
        //draw my tank
        if(hero!=null&&hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        //draw my bullet
        for(int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if(shot != null && shot.isLive == true){
                g.draw3DRect(shot.x,shot.y,2,2,false);
            }else{//if this bullet is dead, then remove it
                hero.shots.remove(shot);
            }
        }
        //if bombs has bomb, draw explosion
        for (int i = 0; i < bombs.size(); i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bomb bomb = bombs.get(i);
            if(bomb.life>6){
                g.drawImage(image1, bomb.x,bomb.y,60,60,this);
            }else if(bomb.life>3){
                g.drawImage(image2, bomb.x,bomb.y,60,60,this);
            }else{
                g.drawImage(image3, bomb.x,bomb.y,60,60,this);
            }
            bomb.lifeDown();
            if(bomb.life ==0){
                bombs.remove(bomb);
            }

        }

        //draw enemy tank
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if(enemyTank.islive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //draw enemy's bullets
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    } else {
                        enemyTank.shots.remove(j);
                    }
                }
            }
        }


    }

    public void drawTank(int x, int y, Graphics g, int direct, int type ){
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }

        switch (direct){
            case 0://up
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1://right
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2://down
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3://left
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;

            default:
                System.out.println("暂时没有处理");

        }
    }

    //determine if any bullet hit enemy's tank
    public void hitEnemyTank(){
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if(shot.isLive && shot != null) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    //determine if enemy hit my tank
    public void hitHero(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if(hero.isLive && shot.isLive){
                    hitTank(shot,hero);
                }

            }
        }
    }

    //determine whether to hit tank
    public void hitTank(Shot s, Tank tank){
        switch (tank.getDirect()){
            case 0://up
            case 2://down
                if(s.x > tank.getX() && s.x < tank.getX()+40 && s.y > tank.getY() && s.y <tank.getY()+60){
                    s.isLive=false;
                    tank.isLive= false;
                    //when hit one enemy's tank, remove it from vector
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(tank);
                    //when one enemy tank is killed, then add one to allEnemyTankNum
                    //determine if the tank killed is enemy
                    if(tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                }
                break;
            case 1://up
            case 3://down
                if(s.x > tank.getX() && s.x < tank.getX()+60 && s.y > tank.getY() && s.y <tank.getY()+40){
                    s.isLive=false;
                    tank.isLive= false;
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(tank);
                    if(tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(0);
            if(hero.getY()>0) {
                hero.moveUp();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirect(1);
            if(hero.getX()+60<1000) {
                hero.moveRight();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirect(2);
            if(hero.getY()+60<750) {
                hero.moveDown();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirect(3);
            if(hero.getX()>0) {
                hero.moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_J){

            hero.shotEnemyTank();
        }
        this.repaint();
    }



    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run(){
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //determine whether to hit enemy's tank
            hitEnemyTank();
            hitHero();
            this.repaint();
        }
    }

}



