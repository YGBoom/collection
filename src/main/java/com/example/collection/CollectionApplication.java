package com.example.collection;

import com.example.collection.controller.ThreadTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionApplication.class, args);
        ThreadTest threadSynOne = new ThreadTest();
        Thread thread1 = new Thread(threadSynOne, "ThreadSynOne");
        Thread thread2 = new Thread(threadSynOne, "ThreadSynTwo");
        thread1.start();
        thread2.start();
    }
}
