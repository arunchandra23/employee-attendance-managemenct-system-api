package com.arun.eamsrest.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonPropertyOrder({
        "success",
        "message"
})
public class ApiResponse implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 7702134516418120340L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;
    @JsonProperty("errors")
    private List<Object> errors;
    @JsonProperty("data")
    private Object data;
    @JsonIgnore
    private HttpStatus status;

    public ApiResponse() {
        this.errors = new ArrayList<>();
    }

    public ApiResponse(Boolean success, String message, List<Object> errors, Object data, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.errors = errors;
        this.data = data;
        this.status = status;
    }


    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }


}
