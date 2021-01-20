package liquid.service;

import ...;
import liquid.LiquidStreamingAdapters;
import liquid.dto.LiquidResponse;
import liquid.dto.LiquidStreamingOrderBook;

public class LiquidStreamingMarketDataService implements StreamingMarketDataService {
    private LiquidStreamingService streamingService;
    private final ObjectMapper mapper;
    
    public LiquidStreamingMarketDataService(LiquidStreamingService streamingService) {
        this.streamingService = streamingService;
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
        return streamingService
                .subscribeChannel("price_ladders_cash" + currencyPair.toString().replace("/", ""), currencyPair)
                .map(JsonNode::toString)
                .map(s -> mapper.readValue(s, LiquidResponse.class))
                .map(liquidResponse -> mapper.readValue(liquidResponse.getData(), LiquidStreamingOrderBook.class))
                .map(liquidStreamingOrderBook -> LiquidStreamingAdapters.toOrderBook(
                        liquidStreamingOrderBook,
                        currencyPair
                ));
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
        //...
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
        //...
    }
}
