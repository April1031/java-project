package com.hsp.tankgame5;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

public class TankGame05 extends JFrame {
    MyPanel mp = null;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        TankGame05 tankGame05 = new TankGame05();


    }

    public TankGame05() {
        System.out.println("请输入1（新游戏）或2（继续上局）");
        String key = sc.next();
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1300,950);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //add a method to handle window closing operations
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });
    }


}
