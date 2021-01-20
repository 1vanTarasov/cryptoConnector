package org.wizardsdev.xchange.liquid.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiquidWithdrawalRequestWrapper {
    @JsonProperty("crypto_withdrawal")
    private LiquidWithdrawalRequest withdrawalRequest;
}
