package org.wizardsdev.xchange.liquid;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.liquid.dto.LiquidException;

public class LiquidErrorAdapter {
    public static ExchangeException adapt(LiquidException e) {
        return new ExchangeException(e.getMessageDesc());
    }
}
