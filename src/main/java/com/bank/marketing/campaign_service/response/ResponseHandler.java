package com.bank.marketing.campaign_service.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse
            (String message, HttpStatus status, Object responseObj) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status);
        response.put("data", responseObj);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
