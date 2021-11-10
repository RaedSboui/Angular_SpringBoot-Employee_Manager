package tn.esps.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esps.model.Role;
import tn.esps.model.RoleName;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(RoleName roleName);
    Optional<Role> findById(Long id);
    
    @Transactional
	void deleteRoleById(Long id);

}
