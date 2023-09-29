package com.sivalabs.messages.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
class UserInfoController {

    @GetMapping("/api/me")
    Map<String, Object> currentUserDetails() {
        return getLoginUserDetails();
    }

    Map<String, Object> getLoginUserDetails() {
        Map<String, Object> map = new HashMap<>();
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        map.put("username", jwt.getClaimAsString("preferred_username"));
        map.put("email", jwt.getClaimAsString("email"));
        map.put("name", jwt.getClaimAsString("name"));
        map.put("token", jwt.getTokenValue());
        map.put("authorities", authentication.getAuthorities());
        map.put("roles", getRoles(jwt));

        return map;
    }

    List<String> getRoles(Jwt jwt) {
        Map<String,Object> realm_access = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if(realm_access != null && !realm_access.isEmpty()) {
            return  (List<String>) realm_access.get("roles");
        }
        return List.of();
    }
}
