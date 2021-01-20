package liquid.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
@JsonDeserialize(using = LiquidStreamingOrderBookOrder.LiquidStreamingOrderBookOrderDeserializer.class)
public class LiquidStreamingOrderBookOrder {
    private BigDecimal price;
    private BigDecimal amount;
    
    static class LiquidStreamingOrderBookOrderDeserializer extends JsonDeserializer<LiquidStreamingOrderBookOrder> {
        @Override
        public LiquidStreamingOrderBookOrder deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            ObjectCodec oc = parser.getCodec();
            JsonNode node = oc.readTree(parser);
            BigDecimal price = new BigDecimal(node.path(0).asText());
            BigDecimal amount = new BigDecimal(node.path(1).asText());
            return new LiquidStreamingOrderBookOrder(price, amount);
        }
    }
}
