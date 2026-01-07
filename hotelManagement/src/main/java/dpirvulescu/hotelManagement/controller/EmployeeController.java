package dpirvulescu.hotelManagement.controller;


import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.model.EmployeeRoom;
import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.service.EmployeeRoomService;
import dpirvulescu.hotelManagement.service.EmployeeRoomServiceImpl;
import dpirvulescu.hotelManagement.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Employee Controller",
        description = "APIs for managing employees and assigning rooms"
)
@RestController
@RequestMapping("/rest/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl EmployeeService;
    @Autowired
    private EmployeeRoomServiceImpl EmployeeRoomService;

    @Operation(
            summary = "Get all employees",
            description = "Returns a list of all employees"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully")
    })
    @GetMapping("/get/all")
    public List<Employee> getAllEmployees() {
        return EmployeeService.getAllEmployees();
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Returns an employee identified by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @Parameter(description = "Id of the employee ", example = "1")
    @GetMapping("/get/one")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam(value = "id" )Integer id) {
        Optional<Employee> employee = EmployeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(
            summary = "Search employees by name",
            description = "Returns employees whose name contains the given value"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully")
    })
    @Parameter(description = "Employee name (partial or full)", example = "John")
    @GetMapping("/get/by/name")
    public ResponseEntity<List<Employee>> getEmployeeByName(@RequestParam(value = "name" ) @Size(max = 100) String name) {
        List<Employee> Employees = EmployeeService.findEmployeeByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(Employees);
    }

    @Operation(
            summary = "Search employees by role",
            description = "Returns employees with the specified role"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully")
    })
    @Parameter(description = "Employee role", example = "RECEPTIONIST")
    @GetMapping("/get/by/role")
    public ResponseEntity<List<Employee>> getEmployeeByRole(@RequestParam(value = "role" ) @Size(max = 100) String role) {
        List<Employee> Employees = EmployeeService.findEmployeeByRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(Employees);
    }

    @Operation(
            summary = "Create a new employee",
            description = "Creates a new employee with validated data"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping("/create")
    public Employee createEmployee(@Valid @RequestBody Employee Employee) {
        return EmployeeService.createEmployee(Employee);
    }

    @Operation(
            summary = "Assign a room to an employee",
            description = "Creates an assignment between an employee and a room"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Employee or room not found")
    })
    @Parameter(description = "Id of the employee", example = "1")
    @Parameter(description = "Id of the room", example = "101")
    @PostMapping("/assign/room")
    public EmployeeRoom assignRoom(@RequestParam(value = "employeeId" ) Integer employeeId,
                                   @RequestParam(value = "roomId" ) Integer roomId) {
        return EmployeeRoomService.assignEmployeeToRoom(employeeId, roomId);
    }

    @Operation(
            summary = "Unassign room from employee",
            description = "Removes the assignment between an employee and a room"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room unassigned successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    @Parameter(description = "Id of the employee", example = "1")
    @Parameter(description = "Id of the room", example = "101")
    @DeleteMapping("/unassign/room")
    public ResponseEntity<String> unassignRoom(@RequestParam(value = "employeeId" ) Integer employeeId,
                                             @RequestParam(value = "roomId" ) Integer roomId) {
        EmployeeRoomService.unassignEmployeeFromRoom(employeeId, roomId);
        return ResponseEntity.ok("Room was unassigned successfully");
    }

    @Operation(
            summary = "Get rooms for employee",
            description = "Returns all rooms assigned to a specific employee"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @Parameter(description = "Id of the employee", example = "1")
    @GetMapping("/get/by/room")
    public ResponseEntity<List<Room>> getRoomsForEmployee(@RequestParam(value = "employeeId" ) Integer employeeId) {
        List<Room> rooms = EmployeeRoomService.getRoomsForEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }



}
