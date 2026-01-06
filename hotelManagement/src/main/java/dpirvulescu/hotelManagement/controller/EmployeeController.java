package dpirvulescu.hotelManagement.controller;


import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.service.EmployeeServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl EmployeeService;

    @GetMapping("/get/all")
    public List<Employee> getAllEmployees() {
        return EmployeeService.getAllEmployees();
    }

    @GetMapping("/get/one")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam(value = "id" )Integer id) {
        Optional<Employee> employee = EmployeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/by/name")
    public ResponseEntity<List<Employee>> getEmployeeByName(@RequestParam(value = "name" ) @Size(max = 100) String name) {
        List<Employee> Employees = EmployeeService.findEmployeeByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(Employees);
    }

    @GetMapping("/get/by/role")
    public ResponseEntity<List<Employee>> getEmployeeByRole(@RequestParam(value = "role" ) @Size(max = 100) String role) {
        List<Employee> Employees = EmployeeService.findEmployeeByRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(Employees);
    }


    @PostMapping("/create")
    public Employee createEmployee(@Valid @RequestBody Employee Employee) {
        return EmployeeService.createEmployee(Employee);
    }
}
