package com.seiright.assignment.controller;

import com.seiright.assignment.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping({"/seiright/v1"})
@CrossOrigin(allowedHeaders="*")
public class compliance {

    @Autowired
    ComplianceService complianceService;

    @GetMapping(value = "/compliance")
    public String compliance(@RequestParam String compliancePolicy, @RequestParam String websiteToBeChecked) throws IOException {


        String results = complianceService.complianceCheck(compliancePolicy,websiteToBeChecked);
        return results;
    }

}
