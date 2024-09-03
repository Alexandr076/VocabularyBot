package com.azhdankov.vocabularybot.externalAPI.rest.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Builder
public class Login {

    private static final String url = "https://edvibe.com/Account/Login";
    private HttpHeaders httpHeaders;
    private MultiValueMap<String, String> mapping;

    public boolean isSignUPCompleted() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(mapping, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url,
                HttpMethod.POST,
                entity,
                String.class);

        return response.getStatusCode().is2xxSuccessful();
    }
}
