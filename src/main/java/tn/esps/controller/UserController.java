package tn.esps.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esps.dto.AuthenticationResponse;
import tn.esps.dto.LoginRequest;
import tn.esps.exceptions.GenericResponse;
import tn.esps.model.User;
import tn.esps.service.UserService;



@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Transactional
public class UserController {
	
	@Autowired
	UserService userService;

	
	
    @GetMapping("users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    
    @PostMapping("user/add")
	public GenericResponse registerUser(@RequestBody User user) {
    	
    	GenericResponse response = new GenericResponse();
    	response.setHttpStatus("OK");
		response.setMessage("The new user has been created successfully!");
		response.setHttpStatusCode(201);
    	
		if (userService.existsByUsername(user.getUsername())) {
			response.setHttpStatus("Bad REQUEST");
			response.setMessage("Username is already exists!");
			response.setHttpStatusCode(406);
			return response;
		}
	
		userService.addUser(user);
		
		return response;
	}
    
	@PutMapping("user/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	
	@DeleteMapping("user/delete/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
        
    @PostMapping("auth/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.authenticate(loginRequest), HttpStatus.OK);
    }
    
    
	@GetMapping("logedUser")
	public Map<String, Object> getLogedUser(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		
		String username = securityContext.getAuthentication().getName();
		
		List<String> roles = new ArrayList<>();
		
		for(GrantedAuthority grantedAuthority : securityContext.getAuthentication().getAuthorities()) {
			roles.add(grantedAuthority.toString());
		}
		
		 Map<String, Object> params = new HashMap<>();
		 params.put("username", username);
		 params.put("roles", roles);
		 
		 return params;
	}
    
}
