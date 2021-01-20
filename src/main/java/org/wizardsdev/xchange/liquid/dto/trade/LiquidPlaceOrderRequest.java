package org.wizardsdev.xchange.liquid.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiquidPlaceOrderRequest {
    @JsonProperty("order_type")
    private String orderType;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("side")
    private String side;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("price")
    private BigDecimal price;
}
