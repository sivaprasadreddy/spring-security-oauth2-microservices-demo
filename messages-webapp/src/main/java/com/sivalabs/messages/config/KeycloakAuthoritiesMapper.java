package com.sivalabs.messages.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class KeycloakAuthoritiesMapper implements GrantedAuthoritiesMapper {

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        authorities.forEach(authority -> {
            if (authority instanceof SimpleGrantedAuthority) {
                mappedAuthorities.add(authority);
            }
            else if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                OidcIdToken idToken = oidcUserAuthority.getIdToken();
                Map<String, Object> claims = idToken.getClaims();
                Map<String,Object> realm_access = (Map<String, Object>) claims.get("realm_access");
                if(realm_access != null && !realm_access.isEmpty()) {
                    List<String> roles = (List<String>) realm_access.get("roles");
                    var list = roles.stream()
                            .filter(role -> role.startsWith("ROLE_"))
                            .map(SimpleGrantedAuthority::new).toList();
                    mappedAuthorities.addAll(list);
                }
            } else if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {
                Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
                // Map the attributes found in userAttributes
                // to one or more GrantedAuthority's and add it to mappedAuthorities
            }
        });
        return mappedAuthorities;
    }
}
