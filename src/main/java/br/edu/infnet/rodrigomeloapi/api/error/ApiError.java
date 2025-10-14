package br.edu.infnet.rodrigomeloapi.api.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    public Instant timestamp = Instant.now();
    public int status;
    public String error;
    public String message;
    public String path;
    public List<FieldErrorItem> violations;

    public ApiError(int status, String error, String message, String path, List<FieldErrorItem> violations) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.violations = violations;
    }

    public ApiError(int status, String error, String message, String path) {
        this(status, error, message, path, null);
    }

    public static record FieldErrorItem(String field, String message) { }
}
