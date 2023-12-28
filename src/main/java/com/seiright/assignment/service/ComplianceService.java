package com.seiright.assignment.service;

public interface ComplianceService {

    String complianceCheck(String compliancePolicy,String websiteToBeChecked);

    Boolean login(String username, String password);
}
