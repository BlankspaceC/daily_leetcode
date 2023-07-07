package com.example.study;

/**
 * @Author: LongX
 * @Date: 2023/7/7 14:27
 * @Description: Worker TODO
 * @Version: 1.0
 **/
public class Worker {

    public int leftToRight;

    public int pickOld;

    public int rightToLeft;

    public int pickNew;

    public int throwBridgeTime;
    public int index;

    //0 - 停止 1 - 左到右过桥 2 - 右到左过桥 3 - 搬旧  4 - 入新
    public int handleType;

    public int restTime;
    public Worker(int leftToRight, int pickOld, int rightToLeft, int pickNew, int throwBridgeTime, int index) {
        this.leftToRight = leftToRight;
        this.pickOld = pickOld;
        this.rightToLeft = rightToLeft;
        this.pickNew = pickNew;
        this.throwBridgeTime = throwBridgeTime;
        this.index = index;
    }
}
