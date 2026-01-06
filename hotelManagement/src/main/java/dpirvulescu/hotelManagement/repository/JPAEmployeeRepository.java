package dpirvulescu.hotelManagement.repository;

import dpirvulescu.hotelManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByNameIgnoreCaseContaining(String name);
    List<Employee> findByRoleIgnoreCase(String role);
}
