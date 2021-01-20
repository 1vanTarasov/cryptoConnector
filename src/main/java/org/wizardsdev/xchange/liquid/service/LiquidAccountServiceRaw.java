package org.wizardsdev.xchange.liquid.service;

import ...;
import org.wizardsdev.xchange.liquid.LiquidAuthenticated;
import org.wizardsdev.xchange.liquid.dto.account.LiquidBalance;
import org.wizardsdev.xchange.liquid.dto.account.LiquidWithdrawalHistory;
import org.wizardsdev.xchange.liquid.dto.account.LiquidWithdrawalRequestWrapper;
import org.wizardsdev.xchange.liquid.dto.account.LiquidWithdrawalResponse;

import java.io.IOException;
import java.util.List;

//Class for withdrawing and getting raw data about wallet of user
class LiquidAccountServiceRaw extends LiquidBaseService<LiquidAuthenticated> {
    LiquidAccountServiceRaw(Exchange exchange) {
        super(LiquidAuthenticated.class, exchange);
    }
    
    List<LiquidBalance> getLiquidBalances() throws IOException {
        return restProxy.getBalances(digest);
    }
    
    LiquidWithdrawalResponse liquidWithdraw(LiquidWithdrawalRequestWrapper liquidWithdrawalRequest) throws IOException {
        return restProxy.withdraw(liquidWithdrawalRequest, digest);
    }
    
    LiquidWithdrawalHistory getWithdrawalHistory(Currency currency) throws IOException {
        return restProxy.fundingHistory(currency.toString(), digest);
    }
}
