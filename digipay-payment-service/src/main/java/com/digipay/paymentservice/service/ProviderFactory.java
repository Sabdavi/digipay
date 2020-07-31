package com.digipay.paymentservice.service;

public class ProviderFactory {
    private static String SAMAN_PROVIDER_ID = "6037";
    private String source;

    public ProviderFactory(String source) {
        this.source = source;
    }
    public Provider  createProvider(){
        Provider provider = source.equals(SAMAN_PROVIDER_ID) ?
                new SamanProvider() : new MellatProvider();
        return provider;
    }
}