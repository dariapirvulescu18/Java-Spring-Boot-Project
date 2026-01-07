package dpirvulescu.hotelManagement.serviceTests;

import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.repository.JPAEmployeeRepository;
import dpirvulescu.hotelManagement.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTests {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private JPAEmployeeRepository employeeRepository;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee1 = new Employee(1, "Alice Johnson", "Receptionist");
        employee2 = new Employee(2, "Bob Smith", "Cleaner");
    }



    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        assertTrue(result.contains(employee1));
        assertTrue(result.contains(employee2));
    }



    @Test
    void testGetEmployeeById_Found() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee1));

        Optional<Employee> result = employeeService.getEmployeeById(1);

        assertTrue(result.isPresent());
        assertEquals(employee1, result.get());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.getEmployeeById(99);

        assertFalse(result.isPresent());
    }



    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        Employee result = employeeService.createEmployee(employee1);

        assertEquals(employee1, result);
    }



    @Test
    void testFindEmployeeByName() {
        when(employeeRepository.findByNameIgnoreCaseContaining("Alice")).thenReturn(List.of(employee1));

        List<Employee> result = employeeService.findEmployeeByName("Alice");

        assertEquals(1, result.size());
        assertEquals(employee1, result.get(0));
    }



    @Test
    void testFindEmployeeByRole() {
        when(employeeRepository.findByRoleIgnoreCase("Cleaner")).thenReturn(List.of(employee2));

        List<Employee> result = employeeService.findEmployeeByRole("Cleaner");

        assertEquals(1, result.size());
        assertEquals(employee2, result.get(0));
    }
}
