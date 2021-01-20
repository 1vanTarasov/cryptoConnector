package org.wizardsdev.xchange.liquid.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LiquidOrderResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("side")
    private String orderType;
    @JsonProperty("filled_quantity")
    private BigDecimal filledQuantity;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("created_at")
    private long timeStamp;
    @JsonProperty("status")
    private String status;
    @JsonProperty("funding_currency")
    private String fundingCurrency;
    @JsonProperty("currency_pair_code")
    private String currencyPairCode;
    @JsonProperty("average_price")
    private BigDecimal averagePrice;
    @JsonProperty("order_fee")
    private BigDecimal orderFee;
    @JsonProperty("message")
    private String message;
}
