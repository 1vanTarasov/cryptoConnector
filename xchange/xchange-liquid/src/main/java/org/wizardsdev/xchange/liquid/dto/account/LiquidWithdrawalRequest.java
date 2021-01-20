package org.wizardsdev.xchange.liquid.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiquidWithdrawalRequest {
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("address")
    private String address;
    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("memo_type")
    private String memoType;
    @JsonProperty("memo_value")
    private String memoValue;
}
