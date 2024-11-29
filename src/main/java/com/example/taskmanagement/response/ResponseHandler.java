package com.example.taskmanagement.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
/**
 * This function is responsible for building a standardized response for API endpoints.
 * It takes three parameters: a message, an HTTP status, and an optional response object.
 * The function constructs a map containing the message, status, and data (if provided),
 * and returns a ResponseEntity object with the constructed map and the provided HTTP status.
 *
 * @param message A brief description of the response.
 * @param httpStatus The HTTP status code for the response.
 * @param responseObject An optional object to include in the response.
 * @return A ResponseEntity object containing the constructed map and the provided HTTP status.
 */
public static ResponseEntity<Object> ResponseBuilder(
  String message, HttpStatus httpStatus, Object responseObject) {
    
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", httpStatus);
    responseMap.put("data", responseObject);

    return new ResponseEntity<>(responseMap, httpStatus);
  }

  // Additional methods for handling other response scenarios can be added here

    public static ResponseEntity<Object> ResponseBuilder(
            String message,HttpStatus httpStatus){

        Map<String,Object>responseMap = new HashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", httpStatus);

        return new ResponseEntity<>(responseMap, httpStatus);
    }

}
