package liquid;

import ...;
import liquid.dto.LiquidStreamingOrderBookOrder;
import liquid.dto.LiquidStreamingOrderBook;

import java.util.*;
import java.util.stream.Collectors;

public class LiquidStreamingAdapters {
    public static Map<String, CurrencyPair> currencyPairMap = new HashMap<>();
    
    public static OrderBook toOrderBook(
            LiquidStreamingOrderBook liquidStreamingOrderBook,
            CurrencyPair currencyPair) {
        
        List<LimitOrder> newAsks = liquidStreamingOrderBook.getAsks()
                .stream()
                .map(order -> toLimitOrder(order, currencyPair, Order.OrderType.ASK))
                .collect(Collectors.toList());
        
        List<LimitOrder> newBids = liquidStreamingOrderBook.getBids()
                .stream()
                .map(order -> toLimitOrder(order, currencyPair, Order.OrderType.BID))
                .collect(Collectors.toList());
        
        return new OrderBook(new Date(), newAsks, newBids);
    }
    
    private static LimitOrder toLimitOrder(LiquidStreamingOrderBookOrder liquidStreamingOrderBookOrder, CurrencyPair currencyPair, Order.OrderType orderType) {
        return new LimitOrder(
                orderType,
                liquidStreamingOrderBookOrder.getAmount(),
                currencyPair,
                null,
                new Date(System.currentTimeMillis()),
                liquidStreamingOrderBookOrder.getPrice()
        );
    }
    
    public static void initCurrenciesMap(Set<CurrencyPair> keySet) {
        keySet.forEach(currencyPair -> currencyPairMap.put(currencyPair.toString().replace("/", ""), currencyPair));
    }

    //...
}
