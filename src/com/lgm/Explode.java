package com.lgm;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author:李罡毛
 * @date:2021/2/5 13:18
 */
public class Explode implements Serializable {
    @Serial
    private static final long serialVersionUID = -5927261928218784540L;
    private int x;
    private int y;
    private TankFrame tankFrame;//获取坦克窗口的私有属性
    private static int WIDTH,HEIGHT;//帧尺寸
    private int f;//第几帧

    public Explode() {
    }

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g){
        g.drawImage(ResourceMgr.explodeImages[f++],x,y,null);
        if (f>=ResourceMgr.explodeImages.length){
            this.tankFrame.getExplodeList().remove(this);
        }
    }
}
