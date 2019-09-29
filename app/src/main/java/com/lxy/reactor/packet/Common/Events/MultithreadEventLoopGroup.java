package com.lxy.reactor.packet.Common.Events;

import com.lxy.reactor.packet.Common.Excutors.DefaultEventExecutor;
import com.lxy.reactor.packet.Common.Excutors.EventExecutor;
import com.lxy.reactor.packet.Common.Excutors.EventExecutorChooserFactory;
import com.lxy.reactor.packet.Common.Threads.SimpleThreadFactory;
import com.lxy.reactor.packet.Common.Threads.ThreadPerTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author :lixinyi
 * 该类中初始化线程池中线程的数量且实例化线程池
 */

public abstract class MultithreadEventLoopGroup {

    private static final int DEFAULT_EVENT_LOOP_THREADS;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private final AtomicInteger mAtomic = new AtomicInteger();
    private EventExecutor[] children;//存放线程的数组 这些线程也在线程池中

    static {
        DEFAULT_EVENT_LOOP_THREADS = Math.max(1,CPU_COUNT * 2);
    }

    public MultithreadEventLoopGroup(int nThreads, SimpleThreadFactory threadFactory,Object... args){
        this(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads,
                threadFactory == null ? null:new ThreadPerTaskExecutor(threadFactory),
                args);
    }

    public MultithreadEventLoopGroup(int nThread, Executor executor,Object... args){
        this(nThread,executor, DefaultEventExecutor.INSTANCE,args);
    }

    public MultithreadEventLoopGroup(int nThreads, Executor executor,
                                     EventExecutorChooserFactory chooserFactory,
                                     Object... args){
        if(nThreads <= 0){
            throw new IllegalArgumentException(String.format("nThread:%d should > 0",nThreads));
        }

        if(executor == null){
            throw new NullPointerException("executor is null");
        }

        children = new EventExecutor[nThreads];
        for(int i=0;i<children.length;i++){
            boolean success = false;
            try {
                children[i] = newChild(executor,args);
                success = true;
            } catch (Exception e) {
                throw  new IllegalStateException("failed to create a child event loop",e);
            }finally {
                if (!success){
                    for(int j=0;j<i;j++){
                        children[j].interrupt();//中断这个线程
                    }
                }
            }
        }
    }

    private void incrementInteger(){
        mAtomic.incrementAndGet();//mAtomic++;
    }

    protected abstract EventExecutor newChild(Executor executor, Object... args) throws Exception;
}
