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
    private static final Duration MAX_TIME_IN_CACHE = Duration.ofSeconds(20);
    private final AtomicLong incrementInitialDelay = new AtomicLong(Duration.ofSeconds(5).toMillis());
    private Timer refreshTimer = new Timer();

    private Map<String, ScheduledExecutorService> balanceExecutors = new HashMap<>();
    private Map<String, ScheduledExecutorService> openOrderExecutors = new HashMap<>();
    private static final String DATABASE_NAME = "stoppedexchanges";
    private static final String DISABLED_CURRENCIES_DB = "disabled.currencies";
    private static final String ARBITRAGE_DB = "arbitrage";
    private List<StoppedExchanges> stoppedExchangesInfo = new ArrayList<>();

    private List<String> pairs;
    private Set<String> stoppedExchanges = new HashSet<>();
    private Set<String> exchangesWithStoppedOrderBook = new HashSet<>();
    private Map<String, StreamingExchange> streamingExchangeCache = new HashMap<>();
    private Map<String, StreamingExchange> streamingExchangeCacheOfTwoInstance = new HashMap<>();
    private Map<String, List<String>> disabledCurrencyPairMap = new HashMap<>();

    private Dictionary<CurrencyPair, String, Exchange> exchangeDictionary = new Dictionary<>();
    private Dictionary<String, Currency, Disposable> balanceSubscriptions = new Dictionary<>();
    private Dictionary<String, CurrencyPair, Disposable> orderSubscriptions = new Dictionary<>();
    private Dictionary<String, CurrencyPair, Disposable> orderBookSubscriptions = new Dictionary<>();

    private Map<String, Exchange> exchangeMap = new HashMap<>();
    private Map<String, Map<String, String>> javaConfig;

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
    private void init(...) {...}

    //TODO:
    public ExchangeManager(...) {...}

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

    public synchronized StreamingExchange getStreamingExchangeCacheOfTwoInstance(...) {...}

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
