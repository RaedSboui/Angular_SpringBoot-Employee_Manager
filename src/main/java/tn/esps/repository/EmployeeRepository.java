package tn.esps.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esps.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Transactional
	void deleteEmployeeById(Long id);

	Optional<Employee> findEmployeeById(Long id);

}
