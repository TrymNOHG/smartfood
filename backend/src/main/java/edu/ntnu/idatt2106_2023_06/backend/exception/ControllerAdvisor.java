package edu.ntnu.idatt2106_2023_06.backend.exception;

import edu.ntnu.idatt2106_2023_06.backend.exception.exists.ExistsException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.NotFoundException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  ControllerAdvisor is a global exception handler for the Spring controllers. It, therefore, provides methods for
 *  handling exceptions thrown in the controllers. Thereafter, it returns an HTTP response entity with a body containing
 *  the details of the exception and the status code to be returned.
 *
 * @author Leon Egeberg Hesthaug, Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    /**
     * This method handles UnauthorizedExceptions by returning an HTTP response entity with a body containing
     * the time of the error and the message to be returned to the client with a status code of 401.
     *
     * @param e             The UnauthorizedException that was thrown
     * @param webRequest    The request that was made
     * @return              The HTTP response entity containing the details of the exception and status code
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> unauthorizedAction(UnauthorizedException e, WebRequest webRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message:", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * This method handles ExistsException by returning an HTTP response entity with a body containing
     * the time of the error and the message to be returned to the client with a status code of 400.
     *
     * @param e          The ExistsException that was thrown
     * @param webRequest The request that was made
     * @return           The HTTP response entity containing the details of the exception and status code
     */
    @ExceptionHandler(ExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> existsAction(ExistsException e, WebRequest webRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message: ", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * This method handles NotFoundException by returning an HTTP response entity with a body containing
     * the time of the error and the message to be returned to the client with a status code of 404.
     *
     * @param e          The NotFoundException that was thrown
     * @param webRequest The request that was made
     * @return           The HTTP response entity containing the details of the exception and status code
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> notFoundAction(NotFoundException e, WebRequest webRequest){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message: ", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles all uncaught exceptions in the application and returns a response with a 400 Bad Request
     * status code.
     *
     * @param e          The exception that was thrown
     * @param webRequest The current web request
     * @return           ResponseEntity with a JSON body containing the error message and a 400 Bad Request status code
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exceptionAction(Exception e, WebRequest webRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message: ", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * This method handles IOExceptions in the application and returns a response with a 500 Internal Server Error
     * status code.
     *
     * @param e          The IOException that was thrown
     * @param webRequest The current web request
     * @return           ResponseEntity with a JSON body containing the error message and a 500 Internal Server Error status code
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> ioExceptionAction(IOException e, WebRequest webRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message: ", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * This method handles JwtExceptions in the application and returns a response with a 400 Bad Request status code.
     *
     * @param e          The JwtException that was thrown
     * @param webRequest The current web request
     * @return           ResponseEntity with a JSON body containing the error message and a 400 Bad Request status code
     */
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> jwtExceptionAction(ServletException e, WebRequest webRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Time of error: ", LocalDateTime.now());
        body.put("Message: ", e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

}
