package liquid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiquidData {
    @JsonProperty("channel")
    private String currency;
    
    public LiquidData(String currency) {
        this.currency = "price_ladders_cash_" + currency;
    }
}
