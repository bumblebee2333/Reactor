package com.lxy.reactor.packet.Common.Threads;

/**
 * @athour lixinyi
 * @date 2019/9/28
 * 创建开启一个线程的接口
 */

public interface ThreadFactory {

    Thread newThread(Runnable r);
}
