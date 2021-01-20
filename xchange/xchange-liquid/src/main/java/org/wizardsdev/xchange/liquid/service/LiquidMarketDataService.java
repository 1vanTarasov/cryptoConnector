package org.wizardsdev.xchange.liquid.service;

import ...;
import org.wizardsdev.xchange.liquid.LiquidAdapters;
import org.wizardsdev.xchange.liquid.dto.LiquidException;

import java.io.IOException;

public class LiquidMarketDataService extends LiquidMarketDataServiceRaw implements MarketDataService {

    public LiquidMarketDataService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
        try {
            return LiquidAdapters.toOrderBook(getLiquidOrderBook(LiquidAdapters.toLiquidCurrencyPair(currencyPair)), currencyPair);
        } catch (LiquidException e) {
            throw LiquidErrorAdapter.adapt(e);
        }
    }
}
