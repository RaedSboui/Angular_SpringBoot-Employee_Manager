package tn.esps.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esps.exceptions.NotFoundException;
import tn.esps.model.Employee;
import tn.esps.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;


	public Employee addEmployee(Employee employee) {
		employee.setEmployeeCode(UUID.randomUUID().toString());
		return employeeRepository.save(employee);
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(Long id) {
		return employeeRepository.findEmployeeById(id)
				.orElseThrow(() -> new NotFoundException("User by id " + id + "not found"));
	}

	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public void deleteEmployee(Long id) {
		employeeRepository.deleteEmployeeById(id);
	}
}
