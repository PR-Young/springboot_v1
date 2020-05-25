package com.system.springbootv1.utils;

import java.util.concurrent.*;

/**
 * @description:
 * @author: yy 2020/05/08
 **/
public class ThreadPoolUtils {
    public static ExecutorService cacheThreadPool = Executors.newCachedThreadPool();

    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    public static ExecutorService executorService = new ThreadPoolExecutor(5,
            10,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(512),
            new ThreadPoolExecutor.DiscardPolicy());
}
