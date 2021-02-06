package com.lgm;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author:李罡毛
 * @date:2021/2/5 13:18
 */
public class Bullet implements Serializable {
    @Serial
    private static final long serialVersionUID = 8918545667283636286L;
    private int x;
    private int y;
    private Dir dir;
    private final int SPEED = 20;
    private boolean isLive = true;//子弹撞车或者跃出窗口就移除，等待回收，否则子弹变多后占用内存导致内存溢出
    private TankFrame tankFrame;//获取坦克窗口的私有属性
    private int fireX,fireY;//子弹射出时候的坐标，通过坦克坐标、图片长宽、运行方向 获取
    private static int WIDTH,HEIGHT;//子弹的宽度，高度

    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir,TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g){
        if (!isLive){
            this.tankFrame.getBullets().remove(this);
            return;
        }

        switch (dir){
            case LEFT:
                WIDTH = ResourceMgr.bulletL.getWidth();
                HEIGHT = ResourceMgr.bulletL.getHeight();
                fireX = x;
                fireY = y-HEIGHT/2+ResourceMgr.tankL.getHeight()/2;
                g.drawImage(ResourceMgr.bulletL,fireX,fireY,null);
                break;
            case RIGHT:
                WIDTH = ResourceMgr.bulletR.getWidth();
                HEIGHT = ResourceMgr.bulletR.getHeight();
                fireX = x+ResourceMgr.tankR.getWidth()-WIDTH;
                fireY = y-HEIGHT/2+ResourceMgr.tankR.getHeight()/2;
                g.drawImage(ResourceMgr.bulletR,fireX,fireY,null);
                break;
            case UP:
                WIDTH = ResourceMgr.bulletU.getWidth();
                HEIGHT = ResourceMgr.bulletU.getHeight();
                fireX = x+ResourceMgr.tankU.getWidth()/2-WIDTH/2;
                fireY = y;
                g.drawImage(ResourceMgr.bulletU,fireX,fireY,null);
                break;
            case DOWN:
                WIDTH = ResourceMgr.bulletD.getWidth();
                HEIGHT = ResourceMgr.bulletD.getHeight();
                fireX = x+ResourceMgr.tankD.getWidth()/2-WIDTH/2;
                fireY = y+ResourceMgr.tankD.getHeight()-HEIGHT;
                g.drawImage(ResourceMgr.bulletD,fireX,fireY,null);
                break;
        }
        this.move();
    }
    private void move(){
        if (dir == Dir.LEFT){
            x -= SPEED;
        }else if (dir == Dir.RIGHT){
            x += SPEED;
        }else if (dir == Dir.UP){
            y -= SPEED;
        }else if (dir == Dir.DOWN){
            y += SPEED;
        }
        if (x<0||y<0||x>tankFrame.getWidth()||y> tankFrame.getHeight()){
            this.isLive = false;
        }
    }
    //子弹与坦克碰撞
    public void collideWith(Tank tank) {
        Rectangle bulletRectangle = new Rectangle(x,y,WIDTH,HEIGHT);
        Rectangle enemyTankRectange = new Rectangle(tank.getX(),tank.getY(),tank.getWIDTH(),tank.getHEIGHT());
        if (bulletRectangle.intersects(enemyTankRectange)) {
            this.isLive = false;
            tank.setIsLive(false);
        }
    }

}
