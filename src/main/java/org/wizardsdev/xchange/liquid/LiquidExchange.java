package org.wizardsdev.xchange.liquid;

import ...;

import org.wizardsdev.xchange.liquid.service.LiquidAccountService;
import org.wizardsdev.xchange.liquid.service.LiquidMarketDataService;

import java.time.Instant;

public class LiquidExchange extends BaseExchange implements Exchange {

    //Initial services for work with exchange
    @Override
    protected void initServices() {
        this.marketDataService = new LiquidMarketDataService(this);
        this.accountService = new LiquidAccountService(this);
        this.tradeService = new LiquidTradeService(this);
        
        LiquidAdapters.initCurrencyMap(this);
    }
    
    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return () -> Instant.now().getEpochSecond() * 1000;
    }

    //create default specification for connection to exchange
    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
        spec.setSslUri("https://api.liquid.com/");
        spec.setHost("www.liquid.com");
        spec.setPort(80);
        spec.setExchangeName("Liquid");
        spec.setExchangeDescription("Liquid Exchange.");
        return spec;
    }

    //initial meta data which we need for trading
    @Override
    public void remoteInit() throws ExchangeException {
        exchangeMetaData = LiquidAdapters.toExchangeMetaData(LiquidAdapters.getProductsRaw(), getExchangeMetaData());
    }
}
