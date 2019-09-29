package com.lxy.reactor.packet.Common.Events;

import com.lxy.reactor.packet.Common.Selectors.SelectorProvider;

import java.nio.channels.Selector;
import java.util.concurrent.Executor;

public class NioEventLoop {
    private SelectorProvider provider;
    private Selector mSelector;

    public NioEventLoop(NioEventLoopGroup parent, Executor executor, SelectorProvider selectorProvider){
        if(selectorProvider == null){
            throw new NullPointerException("selectorProvider is null");
        }

        this.provider = selectorProvider;
        mSelector = selectorProvider.openSelector();
    }

    public SelectorProvider selectorProvider(){
        return provider;
    }
}
