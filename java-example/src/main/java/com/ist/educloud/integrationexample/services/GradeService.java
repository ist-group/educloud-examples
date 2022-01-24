package com.ist.educloud.integrationexample.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ist.educloud.integrationexample.dtos.AuthenticationDTO;
import com.ist.educloud.integrationexample.dtos.DiplomaProjectDTO;
import com.ist.educloud.integrationexample.dtos.DisplayObjectDTO;
import com.ist.educloud.integrationexample.dtos.GradeDTO;
import com.ist.educloud.integrationexample.dtos.MetaDTO;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GradeService {

    @Value("${educloud.url}")
    private String url;

    private AuthenticationDTO auth;

    public GradeService(Authenticator auth) throws JsonProcessingException {
        this.auth = auth.authenticate();
    }

    private String endpoint = "";
    public ArrayList<GradeDTO> getAllGrades() throws Exception {
        ResteasyClient client = new ResteasyClientBuilder().build();
        String path = "https://api.ist.com/ss12000v2-api/source/SE00100/v2.0/grades";
        System.out.println(path);
        client.target(UriBuilder.fromPath(path));
        Response response = client.target(path)
                .request()
                .header("Authorization", auth.getToken_type() + " " + auth.getAccess_token())
                .accept(MediaType.APPLICATION_JSON)
                .get();

        String jsonResponse = response.readEntity(String.class);
        response.close();

        JsonObject gradeResponse = new JsonParser().parse(jsonResponse).getAsJsonObject();
        JsonArray gradesJson = gradeResponse.get("data").getAsJsonArray();
        ArrayList<GradeDTO> grades = new ArrayList<>();
        gradesJson.forEach(grade -> {
            try {
                grades.add(
                    parseGradeJsonToGradeDTO(grade.getAsJsonObject())
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return grades;
    }

    public GradeDTO parseGradeJsonToGradeDTO(JsonObject grade) throws ParseException {
        DisplayObjectDTO student = getDisplayObject(grade, "student");
        DisplayObjectDTO organisation = getDisplayObject(grade, "organisation");
        DisplayObjectDTO registeredBy = getDisplayObject(grade, "registeredBy");
        DisplayObjectDTO gradingTeacher = getDisplayObject(grade, "gradingTeacher");
        DisplayObjectDTO group = getDisplayObject(grade, "group");
        DisplayObjectDTO syllabus = getDisplayObject(grade, "syllabus");
        String remark = grade.has("remark") ? grade.get("remark").getAsString() : "";

        DiplomaProjectDTO diplomaProject = grade.has("diplomaProject")
        ? new DiplomaProjectDTO(
                grade.getAsJsonObject("diplomaProject").get("title").getAsString(),
                grade.getAsJsonObject("diplomaProject").get("description").getAsString(),
                grade.getAsJsonObject("diplomaProject").get("titleEnglish").getAsString(),
                grade.getAsJsonObject("diplomaProject").get("descriptionEnglish").getAsString()
        )
        : null;

        GradeDTO dto = new GradeDTO(
                grade.get("id").getAsString(),
                new MetaDTO(
                        grade.getAsJsonObject("meta").get("created").getAsString(),
                        grade.getAsJsonObject("meta").get("modified").getAsString()
                ),
                student,
                organisation,
                registeredBy,
                gradingTeacher,
                group,
                grade.get("registeredDate").getAsString(),
                grade.get("gradeValue").getAsString(),
                grade.get("finalGrade").getAsBoolean(),
                grade.get("trial").getAsBoolean(),
                grade.get("adaptedStudyPlan").getAsString(),
                remark,
                grade.get("converted").getAsBoolean(),
                grade.get("year").getAsInt(),
                syllabus,
                diplomaProject
        );

        return dto;
    }

    private DisplayObjectDTO getDisplayObject(JsonObject grade, String key) {
        DisplayObjectDTO displayObject = new DisplayObjectDTO();
        String displayName = "";
        String id = "";

        if (grade.has(key)) {
            JsonObject gradeAttribute = grade.getAsJsonObject(key);
            if (gradeAttribute.has("displayName")) {
                displayObject.setDisplayName(gradeAttribute.get("displayName").getAsString());
            }
            if (gradeAttribute.has("id")) {
                displayObject.setId(gradeAttribute.get("id").getAsString());
            }

            if (gradeAttribute.has("securityMarking")) {
                if (gradeAttribute.get("securityMarking").getAsString().equals(DisplayObjectDTO.securityMarking.NONE.label)) {
                    DisplayObjectDTO.securityMarking marking = DisplayObjectDTO.securityMarking.NONE;
                } else if (gradeAttribute.get("securityMarking").getAsString().equals(DisplayObjectDTO.securityMarking.SECRET.label)) {
                    displayObject.setSecurityMarking(DisplayObjectDTO.securityMarking.SECRET);
                } else if (gradeAttribute.get("securityMarking").getAsString().equals(DisplayObjectDTO.securityMarking.PROTECTED)) {
                    displayObject.setSecurityMarking(DisplayObjectDTO.securityMarking.PROTECTED);
                }
            }
        }

        return displayObject;
    }
}
