package liquid.service;

import ...;
import liquid.dto.LiquidData;
import liquid.dto.LiquidRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiquidStreamingService extends JsonNettyStreamingService {
    private boolean loginRequired = true;
    
    public LiquidStreamingService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) {
       //...
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) {
        //...
    }

    @Override
    protected void subscribeChannels() {
        //...
    }

    @Override
    protected void scheduleReconnect() {
        //...
    }

    @Override
    public String getUnsubscribeMessage(String channelName) {
        //...
    }
    
    @Override
    public void sendMessage(String message) {
        if (message.contains("user_account") || message.contains("X-Quoine-Auth")) {
            super.sendMessage(message);
            return;
        }
        
        String subscribeMessage = null;
        try {
            subscribeMessage = objectMapper.writeValueAsString(new LiquidRequest("pusher:subscribe", new LiquidData(message)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        super.sendMessage(subscribeMessage);
    }
    
    @Override
    protected void handleMessage(JsonNode message) {
        if (message.toString().contains("subscription_failed")) {
            sendMessage(LiquidRequestCreator.createLogin());
        } else if (message.toString().contains("user_account") && !message.toString().contains("\"data\":{}") ||
                message.toString().contains("price_ladders_cash") && !message.toString().contains("subscription_succeeded")) {
            super.handleMessage(message);
        }
    }
}
