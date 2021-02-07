package com.lgm;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author:李罡毛
 * @date:2021/2/5 10:24
 */
public class Tank implements Serializable {

    @Serial
    private static final long serialVersionUID = 4647626261280633755L;
    private int x;
    private int y;
    private Dir dir;
    private int SPEED = 5;
    private Dir dirBeforeImmobile;
    private TankFrame tankFrame;
    private static int WIDTH,HEIGHT;//坦克高度宽度
    private boolean isLive = true;//坦克存活状态，初始化是true
    private Group group;//区分敌方或友方坦克
    private final Random random = new Random();//随机数，控制子弹发射
    private final List<Bullet> bulletList = new ArrayList<Bullet>();//弹夹

    public Tank() {
    }

    public Tank(int x, int y, Dir dir,TankFrame tankFrame,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
    }

    public Tank(int x, int y, Dir dir,TankFrame tankFrame,Group group,int speed) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
        this.SPEED = speed;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }
    public Group getGroup() {
        return group;
    }
    public int getWIDTH() {
        return WIDTH;
    }
    public int getHEIGHT() {
        return HEIGHT;
    }
    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }
    public Dir getDirBeforeImmobile() {
        return dirBeforeImmobile;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Dir getDir() {
        return dir;
    }
    public int getSPEED() {
        return SPEED;
    }
    public void setDir(Dir dir) {
        this.dir = dir;
    }
    public void setDirBeforeImmobile(Dir dirBeforeImmobile) {
        this.dirBeforeImmobile = dirBeforeImmobile;
    }

    public void paint(Graphics g) {
        //如果是敌方，绘制前判断一下存活状态
        if (!isLive && this.getGroup() == Group.BAD){
            Explode explode = new Explode(x,y,tankFrame);
            tankFrame.getExplodeList().add(explode);
            tankFrame.getTanks().remove(this);
            return;
        }
      //静止前方向如果是null,初始化赋予向右的方向
        if (dirBeforeImmobile==null && this.group == Group.GOOD){
            dirBeforeImmobile = Dir.RIGHT;
            WIDTH = ResourceMgr.tankR.getWidth();
            HEIGHT = ResourceMgr.tankR.getHeight();
            g.drawImage(ResourceMgr.tankR,x,y,null);
            return;
        }else if (dirBeforeImmobile == null && this.group ==Group.BAD){
            dirBeforeImmobile = Dir.DOWN;
            WIDTH = ResourceMgr.tankD.getWidth();
            HEIGHT = ResourceMgr.tankD.getHeight();
            g.drawImage(ResourceMgr.tankD,x,y,null);
            return;
        }
        //绘制子弹
        for (int i = 0; i < this.bulletList.size(); i++) {
            if (this.bulletList.get(i)!=null){
                this.bulletList.get(i).paint(g);
            }
        }
        //嵌套循环，子弹与敌方坦克的碰撞验证
        for (int i = 0; i < this.bulletList.size(); i++) {
            for (int j = 0; j < tankFrame.getTanks().size(); j++) {
                this.bulletList.get(i).collideWith(tankFrame.getTanks().get(j));
            }
        }
        switch (dirBeforeImmobile){
            case LEFT:
                WIDTH = ResourceMgr.tankL.getWidth();
                HEIGHT = ResourceMgr.tankL.getHeight();
                g.drawImage(ResourceMgr.tankL,x,y,null);
                break;
            case RIGHT:
                WIDTH = ResourceMgr.tankR.getWidth();
                HEIGHT = ResourceMgr.tankR.getHeight();
                g.drawImage(ResourceMgr.tankR,x,y,null);
                break;
            case UP:
                WIDTH = ResourceMgr.tankU.getWidth();
                HEIGHT = ResourceMgr.tankU.getHeight();
                g.drawImage(ResourceMgr.tankU,x,y,null);
                break;
            case DOWN:
                WIDTH = ResourceMgr.tankD.getWidth();
                HEIGHT = ResourceMgr.tankD.getHeight();
                g.drawImage(ResourceMgr.tankD,x,y,null);
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
        //敌方坦克随机发射子弹
        if (this.getGroup() == Group.BAD){
            if (random.nextInt(20)>18) {
                this.fire();
            }
        }
    }
    public void fire() {
        Bullet bullet = new Bullet(this.x,this.y,this.dirBeforeImmobile,this.tankFrame,this);
        bulletList.add(bullet);
    }
}
