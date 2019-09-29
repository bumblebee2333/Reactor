package com.lxy.reactor.packet.Common.Events;

import com.lxy.reactor.packet.Common.Excutors.EventExecutor;
import com.lxy.reactor.packet.Common.Selectors.SelectorProvider;
import com.lxy.reactor.packet.Common.Threads.SimpleThreadFactory;
import com.lxy.reactor.packet.Common.Threads.ThreadFactory;

import java.util.concurrent.Executor;

/**
 * Created by 李昕怡 on 2019/9/28 15:30
 * 线程组：里面可以有多个NioEventLoop
 */

public class NioEventLoopGroup extends MultithreadEventLoopGroup{

    public NioEventLoopGroup(){
        this(0);
    }

    public NioEventLoopGroup(int nThreads){
        this(nThreads,(ThreadFactory) null);
    }

    public NioEventLoopGroup(int nThreads,ThreadFactory threadFactory){
        this(nThreads,threadFactory,SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads,ThreadFactory threadFactory,final SelectorProvider selectorProvider){
        super(nThreads, (SimpleThreadFactory) threadFactory,selectorProvider);
    }

    @Override
    protected EventExecutor newChild(Executor executor, Object... args) throws Exception {
        return new NioEventLoop(this,executor,(SelectorProvider) args[0]);
    }
}
