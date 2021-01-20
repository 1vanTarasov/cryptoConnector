package org.wizardsdev.xchange.liquid.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LiquidCancelOrderResponse {
    @JsonProperty("message")
    private String message;
}
