package cz.knaisl.munchen_tdd.core.rest;

import cz.knaisl.munchen_tdd.core.exceptions.BadRequestException;
import cz.knaisl.munchen_tdd.core.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@ControllerAdvice
public class ExceptionMapperAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorDto handleException(MethodArgumentNotValidException ex) {
        return this.createErrorFromBindingResult(ex.getBindingResult());
    }

    private ErrorDto createErrorFromBindingResult(BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ObjectError> globalErrors = result.getGlobalErrors();

        Map<String, Object> errors = new TreeMap<>();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, String> globalErrorsMap = new TreeMap<>();
        for (ObjectError objectError : globalErrors) {
            globalErrorsMap.put(objectError.getObjectName(), objectError.getDefaultMessage());
        }

        // put global errors as one of errors with special key
        if (!globalErrorsMap.isEmpty()) {
            errors.put("_global", globalErrorsMap);
        }

        return new ErrorDto(400, "Bad request", errors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorDto handleException(BadRequestException ex) {
        TreeMap errors = new TreeMap();
        errors.put("_global", ex.getMessage());
        return new ErrorDto(400, "Bad request", errors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorDto handleException(HttpMessageNotReadableException ex) {
        Map<String, Object> errors = new TreeMap<>();
        errors.put("_global", "Unable to read JSON. Maybe not valid schema?");
        return new ErrorDto(400, "Bad request", errors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorDto handleException(NotFoundException ex) {
        return new ErrorDto(404, ex.getMessage(), null);
    }

}
