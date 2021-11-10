package tn.esps.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String username; 
    private Collection<? extends GrantedAuthority> authorities;
}
