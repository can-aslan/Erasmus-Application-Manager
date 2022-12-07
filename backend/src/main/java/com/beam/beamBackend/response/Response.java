package com.beam.beamBackend.response;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class Response {

    public static ResponseEntity<Object> create(String message, HttpStatus status, Object data) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        response.put("timestamp", timestamp);
        response.put("message", message);
        response.put("status", status);
        response.put("data", data);

        return new ResponseEntity<Object>(response, status);
    }

    public static ResponseEntity<Object> create(String message, HttpStatus status) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        response.put("timestamp", timestamp);
        response.put("message", message);
        response.put("status", status);

        return new ResponseEntity<Object>(response, status);
    }

    public static ResponseEntity<Object> create(String message, int status) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        response.put("timestamp", timestamp);
        response.put("message", message);
        response.put("status", status);

        return new ResponseEntity<Object>(response, HttpStatusCode.valueOf(status));
    }
}