package tn.esps.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esps.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    
    @Transactional
	void deleteUserById(Long id);
}
