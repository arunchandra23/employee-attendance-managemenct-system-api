package com.arun.eamsrest;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LeaveControllerTests {
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
    void deleteLeave_BasicScenario() throws JSONException {
        String body = """
                {
                  "date": "12-02-2023"
                }
                """;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity = template.exchange("http://localhost:8080/api/v1/leaves/1", HttpMethod.DELETE, httpEntity, String.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
}
