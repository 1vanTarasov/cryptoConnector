package org.wizardsdev.xchange.liquid.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class LiquidTradeHistoryWrapper {
    @JsonProperty("models")
    List<LiquidTradeHistory> models;
}
