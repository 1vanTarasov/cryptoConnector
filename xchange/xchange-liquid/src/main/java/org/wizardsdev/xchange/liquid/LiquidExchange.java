package org.wizardsdev.xchange.liquid;

import ...;

import org.wizardsdev.xchange.liquid.service.LiquidAccountService;
import org.wizardsdev.xchange.liquid.service.LiquidMarketDataService;

import java.time.Instant;

public class LiquidExchange extends BaseExchange implements Exchange {

    @Override
    protected void initServices() {
        this.marketDataService = new LiquidMarketDataService(this);
        this.accountService = new LiquidAccountService(this);
        this.tradeService = new LiquidTradeService(this);
        
        LiquidAdapters.initCurrencyMap(this);
    }

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

    //...
}
