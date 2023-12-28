package com.seiright.assignment.service;


import com.seiright.assignment.dto.ChatRequest;
import com.seiright.assignment.dto.ChatResponse;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.lang3.StringUtils;
import static com.seiright.assignment.constants.loginConstants.PASSWORD;
import static com.seiright.assignment.constants.loginConstants.USERNAME;


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



//        String prompt = "Apply the compliance policy  "+ compliancePolicy + " on this piece of text for compliance checks "+ fetchWebPageData(websiteToBeChecked);
        String prompt = compliancePolicy;
        String result = chat(prompt);
        return result;
    }

    @Override
    public Boolean login(String username, String password) {



        if(StringUtils.equals(username,USERNAME)&&StringUtils.equals(password,PASSWORD))
            return  true;

        return false;
    }

    public String chat(String prompt) {
        // create a request
        ChatRequest request = new ChatRequest(model, prompt);
        request.setN(1);
        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }

    public String fetchWebPageData(String url) {

        // Fetch HTML content
        String htmlContent = restTemplate.getForObject(url, String.class);

        // Parse HTML using Jsoup
        Document document = Jsoup.parse(htmlContent);

        // Remove images from the parsed HTML
        document.select("img").remove();

        // Extract text content
        String textContent = document.text();

        return textContent;
    }





}
