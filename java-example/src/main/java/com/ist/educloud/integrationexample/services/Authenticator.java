package com.ist.educloud.integrationexample.services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ist.educloud.integrationexample.dtos.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Authenticator {
    @Value("${educloud.clientId}")
    private String clientId;
    @Value("${educloud.clientSecret}")
    private String clientSecret;
    @Value("${educloud.grantType}")
    private String grantType;
    private final String endpoint = "https://skolid.se/connect/token";

    public AuthenticationDTO authenticate() {
        MultivaluedHashMap entity = new MultivaluedHashMap();
        entity.add("client_id", clientId);
        entity.add("client_secret", clientSecret);
        entity.add("grant_type", grantType);

        Client client = ClientBuilder.newBuilder().build();
        client.target(UriBuilder.fromPath(endpoint));

        Response response = client.target(endpoint)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.form(entity));

        String jsonResponse = response.readEntity(String.class);
        response.close();

        JsonObject jsonObject = new JsonParser().parse(jsonResponse).getAsJsonObject();

        if (jsonObject.get("access_token") != null) {
            return new AuthenticationDTO(
                    jsonObject.get("access_token").getAsString(),
                    jsonObject.get("expires_in").getAsString(),
                    jsonObject.get("token_type").getAsString(),
                    jsonObject.get("scope").getAsString()
            );    
        } else {
            throw new RuntimeException("Error " + jsonObject);  
        }
    }
}
