package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.repository.JPAEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private JPAEmployeeRepository EmployeeRepository;


    @Override
    public List<Employee> getAllEmployees() {
        return EmployeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer id) {
        return EmployeeRepository.findById(id);
    }

    @Override
    public Employee createEmployee(Employee Employee) {
        return EmployeeRepository.save(Employee);
    }

    @Override

    public List<Employee> findEmployeeByName(String name){
        return EmployeeRepository.findByNameIgnoreCaseContaining(name);
    }
    @Override
    public List<Employee> findEmployeeByRole(String role){
        return EmployeeRepository.findByRoleIgnoreCase(role);
    }
}
