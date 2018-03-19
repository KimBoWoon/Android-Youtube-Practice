package com.dabang.byul.byuldabang.http;

public class ProviderImplement implements ProviderInterface {
    @Override
    public ServiceInterface newService() {
        return new HttpService();
    }
}
