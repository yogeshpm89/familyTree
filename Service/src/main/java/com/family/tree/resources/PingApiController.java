package com.family.tree.resources;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
@javax.annotation.Generated(value = "com.rfidcollect.codegen.v3.generators.java.SpringCodegen", date = "2019-06-06T16:11:15.514Z[GMT]")
@Controller
public class PingApiController implements PingApi {

    private static final Logger log = LoggerFactory.getLogger(PingApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PingApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> pingGet() {
        String accept = request.getHeader("Accept");
        System.out.println("ping");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
