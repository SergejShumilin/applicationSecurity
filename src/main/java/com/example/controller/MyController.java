package com.example.controller;

import com.example.dao.UserRepository;
import com.example.model.UserEntity;
import com.example.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping
public class MyController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @GetMapping("/info")
    public String getInfo() {
        return "info";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login(final ModelMap modelMap, @RequestParam("error") final Optional<String> error) {
        error.ifPresent(e -> modelMap.addAttribute("error", e));

        return "login";
    }

    @GetMapping("/logoutSuccess")
    public String logout() {
        return "logout";
    }

    @GetMapping("/blocked")
    public String getBlockedUsers(Model model) {
        List<UserEntity> userEntityList = userRepository.findAll();
        Map<String, LocalDateTime> blockedUsers = userEntityList.stream().map(UserEntity::getUserName)
                .filter(username -> loginAttemptService.isBlocked(username))
                .collect(Collectors.toMap(user -> user, user -> loginAttemptService.getCachedValue(user).getBlockedTimestamp()));

        if (!blockedUsers.isEmpty()){
            model.addAttribute("blockedUsers", blockedUsers);
        }
        return "blocked";
    }
}
