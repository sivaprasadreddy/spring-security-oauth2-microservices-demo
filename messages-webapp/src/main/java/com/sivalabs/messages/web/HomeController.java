package com.sivalabs.messages.web;

import com.sivalabs.messages.domain.Message;
import com.sivalabs.messages.domain.MessageServiceClient;
import com.sivalabs.messages.domain.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final MessageServiceClient messageServiceClient;

    public HomeController(MessageServiceClient messageServiceClient) {
        this.messageServiceClient = messageServiceClient;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if(principal != null) {
            model.addAttribute("username", principal.getAttribute("name"));
        } else {
            model.addAttribute("username", "Guest");
        }
        List<Message> messages = messageServiceClient.getMessages();
        log.info("Message count: {}", messages.size());
        model.addAttribute("messages", messages);
        return "home";
    }

    @PostMapping("/messages")
    String createMessage(Message message) {
        Map<String, Object> loginUserDetails = SecurityHelper.getLoginUserDetails();
        message.setCreatedBy(loginUserDetails.get("username").toString());
        messageServiceClient.createMessage(message);
        return "redirect:/";
    }

    @PostMapping("/messages/archive")
    String archiveMessages() {
        messageServiceClient.archiveMessages();
        return "redirect:/";
    }
}