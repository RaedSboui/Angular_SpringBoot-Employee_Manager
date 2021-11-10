package tn.esps.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.esps.model.User;
import tn.esps.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
		
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
        new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.isActive(), true, true, user.isNotLocked(), authorities);

		
	}

}
