package com.lxy.reactor.packet.Common.Excutors;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 李昕怡 on 2019/9/29
 * 这里主要是为在线程池中选择下一个工作的线程的接口
 */

public class DefaultEventExecutor implements EventExecutorChooserFactory {

    public static final DefaultEventExecutor INSTANCE = new DefaultEventExecutor();

    private DefaultEventExecutor(){}

    @Override
    public EventExecutorChooser newChooser(EventExecutor[] executors) {
        if(isPowerOfTwo(executors.length)){
            return new EvenLengthChooser(executors);
        }else {
            return new OddLengthChooser(executors);
        }
    }

    //2的几次幂
    //算出的结果 val为奇数结果都为1 val为偶数结果为2的n次方
    private static boolean isPowerOfTwo(int val){
        return (val & -val) == val;
    }

    private static final class EvenLengthChooser implements EventExecutorChooser{
        private final AtomicInteger idx = new AtomicInteger();
        private final EventExecutor[] executors;

        private EvenLengthChooser(EventExecutor[] executors) {
            this.executors = executors;
        }

        @Override
        public EventExecutor next() {
            return executors[idx.getAndIncrement() & executors.length-1];
        }
    }

    private static final class OddLengthChooser implements EventExecutorChooser{
        private final AtomicInteger idx = new AtomicInteger();
        private final EventExecutor[] executors;

        private OddLengthChooser(EventExecutor[] executors){
            this.executors = executors;
        }

        @Override
        public EventExecutor next() {
            return executors[Math.abs(idx.getAndIncrement() % executors.length)];
        }
    }
}
