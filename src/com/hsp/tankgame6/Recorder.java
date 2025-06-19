package com.hsp.tankgame6;

import java.io.*;
import java.util.Vector;

public class Recorder{
    private static int allEnemyTankNum = 0;//the number of enemy tanks killed
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\resources\\myRecord.txt";
    //to get vector which contains all existing enemy, define a property, and set it
    private static Vector<EnemyTank> enemyTanks = null;
    //define a node vector to store enemies' info
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks){
        Recorder.enemyTanks = enemyTanks;
    }

    //add a method to get record file
    public static String getRecordFile() {
        return recordFile;
    }

    //add a method to read record file
    public static Vector<Node> getNodesAndEnemyTankRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //loop to read to create nodes
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return nodes;
    }


    //when user exits game
    //save num to record file
    //save enemy's position and direction
    public static void keepRecord() throws IOException {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\r\n");
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.islive){
                    String record = enemyTank.getX()+" " +enemyTank.getY()+" "+enemyTank.getDirect();
                    bw.write(record+"\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //when we kill one enemy, then add one to allEnemyTankNum
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
}
