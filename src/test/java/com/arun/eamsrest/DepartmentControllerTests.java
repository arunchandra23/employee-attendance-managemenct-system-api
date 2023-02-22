package com.arun.eamsrest;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentControllerTests {
    static String standardExpected = """
            {
              "success": true,
              "message": "Data retrieval successful",
              "errors": []
            }
            """;
    @Autowired
    private TestRestTemplate template;

    @Test
    void getAllDepartments_BasicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity("http://localhost:8080/api/v1/departments", String.class);
        JSONAssert.assertEquals(standardExpected, responseEntity.getBody(), false);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    void addDepartment_BasicScenario() throws JSONException {
        String body = """
                {
                  "departmentName": "Test Department"
                }
                """;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity = template.exchange("http://localhost:8080/api/v1/departments", HttpMethod.POST, httpEntity, String.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
}
