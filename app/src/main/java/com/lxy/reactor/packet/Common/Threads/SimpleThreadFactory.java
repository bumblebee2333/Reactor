package com.lxy.reactor.packet.Common.Threads;
/**
 * @athour:lixinyi
 * @date:2019/9/28
 */


public class SimpleThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
