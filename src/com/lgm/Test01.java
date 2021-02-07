package com.lgm;

/**
 * @author:李罡毛
 * @date:2021/2/4 13:13
 * new 一个Frame窗口
 */
public class Test01 {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        //初始化5个敌方坦克
        for (int i = 0; i < 5; i++) {
            tankFrame.getTanks().add(new Tank(300+i*50,10,Dir.DOWN,tankFrame,Group.BAD));
        }

        while (true){
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
