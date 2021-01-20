package org.wizardsdev.xchange.liquid.service;

import ...;
import org.wizardsdev.xchange.liquid.Liquid;
import org.wizardsdev.xchange.liquid.dto.marketdata.LiquidOrderBook;
import org.wizardsdev.xchange.liquid.dto.marketdata.LiquidProduct;

import java.io.IOException;
import java.util.List;

// Getting raw public data
public class LiquidMarketDataServiceRaw extends LiquidBaseService<Liquid> {

    public LiquidMarketDataServiceRaw(Exchange exchange) {
        super(Liquid.class, exchange);
    }

    public List<LiquidProduct> getProductsRaw() throws IOException {
        return restProxy.getCurrencyPairData();
    }

    LiquidOrderBook getLiquidOrderBook(String currency) throws IOException {
        return restProxy.getOrderBook(currency);
    }
}
