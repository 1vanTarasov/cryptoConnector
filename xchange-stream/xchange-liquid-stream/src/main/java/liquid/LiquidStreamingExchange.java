package liquid;

import ...;
import liquid.service.LiquidStreamingMarketDataService;
import liquid.service.LiquidStreamingService;

public class LiquidStreamingExchange extends LiquidExchange implements StreamingExchange {
    private static final String API_BASE_URI = "wss://tap.liquid.com/app/LiquidTapClient";
    private LiquidStreamingService streamingService;
    private LiquidStreamingMarketDataService streamingMarketDataService;
    private LiquidStreamingTradeService streamingTradeService;

    public LiquidStreamingExchange() {
        streamingService = new LiquidStreamingService(API_BASE_URI);
    }

    @Override
    protected void initServices() {
        super.initServices();
        streamingMarketDataService = new LiquidStreamingMarketDataService(streamingService);
        streamingTradeService = new LiquidStreamingTradeService(streamingService);
        LiquidRequestCreator.initKeys(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey());
        LiquidStreamingAdapters.initCurrenciesMap(exchangeMetaData.getCurrencyPairs().keySet());
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        //...
    }

    @Override
    public Completable disconnect() {
        //...
    }

    @Override
    public boolean isAlive() {
        //...
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        //...
    }

    @Override
    public StreamingTradeService getStreamingTradeService() {
        //...
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        //...
    }
}
