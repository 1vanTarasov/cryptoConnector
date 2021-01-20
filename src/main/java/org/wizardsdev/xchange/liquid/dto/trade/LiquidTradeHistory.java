package org.wizardsdev.xchange.liquid.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LiquidTradeHistory {
    @JsonProperty("id")
    private String id;
    @JsonProperty("currency_pair_code")
    private String currencyPairCode;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("created_at")
    private long timeStamp;
}
