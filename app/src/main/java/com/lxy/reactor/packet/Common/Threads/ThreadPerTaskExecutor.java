package com.lxy.reactor.packet.Common.Threads;

import java.util.concurrent.Executor;

/**
 * Created by 李昕怡 on 2019/9/28
 * 将线程放入到线程池中
 */

public class ThreadPerTaskExecutor implements Executor {
    private final SimpleThreadFactory threadFactory;

    public ThreadPerTaskExecutor(SimpleThreadFactory threadFactory){
        if(threadFactory == null){
            throw new NullPointerException("SimpleThreadFactory");
        }
        this.threadFactory = threadFactory;
    }

    @Override
    public void execute(Runnable command) {
        threadFactory.newThread(command);
    }
}
