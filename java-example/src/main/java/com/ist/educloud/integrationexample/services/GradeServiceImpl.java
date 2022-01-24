package com.ist.educloud.integrationexample.services;

import java.util.ArrayList;
import java.util.List;

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
public class GradeServiceImpl implements GradeService {
    private static final String URL_GRADES = "https://api.ist.com/ss12000v2-api/source/SE00100/v2.0/grades";

    @Value("${educloud.url}")
    private String url;

    private AuthenticationDTO auth;

    public GradeServiceImpl(Authenticator auth) {
        this.auth = auth.authenticate();
    }

    @Override
    public List<GradeDTO> getAllGrades() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        
        client.target(UriBuilder.fromPath(URL_GRADES));
        Response response = client.target(URL_GRADES)
                .request()
                .header("Authorization", auth.getToken_type() + " " + auth.getAccess_token())
                .accept(MediaType.APPLICATION_JSON)
                .get();

        String jsonResponse = response.readEntity(String.class);
        response.close();

        JsonObject gradeResponse = new JsonParser().parse(jsonResponse).getAsJsonObject();
        JsonArray gradesJson = gradeResponse.get("data").getAsJsonArray();
        ArrayList<GradeDTO> grades = new ArrayList<>();
        gradesJson.forEach(grade -> grades.add(
                    parseGradeJsonToGradeDTO(grade.getAsJsonObject()))
        );

        return grades;
    }

    public GradeDTO parseGradeJsonToGradeDTO(JsonObject grade) {

        DiplomaProjectDTO diplomaProject = grade.has("diplomaProject")
        ? new DiplomaProjectDTO(
                grade.getAsJsonObject("diplomaProject").get("title").getAsString(),
                grade.getAsJsonObject("diplomaProject").get("description").getAsString(),
                grade.getAsJsonObject("diplomaProject").get("titleEnglish").getAsString(),
                grade.getAsJsonObject("diplomaProject").get("descriptionEnglish").getAsString()
        )
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
                diplomaProject
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
                    DisplayObjectDTO.securityMarking marking = DisplayObjectDTO.securityMarking.NONE;
                    //TODO: Handle
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
