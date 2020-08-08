package com.example.demo.proxy;

public class InterceptorChain {

    public Object pluginAll(ExecutorHandler target) {
        int proxyCount = target.getProxyCount();

        for (int i = 0; i < proxyCount; i++) {
            target = (ExecutorHandler) Plugin.wrap(target);
        }
        return target;
    }


}
