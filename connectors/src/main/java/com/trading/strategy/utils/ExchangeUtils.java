package com.trading.strategy.utils;

import ...;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.*;

public class ExchangeUtils {
    static Map<String, Exchange> exchangeMap = Collections.synchronizedMap(new HashMap<>());
    
    @Autowired
    ExchangeManager exchangeManager;
    
    public static void clearCache() {
        exchangeMap.clear();
    }
    
    public static void remove(String exchangeName) {
        exchangeMap.remove(exchangeName);
    }
    
    public static Exchange createExchangeWithCache(String exchangeName, Map<String, String> config, boolean streaming) {
        if (exchangeMap.containsKey(exchangeName)) {
            return exchangeMap.get(exchangeName);
        }
        Exchange exchange = createExchange(exchangeName, config, streaming);
        exchangeMap.put(exchangeName, exchange);
        return exchange;
    }
    
    public static Exchange createExchange(String exchangeName, Map<String, String> config, boolean streaming) {
        Map<String, Object> exchangeConfig = new HashMap<>(config);
        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(exchangeConfig.get("class").toString());
        //...set credentials
        exchangeSpecification.setExchangeSpecificParameters(exchangeConfig);
        Exchange exchange;
        if (streaming) {
            exchange = StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
        } else {
            exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
        }
        return exchange;
    }
    
        //...
}
