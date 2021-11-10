package tn.esps.security.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tn.esps.model.User;

public class UserPrincipal implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public UserPrincipal(User user) {
		super();
		this.user =  user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
        new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}
	
	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.isNotLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.user.isActive();
	}

}
