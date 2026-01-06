package dpirvulescu.hotelManagement.controller;


import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.model.EmployeeRoom;
import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.service.EmployeeRoomService;
import dpirvulescu.hotelManagement.service.EmployeeRoomServiceImpl;
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
    @Autowired
    private EmployeeRoomServiceImpl EmployeeRoomService;

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

    @PostMapping("/assign/room")
    public EmployeeRoom assignRoom(@RequestParam(value = "employeeId" ) Integer employeeId,
                                   @RequestParam(value = "roomId" ) Integer roomId) {
        return EmployeeRoomService.assignEmployeeToRoom(employeeId, roomId);
    }

    @DeleteMapping("/unassign/room")
    public ResponseEntity<String> unassignRoom(@RequestParam(value = "employeeId" ) Integer employeeId,
                                             @RequestParam(value = "roomId" ) Integer roomId) {
        EmployeeRoomService.unassignEmployeeFromRoom(employeeId, roomId);
        return ResponseEntity.ok("Room was unassigned successfully");
    }

    @GetMapping("/get/by/room")
    public ResponseEntity<List<Room>> getRoomsForEmployee(@RequestParam(value = "employeeId" ) Integer employeeId) {
        List<Room> rooms = EmployeeRoomService.getRoomsForEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }



}
