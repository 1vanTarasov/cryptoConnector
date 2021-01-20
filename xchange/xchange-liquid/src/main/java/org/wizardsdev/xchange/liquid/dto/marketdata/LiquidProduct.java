package org.wizardsdev.xchange.liquid.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class LiquidProduct {
    @JsonProperty("id")
    private String id;
    @JsonProperty("currency_pair_code")
    private String currencyPair;
    @JsonProperty("base_currency")
    private String baseCurrency;
    @JsonProperty("quoted_currency")
    private String quotedCurrency;
    @JsonProperty("taker_fee")
    private BigDecimal takerFee;
    @JsonProperty("maker_fee")
    private BigDecimal makerFee;
}
