package org.wizardsdev.xchange.liquid.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiquidPlaceOrderRequestWrapper {
    @JsonProperty("order")
    private LiquidPlaceOrderRequest orderRequest;
}
