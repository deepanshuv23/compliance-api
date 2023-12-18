package com.seiright.assignment.service;


import com.seiright.assignment.dto.ChatRequest;
import com.seiright.assignment.dto.ChatResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.seiright.assignment.constant.complianceConstants.NO_RESPONSE;
import static com.seiright.assignment.constant.complianceConstants.POLICY_STRIPE;


@Service
public class complianceServiceImpl implements ComplianceService {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Qualifier("WebPageResttemplate")
    @Autowired
    private  RestTemplate restTemplateWebpage;

    @Override
    public String complianceCheck(String compliancePolicy, String websiteToBeChecked) {


        compliancePolicy = POLICY_STRIPE;
        String prompt = "Apply the compliance policy  "+ compliancePolicy + " on this piece of text for compliance checks "
                + fetchWebPageData(websiteToBeChecked) + ". Give the suggestions in points";

        String result = chat(prompt);
        return result;
    }

    public String chat(String prompt) {

        // create a request
        ChatRequest request = new ChatRequest(model, prompt);
        request.setN(1);

        // call the openAI api
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return NO_RESPONSE;
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }

    public String fetchWebPageData(String url) {

        // Fetch HTML content
        String htmlContent = restTemplateWebpage.getForObject(url, String.class);

        // Parse HTML
        Document document = Jsoup.parse(htmlContent);

        // Remove images from the parsed HTML
        document.select("img").remove();

        // Extract text content
        String textContent = document.text();

        return textContent;
    }





}
