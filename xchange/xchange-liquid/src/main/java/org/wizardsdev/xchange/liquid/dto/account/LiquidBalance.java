package org.wizardsdev.xchange.liquid.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LiquidBalance {
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("reserved_balance")
    private BigDecimal reservedBalance;
}
