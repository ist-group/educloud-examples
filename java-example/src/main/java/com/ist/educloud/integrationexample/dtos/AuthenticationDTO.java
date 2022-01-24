package com.ist.educloud.integrationexample.dtos;

public class AuthenticationDTO {
    private final String access_token;
    private final String expires_in;
    private final String token_type;
    private final String scope;

    public AuthenticationDTO(String access_token, String expires_in, String token_type, String scope) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.token_type = token_type;
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    @Override
    public String toString() {
        return "{" +
                "access_token: '" + access_token + '\'' +
                ", expires_in: '" + expires_in + '\'' +
                ", token_type: '" + token_type + '\'' +
                ", scope: '" + scope + '\'' +
                '}';
    }
}
