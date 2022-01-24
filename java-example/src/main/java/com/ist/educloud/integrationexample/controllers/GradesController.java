package com.ist.educloud.integrationexample.controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ist.educloud.integrationexample.dtos.AuthenticationDTO;
import com.ist.educloud.integrationexample.dtos.GradeDTO;
import com.ist.educloud.integrationexample.services.Authenticator;
import com.ist.educloud.integrationexample.services.GradeService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class GradesController {
        private GradeService gradeService;
        public GradesController(Authenticator authenticator) {
            this.gradeService = new GradeService(authenticator);
        }

        @RequestMapping(name = "grades", path= "/grades", method = RequestMethod.GET)
        public List<GradeDTO> index() {
            return gradeService.getAllGrades();
        }

        @RequestMapping(name = "grades", path= "/grades/{id}", method = RequestMethod.GET)
        public GradeDTO show(@PathVariable String id) throws Exception {
            return gradeService.getGradeById(id);
        }
    }
