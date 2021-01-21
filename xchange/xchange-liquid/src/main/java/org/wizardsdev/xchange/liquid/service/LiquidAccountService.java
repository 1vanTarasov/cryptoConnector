package org.wizardsdev.xchange.liquid.service;

import ...;
import org.wizardsdev.xchange.liquid.LiquidAdapters;
import org.wizardsdev.xchange.liquid.dto.LiquidException;
import org.wizardsdev.xchange.liquid.dto.account.LiquidWithdrawalRequest;
import org.wizardsdev.xchange.liquid.dto.account.LiquidWithdrawalRequestWrapper;

import java.io.IOException;

public class LiquidAccountService extends LiquidAccountServiceRaw implements AccountService {
    public LiquidAccountService(Exchange exchange) {
        super(exchange);
    }
    
    @Override
    public AccountInfo getAccountInfo() throws IOException {
        try {
            return LiquidAdapters.toAccountInfo(getLiquidBalances());
        } catch (LiquidException e) {
            throw LiquidErrorAdapter.adapt(e);
        }
    }
    
    @Override
    public String withdrawFunds(WithdrawFundsParams params) throws IOException {
        if (params instanceof RippleWithdrawFundsParams) {
            RippleWithdrawFundsParams rippleParams = (RippleWithdrawFundsParams) params;
            return liquidWithdraw(
                    new LiquidWithdrawalRequestWrapper(
                            new LiquidWithdrawalRequest(
                                    rippleParams.getCurrency().toString(),
                                    rippleParams.getAmount(),
                                    rippleParams.getAddress(),
                                    rippleParams.getTag(),
                                    null,
                                    null
                            )
                    )).getId();
        }
        if (params instanceof DefaultWithdrawFundsParams) {
            DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
            return liquidWithdraw(
                    new LiquidWithdrawalRequestWrapper(
                            new LiquidWithdrawalRequest(
                                    defaultParams.getCurrency().toString(),
                                    defaultParams.getAmount(),
                                    defaultParams.getAddress(),
                                    null,
                                    null,
                                    null)
                    )).getId();
        }
        throw new IllegalArgumentException("params should be instance of DefaultWithdrawFundsParams or RippleWithdrawFundsParams.");
    }
    
    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
        return LiquidAdapters.toFundingHistory(getWithdrawalHistory(((TradeHistoryParamCurrency) params).getCurrency()));
    }
}
