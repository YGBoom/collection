package com.example.collection.controller;

import java.lang.Thread;
public class ThreadTest implements Runnable{

    private volatile static int count = 0;
    @Override
    public void run() {
        synchronized (this){
            for(int i = 0; i<5;i++){
                try {
                    System.out.println(Thread.currentThread().getName()+ ":" + (count++));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
