package com.tssss.bysj.net.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Execute each HTTP request and
 * return the response through OnHttpDataListener.
 *
 * @author Tssss
 * @date 2019-01-07
 */
class HttpThreadPool {
    private static HttpThreadPool instance = new HttpThreadPool();

    private ThreadPoolExecutor mThreadPoolExecutor;
    private LinkedBlockingQueue<Runnable> mHttpTasks = new LinkedBlockingQueue<>();
    private Runnable mHttpTaskTransfer = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = mHttpTasks.take();
                    if (task != null) {
                        mThreadPoolExecutor.execute(task);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private HttpThreadPool() {
        mThreadPoolExecutor = new ThreadPoolExecutor(4,
                20, 20, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    /*
                    Re-execute the task that has not been executed within 20s
                     */
                    mHttpTasks.put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mThreadPoolExecutor.execute(mHttpTaskTransfer);
    }

    /**
     * Single Pattern
     *
     * @return instance of HttpThreadPool.
     */
    public static HttpThreadPool getInstance() {
        return instance;
    }

    /**
     * Add HTTP request task
     */
    public void addHttpTask(Runnable task) {
        try {
            mHttpTasks.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
