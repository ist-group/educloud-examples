package com.ist.educloud.integrationexample.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ist.educloud.integrationexample.dtos.AuthenticationDTO;
import com.ist.educloud.integrationexample.services.Authenticator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class AuthController {
    private Authenticator authenticator;

    public AuthController(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @RequestMapping(name = "auth", path= "/auth", method = RequestMethod.GET)
    public AuthenticationDTO auth() throws JsonProcessingException {
        return authenticator.authenticate();
    }
}
