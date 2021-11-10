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

import tn.esps.model.Employee;
import tn.esps.service.EmployeeService;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class EmloyeeController {
	
	@Autowired
	EmployeeService employeeService;



	@GetMapping("employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	
	@GetMapping("employee/find/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) {
		Employee employee = employeeService.getEmployeeById(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	
	@PostMapping("employee/add")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		Employee newEmployee = employeeService.addEmployee(employee);
		return new ResponseEntity<>(newEmployee, HttpStatus.OK);
	}

	
	@PutMapping("employee/update")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		Employee updatedEmployee = employeeService.updateEmployee(employee);
		return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	}

	
	@DeleteMapping("employee/delete/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
