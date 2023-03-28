package com.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SecretService {

    private static final String SECRET = "SECRET";
    private final Map<String,String> tempLinks = new HashMap<>();
    public String getLink(String secretMessage) {
        if(!SECRET.equals(secretMessage)){
            throw new RuntimeException("Incorrect secret");
        }

        String link = RandomStringUtils.random(16, true, true);
        tempLinks.put(link,secretMessage);
        return link;
    }

    public String useLink(String link) {
        if(!tempLinks.containsKey(link)){
            throw new RuntimeException("The link doesn't exist");
        }

        String secretMessage = tempLinks.get(link);
        tempLinks.remove(link);
        return secretMessage;
    }
}

