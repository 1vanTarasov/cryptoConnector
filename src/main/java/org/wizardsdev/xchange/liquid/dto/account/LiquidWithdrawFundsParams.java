package org.wizardsdev.xchange.liquid.dto.account;

import lombok.Getter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.math.BigDecimal;

@Getter
public class LiquidWithdrawFundsParams implements WithdrawFundsParams {
    private final Currency currency;
    private final BigDecimal amount;
    private final String address;
    private final String paymentId;
    private final String memoType;
    private final String memoValue;

    public LiquidWithdrawFundsParams(Currency currency, BigDecimal amount, String address) {
        this.currency = currency;
        this.amount = amount;
        this.address = address;
        paymentId = null;
        memoType = null;
        memoValue = null;
    }

    public LiquidWithdrawFundsParams(Currency currency, BigDecimal amount, String address, String paymentId) {
        this.currency = currency;
        this.amount = amount;
        this.address = address;
        this.paymentId = paymentId;
        memoType = null;
        memoValue = null;
    }

    public LiquidWithdrawFundsParams(Currency currency, BigDecimal amount, String address, String memoType, String memoValue) {
        this.currency = currency;
        this.amount = amount;
        this.address = address;
        this.memoType = memoType;
        this.memoValue = memoValue;
        paymentId = null;
    }
}
