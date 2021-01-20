package liquid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class LiquidStreamingOrderBook {
    @JsonProperty("asks")
    List<LiquidStreamingOrderBookOrder> asks;
    @JsonProperty("bids")
    List<LiquidStreamingOrderBookOrder> bids;
}
