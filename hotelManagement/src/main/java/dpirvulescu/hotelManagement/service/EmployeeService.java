package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Integer id);

    Employee createEmployee(Employee Employee);

    List<Employee> findEmployeeByName(String name);

    List<Employee> findEmployeeByRole(String role);
}
