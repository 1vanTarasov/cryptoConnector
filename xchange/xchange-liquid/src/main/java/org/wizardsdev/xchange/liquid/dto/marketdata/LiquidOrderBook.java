package org.wizardsdev.xchange.liquid.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class LiquidOrderBook {
    @JsonProperty("buy_price_levels")
    private List<List<BigDecimal>> bids;
    @JsonProperty("sell_price_levels")
    private List<List<BigDecimal>> asks;
}
