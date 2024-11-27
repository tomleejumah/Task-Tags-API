package com.example.taskmanagement.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
public static ResponseEntity<Object> ResponseBuilder(
  String message,HttpStatus httpStatus,Object responseObject){
    
    Map<String,Object>responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", httpStatus);
    responseMap.put("data", responseObject);

    return new ResponseEntity<>(responseMap, httpStatus);
  }
    public static ResponseEntity<Object> ResponseBuilder(
            String message,HttpStatus httpStatus){

        Map<String,Object>responseMap = new HashMap<>();
        responseMap.put("message", message);
        responseMap.put("status", httpStatus);

        return new ResponseEntity<>(responseMap, httpStatus);
    }

    public static ResponseEntity<Object> ResponseBuilder(
            HttpStatus httpStatus,Object responseObject){

        Map<String,Object>responseMap = new HashMap<>();
        responseMap.put("status", httpStatus);
        responseMap.put("data", responseObject);

        return new ResponseEntity<>(responseMap, httpStatus);
    }

}
