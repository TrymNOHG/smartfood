package edu.ntnu.idatt2106_2023_06.backend.exception;

import edu.ntnu.idatt2105.g6.backend.exception.exists.ExistsException;
import edu.ntnu.idatt2105.g6.backend.exception.not_found.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> unauthorizedAction(UnauthorizedException e, WebRequest webRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message:", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> existsAction(ExistsException e, WebRequest webRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message:", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> notFoundAction(NotFoundException e, WebRequest webRequest){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message:", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exceptionAction(Exception e, WebRequest webRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message:", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
