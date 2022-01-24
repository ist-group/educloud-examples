package com.ist.educloud.integrationexample.controllers;

import java.util.List;

import com.ist.educloud.integrationexample.dtos.GradeDTO;
import com.ist.educloud.integrationexample.services.GradeService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class GradesController {
        private GradeService gradeService;
        public GradesController(GradeService gradeService) {
            this.gradeService = gradeService;
        }

        @RequestMapping(name = "grades", path= "/grades", method = RequestMethod.GET)
        public List<GradeDTO> index() {
            return gradeService.getAllGrades();
        }
    }
