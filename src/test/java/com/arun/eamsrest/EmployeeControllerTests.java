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
public class EmployeeControllerTests {

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
    void getAllEmployees_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity("http://localhost:8080/api/v1/employees"
                , String.class);

        JSONAssert.assertEquals(standardExpected, responseEntity.getBody().toString(), false);
    }


    @Test
    void getEmployeeAttendanceReport_basicScenario() throws JSONException {

        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/v1/employees/1/report/09-02-2023/11-02-2023", String.class);
        JSONAssert.assertEquals(standardExpected, response.getBody(), false);
    }

    @Test
    void getEmployeeAttendance_basicScenario() throws JSONException {

        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/v1/employees/1/attendance/09-02-2023/11-02-2023", String.class);
        JSONAssert.assertEquals(standardExpected, response.getBody(), false);
    }

    @Test
    void getEmployeeByDepartmentId_basicScenario() throws JSONException {

        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/v1/employees/departments/1", String.class);
        JSONAssert.assertEquals(standardExpected, response.getBody(), false);
    }

    @Test
    void addEmployee_basicScenario() throws JSONException {

        String body = """
                {
                  "firstName": "test",
                  "lastName": "test",
                  "gender": "male",
                  "age": 18,
                  "email": "test@test.com",
                  "password": "test",
                  "salaryAmount": 1000000,
                  "jobTitle": "test"
                }
                """;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity = template.exchange("http://localhost:8080/api/v1/employees/2", HttpMethod.POST, httpEntity, String.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
}