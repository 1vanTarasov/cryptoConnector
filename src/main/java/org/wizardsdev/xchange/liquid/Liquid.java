package org.wizardsdev.xchange.liquid;

import org.knowm.xchange.liquid.dto.LiquidException;
import org.knowm.xchange.liquid.dto.marketdata.LiquidOrderBook;
import org.knowm.xchange.liquid.dto.marketdata.LiquidProduct;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;
import java.util.List;

// All endpoints which don`t need authentication
@Path("")
public interface Liquid {

    @GET
    @Path("/products")
    List<LiquidProduct> getCurrencyPairData() throws LiquidException, IOException;

    @GET
    @Path("/products/{id}/price_levels")
    LiquidOrderBook getOrderBook(@PathParam("id") String currency) throws LiquidException, IOException;
}
