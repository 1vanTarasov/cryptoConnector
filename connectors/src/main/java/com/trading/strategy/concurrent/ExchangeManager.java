package com.trading.strategy.concurrent;

import ...;
import com.trading.strategy.utils.ExchangeUtils;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class ExchangeManager {
    //...

    @Autowired
    ExchangeUtils exchangeUtils;

    @Autowired
    BalanceProvider balanceProvider;

    @Autowired
    ExecutorService executorService;

    @Autowired
    Environment env;

    @Autowired
    AppConfig arbitrageConfig;

    @Autowired
    OrderBookHandler orderBookHandler;

    @Autowired
    OpenOrderHandler openOrderHandler;

    @Autowired
    BalanceHandler balanceHandler;

    @Autowired
    OpenOrderManager openOrderManager;

    @Autowired
    AppConfig appConfig;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CancelOrderHandler cancelOrderHandler;

    @Autowired
    private PlaceOrderHandler placeOrderHandler;

    @Autowired
    EventsHandler eventsHandler;

    @Autowired
    OpenOrderService openOrderService;

    @Value("${trading.exchanges.ws.multiple.instance}")
    private List<String> exchangesWsMultipleInstance;

    @PostConstruct
    private void init() {
        //...
    }

    public ExchangeManager(Map<String, Map<String, String>> config){
        //...
    }

    //...

    public StreamingExchange getStreamingExchange(String exchangeName) {
        try {
            checkIsInclude(exchangeName);
            Map<String, String> exchangeConfig = javaConfig.get(exchangeName);
            StreamingExchange exchange = (StreamingExchange) ExchangeUtils.createExchangeWithCache(exchangeName, exchangeConfig, true);
            streamingExchangeCache.put(exchangeName, exchange);
            return exchange;
        } catch (Throwable e) {
            log.error("Failed : getStreamingExchange(), exchange: {}  with message {} ", exchangeName, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public synchronized Exchange getExchange(String exchangeName) {
        Map<String, String> config = javaConfig.get(exchangeName);
        Assert.isTrue(config != null, "Cannot get configuration for exchange " + exchangeName);
        boolean isStreaming = isStreamingExchange(config);
        return ExchangeUtils.createExchangeWithCache(exchangeName, config, isStreaming);
    }

    //...

    public Exchange getExchange(CurrencyPair currencyPair, String exchangeName) {
        try {
            Map<String, String> exchangeConfig = javaConfig.get(exchangeName);
            Exchange exchangeObj = ExchangeUtils.createExchangeWithCache(exchangeName, exchangeConfig, false);
            ExchangeMetaData exMetaData = exchangeObj.getExchangeMetaData();
            Map<CurrencyPair, CurrencyPairMetaData> currPairs = exMetaData.getCurrencyPairs();
            if (currPairs.containsKey(currencyPair)) {
                ExchangeMetaData exchangeMetaData = exchangeObj.getExchangeMetaData();
                Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
                if (currencyPairs.containsKey(currencyPair)) {
                    return exchangeObj;
                }
            }
        } catch (Throwable e) {
            log.error("Failed, CurrencyPair:{} , exchange: {}  with message {} ", currencyPair, exchangeName, e.getMessage());
        }
        return null;
    }

   //...
}
