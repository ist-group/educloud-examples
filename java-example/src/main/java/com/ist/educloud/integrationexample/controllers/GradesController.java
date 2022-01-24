package com.ist.educloud.integrationexample.controllers;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ist.educloud.integrationexample.dtos.AuthenticationDTO;
import com.ist.educloud.integrationexample.dtos.GradeDTO;
import com.ist.educloud.integrationexample.services.Authenticator;
import com.ist.educloud.integrationexample.services.GradeService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class GradesController {
        private GradeService gradeService;
        public GradesController(Authenticator authenticator) throws JsonProcessingException {
            this.gradeService = new GradeService(authenticator);
        }

        @RequestMapping(name = "grades", path= "/grades", method = RequestMethod.GET)
        public ArrayList<GradeDTO> index() throws Exception {
            return gradeService.getAllGrades();
        }
    }