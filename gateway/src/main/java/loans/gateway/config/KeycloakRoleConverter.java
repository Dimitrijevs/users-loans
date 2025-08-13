package loans.gateway.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        
        // Get the "realm_access" section from JWT token (where Keycloak stores roles)
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");

        // If no realm_access section exists, return empty list (no roles)
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }

        // Extract the "roles" array from realm_access
        // Convert each role name to Spring Security format by adding "ROLE_" prefix
        Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
            .stream()                                           // Start streaming through the roles ( like a loop )
            .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))  // Convert "admin" to "ROLE_admin"
            .collect(Collectors.toList());                      // Collect all converted roles into a list

        return returnValue;
    }
}