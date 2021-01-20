package org.wizardsdev.xchange.liquid.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import si.mazi.rescu.HttpStatusExceptionSupport;

import java.io.IOException;

@JsonDeserialize(using = LiquidException.Deserializer.class)
public class LiquidException extends HttpStatusExceptionSupport {

    private String message;

    public LiquidException(String message) {
        this.message = message;
    }

    public String getMessageDesc() {
        return message;
    }

    static class Deserializer extends JsonDeserializer<LiquidException> {
        @Override
        public LiquidException deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            if (jsonNode.has("message")) {
                return new LiquidException(jsonNode.get("message").textValue());
            } else if (jsonNode.has("errors")) {
                return new LiquidException(jsonNode.get("errors").textValue());
            } else
                throw new IllegalArgumentException("Couldn't parse Exception");
        }
    }
}

