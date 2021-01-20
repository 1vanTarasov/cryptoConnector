package org.wizardsdev.xchange.liquid.service;

import ...;
import org.wizardsdev.xchange.liquid.Liquid;

class LiquidBaseService<T extends Liquid> extends BaseExchangeService<Exchange> implements BaseService {
    final ParamsDigest digest;
    final T restProxy;

    LiquidBaseService(Class<T> typeClass, Exchange exchange) {
        super(exchange);
        restProxy = RestProxyFactory.createProxy(
                typeClass,
                exchange.getExchangeSpecification().getSslUri(),
                getClientConfig());

        digest = LiquidDigest.createInstance(
                exchange.getExchangeSpecification().getApiKey(),
                exchange.getExchangeSpecification().getSecretKey()
        );
    }
}
