package com.ist.educloud.dto.Grades;

import java.util.Date;

import com.ist.educloud.helpers.DisplayObject;

class GradeDTO {
    private String id;
    private Meta meta;
    private DisplayObject student;
    private DisplayObject organisation;
    private DisplayObject registeredBy;
    private DisplayObject gradingTeacher;
    private DisplayObject group;
    private Date registeredDate;
    private String gradeValue;
    private boolean finalGrade;
    private boolean trial;
    private String adaptedStudyPlan;
    private String remark;
    private boolean converted;
    private enum correctionType {
        CHANGE("Ändring"),
        CORRECTION("Rättelse");

        public final String label;

        private correctionType(String label) {
            this.label = label;
        }
    }
    private enum semester {
      HT,
      VT;
    };
    private Integer year;
    private DisplayObject syllabus;
    private DiplomaProject diplomaProject;

}