package com.rftdevgroup.transporthub.configuration.security;

import com.rftdevgroup.transporthub.data.dto.user.UserCredentialDTO;
import com.rftdevgroup.transporthub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@link AuthenticationProvider} class for authenticating users and managing authorities.
 */
@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserCredentialDTO> user = userService.findAndMapUser(username, UserCredentialDTO.class);

        if (user.isPresent() && encoder.matches(password, user.get().getPassword())) {
            //Get authorities
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : user.get().getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
