package org.wizardsdev.xchange.liquid.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class LiquidWithdrawalResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("address")
    private String address;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("state")
    private String state;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("withdrawal_fee")
    private BigDecimal withdrawalFee;
    @JsonProperty("created_at")
    private long createdAt;
    @JsonProperty("paymentId")
    private String paymentId;
}
