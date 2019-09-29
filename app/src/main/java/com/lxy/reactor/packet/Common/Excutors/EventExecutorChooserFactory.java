package com.lxy.reactor.packet.Common.Excutors;

/**
 * Created by 李昕怡 on 2019/9/29
 * 这里主要是为在线程池中选择下一个工作的线程的接口
 */
public interface EventExecutorChooserFactory {
    EventExecutorChooser newChooser(EventExecutor[] excutors);

    interface EventExecutorChooser{
        EventExecutor next();
    }
}
