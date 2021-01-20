package liquid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LiquidResponse {
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("data")
    private String data;
}
