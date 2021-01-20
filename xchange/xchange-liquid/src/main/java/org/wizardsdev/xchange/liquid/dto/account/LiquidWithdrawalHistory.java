package org.wizardsdev.xchange.liquid.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class LiquidWithdrawalHistory {
    @JsonProperty("models")
    private List<LiquidWithdrawalResponse> withdrawals;
}
