package org.wizardsdev.xchange.liquid.service;


import ...;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Class for creating token of authentication from params of request
public class LiquidDigest implements ParamsDigest {
    private String tokenId;
    private String secretKey;

    private LiquidDigest(String tokenId, String secretKey) {
        this.tokenId = tokenId;
        this.secretKey = secretKey;
    }

    static LiquidDigest createInstance(String tokenId, String secretKey) {
        return tokenId == null || secretKey == null ? null : new LiquidDigest(tokenId, secretKey);
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {
        String path = null;
        Pattern pattern = Pattern.compile(".*api.liquid.com(.*)");
        Matcher matcher = pattern.matcher(restInvocation.getInvocationUrl());
        if (matcher.find()) {
            path = matcher.group(1);
        }

        return Jwts.builder()
                .claim("path", path)
                .claim("nonce", System.currentTimeMillis())
                .claim("token_id", tokenId)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}
