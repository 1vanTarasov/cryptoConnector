package org.wizardsdev.xchange.liquid;

import ...;

// All endpoints which need authentication
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface LiquidAuthenticated extends Liquid {
    String ORDER_TYPE = "limit";

    @GET
    @Path("/crypto_accounts")
    List<LiquidBalance> getBalances(
            @HeaderParam("X-Quoine-Auth") ParamsDigest digest
    ) throws LiquidException, IOException;

    @POST
    @Path("/crypto_withdrawals")
    @Consumes(MediaType.APPLICATION_JSON)
    LiquidWithdrawalResponse withdraw(
            LiquidWithdrawalRequestWrapper withdrawalRequestWrapper,
            @HeaderParam("X-Quoine-Auth") ParamsDigest digest
    ) throws IOException;
    
    @GET
    @Path("/crypto_withdrawals")
    @Consumes(MediaType.APPLICATION_JSON)
    LiquidWithdrawalHistory fundingHistory(
            @QueryParam("currency") String currency,
            @HeaderParam("X-Quoine-Auth") ParamsDigest digest
    ) throws IOException;
}
