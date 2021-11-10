package tn.esps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esps.exceptions.NotFoundException;
import tn.esps.model.Role;
import tn.esps.model.RoleName;
import tn.esps.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	public Role getRoleById(Long id) {
		return roleRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException("Role by id: " + id + ", not found!"));
	}
	
	public Role getRoleByName(RoleName roleName) {
		return roleRepository.findByName(roleName)
				 .orElseThrow(() -> new NotFoundException("Role by name: " + roleName + ", not found!"));
	}
	
	
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
	
	public Role addRole(Role role) {
		return roleRepository.save(role);
	}

	public Role updateRole(Role role) {
		return roleRepository.save(role);
	}

	public void deleteRole(Long id) {
		roleRepository.deleteRoleById(id);
	}
}
