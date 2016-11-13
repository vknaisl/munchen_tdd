package cz.knaisl.munchen_tdd.core.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ErrorDto {

    @JsonProperty
    private final int code;

    @JsonProperty
    private final String message;

    @JsonProperty
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final Map<String, ?> errors;

    public ErrorDto(int code, String message, Map<String, ?> errors) {

        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public ErrorDto(int code, String message) {
        this(code, message, null);
    }

}
