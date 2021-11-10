package tn.esps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esps.model.Role;
import tn.esps.service.RoleService;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class RoleController {
	
	@Autowired
	RoleService roleService;

	
	@GetMapping("/all")
	public ResponseEntity<List<Role>> getAllRoles() {
		List<Role> roles = roleService.getAllRoles();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
		Role role = roleService.getRoleById(id);
		return new ResponseEntity<>(role, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Role> addRole(@RequestBody Role role) {
		Role newRole = roleService.addRole(role);
		return new ResponseEntity<>(newRole, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Role> updateRole(@RequestBody Role role) {
		Role updatedRole = roleService.updateRole(role);
		return new ResponseEntity<>(updatedRole, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Role> deleteRole(@PathVariable("id") Long id) {
		roleService.deleteRole(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

}
