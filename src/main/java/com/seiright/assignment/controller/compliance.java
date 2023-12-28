package com.seiright.assignment.controller;

import com.seiright.assignment.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping({"/seiright/v1"})
@CrossOrigin(allowedHeaders="*")
public class compliance {

    @Autowired
    ComplianceService complianceService;

    @GetMapping(value = "/chatbot")
    public String compliance(@RequestParam String compliancePolicy, @RequestParam String websiteToBeChecked) throws IOException {


        String results = complianceService.complianceCheck(compliancePolicy,websiteToBeChecked);
        return results;
    }

    @GetMapping(value = "/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) throws IOException {


        Boolean results = complianceService.login(username,password);

        if(results==true)
            return ResponseEntity.ok("You are authenticated!");
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");

    }



}
