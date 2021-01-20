package liquid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiquidStreamingLogin {
    @JsonProperty("event")
    private String event = "quoine:auth_request";
    @JsonProperty("data")
    private LiquidStreamingLoginData data;
    
    public LiquidStreamingLogin(String signature) {
        data = new LiquidStreamingLoginData(signature);
    }
    
    private static class LiquidStreamingLoginData {
        @JsonProperty("path")
        private String path = "/realtime";
        @JsonProperty("headers")
        private LiquidHeader header;
        
        public LiquidStreamingLoginData(String signature) {
            header = new LiquidHeader(signature);
        }
        
        private static class LiquidHeader {
            @JsonProperty("X-Quoine-Auth")
            private String signature;
            
            public LiquidHeader(String signature) {
                this.signature = signature;
            }
        }
    }
}
