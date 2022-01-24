package com.ist.educloud.integrationexample.services;
import java.util.List;

import com.ist.educloud.integrationexample.dtos.GradeDTO;
import org.springframework.stereotype.Component;

@Component
public interface GradeService {

    public List<GradeDTO> getAllGrades();

    public GradeDTO getGradeById(String id);
}
