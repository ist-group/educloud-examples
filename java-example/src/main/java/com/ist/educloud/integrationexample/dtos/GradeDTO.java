package com.ist.educloud.integrationexample.dtos;

public class GradeDTO {
    private String id;
    private MetaDTO meta;
    private DisplayObjectDTO student;
    private DisplayObjectDTO organisation;
    private DisplayObjectDTO registeredBy;
    private DisplayObjectDTO gradingTeacher;
    private DisplayObjectDTO group;
    private String registeredDate;
    private String gradeValue;
    private boolean finalGrade;
    private boolean trial;
    private String adaptedStudyPlan;
    private String remark;
    private boolean converted;

    private Integer year;
    private DisplayObjectDTO syllabus;
    private DiplomaProjectDTO diplomaProject;
    private GradeDTO.semester semester;
    private GradeDTO.correctionType correctionType;

    public GradeDTO(String id, MetaDTO meta, DisplayObjectDTO student, DisplayObjectDTO organisation,
                    DisplayObjectDTO registeredBy, DisplayObjectDTO gradingTeacher, DisplayObjectDTO group,
                    String registeredDate, String gradeValue, boolean finalGrade, boolean trial,
                    String adaptedStudyPlan, String remark, boolean converted, Integer year,
                    DisplayObjectDTO syllabus, DiplomaProjectDTO diplomaProject, GradeDTO.semester semester, GradeDTO.correctionType correctionType) {
        this.id = id;
        this.meta = meta;
        this.student = student;
        this.organisation = organisation;
        this.registeredBy = registeredBy;
        this.gradingTeacher = gradingTeacher;
        this.group = group;
        this.registeredDate = registeredDate;
        this.gradeValue = gradeValue;
        this.finalGrade = finalGrade;
        this.trial = trial;
        this.adaptedStudyPlan = adaptedStudyPlan;
        this.remark = remark;
        this.converted = converted;
        this.year = year;
        this.syllabus = syllabus;
        this.diplomaProject = diplomaProject;
        this.semester = semester;
        this.correctionType = correctionType;
    }

    public String getSemester() {
        return (semester instanceof GradeDTO.semester) ? semester.label : null;
    }


    public void setSemester(GradeDTO.semester semester) {
        this.semester = semester;
    }

    public String getCorrectionType() {
        return (correctionType instanceof GradeDTO.correctionType) ? correctionType.label : null;
    }

    public void setCorrectionType(GradeDTO.correctionType correctionType) {
        this.correctionType = correctionType;
    }

    public enum correctionType {
        CHANGE("Ändring"),
        CORRECTION("Rättelse");

        public final String label;
        correctionType(String label) {
            this.label = label;
        }
        public static correctionType fromString(String label) {
            switch(label) {
                case "Ändring": return CHANGE;
                case "Rättelse": return CORRECTION;
                default: throw new RuntimeException(label + " does not exist");
            }
        }
    };

    public enum semester {
        HT("HT"),
        VT("VT");
        public final String label;

        semester(String label) {
            this.label = label;
        }
        public static semester fromString(String label) {
            switch(label) {
                case "VT": return VT;
                case "HT": return HT;
                default: throw new RuntimeException(label + " does not exist");
            }
        }
    };

    public DisplayObjectDTO getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(DisplayObjectDTO syllabus) {
        this.syllabus = syllabus;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }

    public DisplayObjectDTO getStudent() {
        return student;
    }

    public void setStudent(DisplayObjectDTO student) {
        this.student = student;
    }

    public DisplayObjectDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(DisplayObjectDTO organisation) {
        this.organisation = organisation;
    }

    public DisplayObjectDTO getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(DisplayObjectDTO registeredBy) {
        this.registeredBy = registeredBy;
    }

    public DisplayObjectDTO getGradingTeacher() {
        return gradingTeacher;
    }

    public void setGradingTeacher(DisplayObjectDTO gradingTeacher) {
        this.gradingTeacher = gradingTeacher;
    }

    public DisplayObjectDTO getGroup() {
        return group;
    }

    public void setGroup(DisplayObjectDTO group) {
        this.group = group;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public boolean isFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(boolean finalGrade) {
        this.finalGrade = finalGrade;
    }

    public boolean isTrial() {
        return trial;
    }

    public void setTrial(boolean trial) {
        this.trial = trial;
    }

    public String getAdaptedStudyPlan() {
        return adaptedStudyPlan;
    }

    public void setAdaptedStudyPlan(String adaptedStudyPlan) {
        this.adaptedStudyPlan = adaptedStudyPlan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isConverted() {
        return converted;
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }

    public DiplomaProjectDTO getDiplomaProject() {
        return diplomaProject;
    }

    public void setDiplomaProject(DiplomaProjectDTO diplomaProject) {
        this.diplomaProject = diplomaProject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
