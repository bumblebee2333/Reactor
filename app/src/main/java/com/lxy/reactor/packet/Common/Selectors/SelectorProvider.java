package com.lxy.reactor.packet.Common.Selectors;

import java.io.IOException;
import java.nio.channels.Selector;

public class SelectorProvider {
    private static final Object lock = new Object();
    private static SelectorProvider provider = null;

    public SelectorProvider(){

    }

    public static SelectorProvider provider(){
        synchronized (lock){
            if (provider == null)
                return new SelectorProvider();
        }
        return provider;
    }

    public Selector openSelector(){
        Selector selector = null;
        try {
            selector = Selector.open();
        }catch (IOException e){
            e.printStackTrace();
        }
        return selector;
    }
}
