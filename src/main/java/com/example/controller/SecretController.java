package com.example.controller;

import com.example.service.SecretService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SecretController {
    private final SecretService secretService;

    @GetMapping("/secret")
    public String getSecret() {
        return "secret";
    }

    @PostMapping("/secret")
    public String getLink(Model model,
                          @RequestParam("secretMessage") String secretMessage) {
        String link = secretService.getLink(secretMessage);
        model.addAttribute("link", "localhost:8080/secret/" + link);
        return "secret";
    }

    @GetMapping("/secret/{link}")
    public String getSecretInfo(Model model, @PathVariable String link) {
        String secretMessage = secretService.useLink(link);
        model.addAttribute("secretMessage",secretMessage);
        return "secretInfo";
    }

}
