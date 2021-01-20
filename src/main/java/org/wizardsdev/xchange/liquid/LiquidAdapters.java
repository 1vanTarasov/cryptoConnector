package org.wizardsdev.xchange.liquid;

import ...;
import org.wizardsdev.xchange.liquid.dto.LiquidException;
import org.wizardsdev.xchange.liquid.dto.account.LiquidBalance;
import org.wizardsdev.xchange.liquid.dto.account.LiquidWithdrawalHistory;
import org.wizardsdev.xchange.liquid.dto.marketdata.LiquidOrderBook;
import org.wizardsdev.xchange.liquid.dto.marketdata.LiquidProduct;
import org.wizardsdev.xchange.liquid.dto.trade.LiquidOrderModels;
import org.wizardsdev.xchange.liquid.dto.trade.LiquidOrderResponse;
import org.wizardsdev.xchange.liquid.service.LiquidMarketDataServiceRaw;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Class which adapts raw data from exchange
public class LiquidAdapters {
    private static List<LiquidProduct> productsRaw;
    private static Map<CurrencyPair, String> currencyMap = new HashMap<>();
    
    private LiquidAdapters() {
    }
    
    public static List<LiquidProduct> getProductsRaw() {
        return productsRaw;
    }
    
    public static void initCurrencyMap(LiquidExchange liquidExchange) {
        if (currencyMap.isEmpty()) {
            LiquidMarketDataServiceRaw marketDataServiceRaw = new LiquidMarketDataServiceRaw(liquidExchange);
            try {
                productsRaw = marketDataServiceRaw.getProductsRaw();
            } catch (LiquidException e) {
                throw LiquidErrorAdapter.adapt(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
            productsRaw.forEach(liquidProduct ->
                    currencyMap.put(
                            new CurrencyPair(liquidProduct.getBaseCurrency(), liquidProduct.getQuotedCurrency()),
                            liquidProduct.getId()
                    ));
        }
    }
    
    public static OrderBook toOrderBook(LiquidOrderBook liquidOrderBook, CurrencyPair currencyPair) {
        Date timeStamp = new Date();
        List<LimitOrder> asks = toLimitOrderList(liquidOrderBook.getAsks(), Order.OrderType.ASK, currencyPair, timeStamp);
        List<LimitOrder> bids = toLimitOrderList(liquidOrderBook.getBids(), Order.OrderType.BID, currencyPair, timeStamp);
        return new OrderBook(timeStamp, asks, bids);
    }
    
    private static List<LimitOrder> toLimitOrderList(
            List<List<BigDecimal>> liquidLimitOrders,
            Order.OrderType orderType,
            CurrencyPair currencyPair,
            Date timeStamp) {
        return liquidLimitOrders.stream()
                .map(liquidOrder -> toLimitOrder(liquidOrder, orderType, currencyPair, timeStamp))
                .collect(Collectors.toList());
    }
    
    private static LimitOrder toLimitOrder(
            List<BigDecimal> liquidLimitOrder,
            Order.OrderType orderType,
            CurrencyPair currencyPair,
            Date timeStamp) {
        BigDecimal price = liquidLimitOrder.get(0);
        BigDecimal amount = liquidLimitOrder.get(1);
        return new LimitOrder(orderType, amount, currencyPair, null, timeStamp, price);
    }
    
    public static String toLiquidCurrencyPair(CurrencyPair currencyPair) {
        String liquidCurrencyPair = currencyMap.get(currencyPair);
        if (liquidCurrencyPair == null) {
            throw new IllegalArgumentException("Unknown currency: " + currencyPair);
        }
        return liquidCurrencyPair;
    }
    
    public static AccountInfo toAccountInfo(List<LiquidBalance> liquidBalances) {
        List<Balance> balances = liquidBalances.stream()
                .map(liquidBalance ->
                        new Balance(
                                new Currency(liquidBalance.getCurrency()),
                                liquidBalance.getBalance(),
                                liquidBalance.getBalance().subtract(liquidBalance.getReservedBalance()),
                                liquidBalance.getReservedBalance()))
                .collect(Collectors.toList());
        return new AccountInfo(new Wallet(balances));
    }
    
    private static CurrencyPair toCurrencyPair(String currencyPairCode) {
        return currencyMap.keySet()
                .stream()
                .filter(x -> (x.base.toString() + x.counter.toString()).equals(currencyPairCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown currency pair: " + currencyPairCode));
    }
    
    static ExchangeMetaData toExchangeMetaData(List<LiquidProduct> productsRaw, ExchangeMetaData exchangeMetaData) {
        productsRaw.stream()
                .map(product -> toCurrencyPair(product.getCurrencyPair()))
                .filter(pair -> !exchangeMetaData.getCurrencyPairs().containsKey(pair))
                .forEachOrdered(pair ->
                        exchangeMetaData.getCurrencyPairs().put(pair, new CurrencyPairMetaData()));
        
        exchangeMetaData.getCurrencyPairs().keySet()
                .removeIf(e -> !productsRaw.stream()
                        .map(p -> toCurrencyPair(p.getCurrencyPair()))
                        .collect(Collectors.toList())
                        .contains(e));
        LiquidProduct aProduct = productsRaw.get(0);
        exchangeMetaData.setTakerFee(aProduct.getTakerFee());
        exchangeMetaData.setMakerFee(aProduct.getMakerFee());
        return exchangeMetaData;
    }
    
    public static List<FundingRecord> toFundingHistory(LiquidWithdrawalHistory withdrawalHistory) {
        return withdrawalHistory.getWithdrawals().stream()
                .map(liquidWithdrawal -> new FundingRecord(
                        liquidWithdrawal.getAddress(),
                        new Date(liquidWithdrawal.getCreatedAt() * 1000),
                        new Currency(liquidWithdrawal.getCurrency()),
                        liquidWithdrawal.getAmount(),
                        liquidWithdrawal.getId(),
                        null,
                        null,
                        toWithdrawalStatus(liquidWithdrawal.getState()),
                        null,
                        liquidWithdrawal.getWithdrawalFee(),
                        null))
                .collect(Collectors.toList());
    }
    
    private static FundingRecord.Status toWithdrawalStatus(String state) {
        switch (state) {
            case "pending":
            case "processing":
            case "to_be_reviewed":
            case "broadcasted":
                return FundingRecord.Status.PROCESSING;
            case "filed":
            case "processed":
                return FundingRecord.Status.COMPLETE;
            case "cancelled":
            case "reverted":
                return FundingRecord.Status.CANCELLED;
            case "declined":
                return FundingRecord.Status.FAILED;
        }
        return null;
    }
}
