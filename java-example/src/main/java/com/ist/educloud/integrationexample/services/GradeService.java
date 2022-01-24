package com.ist.educloud.integrationexample.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ist.educloud.integrationexample.dtos.AuthenticationDTO;
import com.ist.educloud.integrationexample.dtos.DiplomaProjectDTO;
import com.ist.educloud.integrationexample.dtos.DisplayObjectDTO;
import com.ist.educloud.integrationexample.dtos.GradeDTO;
import com.ist.educloud.integrationexample.dtos.MetaDTO;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GradeService {
    private final String url = "https://api.ist.com/ss12000v2-api/source/SE00100/v2.0";

    private AuthenticationDTO auth;

    public GradeService(Authenticator auth) {
        this.auth = auth.authenticate();
    }

    public List<GradeDTO> getAllGrades() {
        Response response = getRequest(url + "/grades").get();

        String jsonResponse = response.readEntity(String.class);
        response.close();

        JsonObject gradeResponse = new JsonParser().parse(jsonResponse).getAsJsonObject();
        JsonArray gradesJson = gradeResponse.get("data").getAsJsonArray();
        ArrayList<GradeDTO> grades = new ArrayList<>();
        gradesJson.forEach(grade -> grades.add(
                    toGradeDTO(grade.getAsJsonObject()))
        );

        return grades;
    }

    public GradeDTO getGradeById(String id) {
        Response response = getRequest(url + "/grades/" + id).get();

        String jsonResponse = response.readEntity(String.class);
        response.close();
        JsonObject gradeJson = new JsonParser().parse(jsonResponse).getAsJsonObject();

        return toGradeDTO(gradeJson);
    }

    private Invocation.Builder getRequest(String path) {
        ResteasyClient client = new ResteasyClientBuilder().build();

        client.target(UriBuilder.fromPath(path));
        return client.target(path)
                .request()
                .header("Authorization", auth.getToken_type() + " " + auth.getAccess_token())
                .accept(MediaType.APPLICATION_JSON);
    }

    private GradeDTO toGradeDTO(JsonObject grade) {
        DiplomaProjectDTO diplomaProject = grade.has("diplomaProject")
                ? new DiplomaProjectDTO(
                        grade.getAsJsonObject("diplomaProject").get("title").getAsString(),
                        grade.getAsJsonObject("diplomaProject").get("description").getAsString(),
                        grade.getAsJsonObject("diplomaProject").get("titleEnglish").getAsString(),
                        grade.getAsJsonObject("diplomaProject").get("descriptionEnglish").getAsString()
                )
                : null;
        GradeDTO.semester semester = grade.has("semester")
                ? GradeDTO.semester.fromString(grade.get("semester").getAsString())
                : null;
        GradeDTO.correctionType correctionType = grade.has("correctionType")
                ? GradeDTO.correctionType.fromString(grade.get("correctionType").getAsString())
                : null;

        return new GradeDTO(
                grade.get("id").getAsString(),
                new MetaDTO(
                        grade.getAsJsonObject("meta").get("created").getAsString(),
                        grade.getAsJsonObject("meta").get("modified").getAsString()
                ),
                getDisplayObject(grade, "student"),
                getDisplayObject(grade, "organisation"),
                getDisplayObject(grade, "registeredBy"),
                getDisplayObject(grade, "gradingTeacher"),
                getDisplayObject(grade, "group"),
                grade.get("registeredDate").getAsString(),
                grade.get("gradeValue").getAsString(),
                grade.get("finalGrade").getAsBoolean(),
                grade.get("trial").getAsBoolean(),
                grade.get("adaptedStudyPlan").getAsString(),
                grade.has("remark") ? grade.get("remark").getAsString() : "",
                grade.get("converted").getAsBoolean(),
                grade.get("year").getAsInt(),
                getDisplayObject(grade, "syllabus"),
                diplomaProject,
                semester,
                correctionType
        );
    }

    private DisplayObjectDTO getDisplayObject(JsonObject grade, String key) {
        DisplayObjectDTO displayObject = new DisplayObjectDTO();

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
                    displayObject.setSecurityMarking(DisplayObjectDTO.securityMarking.NONE);
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
