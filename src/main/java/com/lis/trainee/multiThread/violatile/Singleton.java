package com.lis.trainee.multiThread.violatile;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

/**
 * ClassName Singleton
 *
 * @author Lis on 2021/12/4
 **/
public class Singleton {

    // private 保证只能自己初始化
    // volatile 保证可见性
    private volatile static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            // synchronized 锁住这个类，保证只初始化一次
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    // 懒加载方式

    static class SingletonHolder {
        static Singleton instance = new Singleton();
    }

    public static Singleton getInstance1() {
        return SingletonHolder.instance;
    }
}
