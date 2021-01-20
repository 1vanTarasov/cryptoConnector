package liquid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LiquidRequest {
    @JsonProperty("event")
    private String event;
    @JsonProperty("data")
    private LiquidData data;
    
    
}
