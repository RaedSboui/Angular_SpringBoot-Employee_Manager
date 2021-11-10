package tn.esps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tn.esps.dto.AuthenticationResponse;
import tn.esps.dto.LoginRequest;
import tn.esps.exceptions.NotFoundException;
import tn.esps.model.User;
import tn.esps.repository.UserRepository;
import tn.esps.security.jwt.JwtTokenProvider;
import tn.esps.security.service.UserPrincipal;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;


	/*********************************************** CRUD **********************************************/
	public User getUserByUserName(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("User by username : " + username + ", not found!"));
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User updateUser(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteUserById(id);
	}

	
	/******************************************   Authentication   ******************************************/
    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginRequest.getUsername(),
                		loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(loginRequest.getUsername()).get();
        UserPrincipal principal = new UserPrincipal(user);
        String accessToken =  jwtTokenProvider.generateJwtToken(principal);
        return new AuthenticationResponse(accessToken, principal.getUsername(), principal.getAuthorities());
    }

}
