package dpirvulescu.hotelManagement.controller;

import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.service.EmployeeRoomService;
import dpirvulescu.hotelManagement.service.EmployeeRoomServiceImpl;
import dpirvulescu.hotelManagement.service.RoomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "Room Controller",
        description = "APIs for managing hotel rooms"
)
@RestController
@RequestMapping("/rest/room")
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private EmployeeRoomServiceImpl employeeRoomService;

    @Operation(
            summary = "Create a new room",
            description = "Creates a new room with its details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        return ResponseEntity.ok(roomService.createRoom(room));
    }

    @Operation(
            summary = "Get all rooms",
            description = "Returns a list of all rooms"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully")
    })
    @GetMapping("/get/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @Operation(
            summary = "Get room by ID",
            description = "Returns a room identified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @Parameter(description = "Id of the room", example = "1")
    @GetMapping("/get/one")
    public ResponseEntity<Room> getRoom(@RequestParam(value = "id") int id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(
            summary = "Get room by number",
            description = "Returns a room identified by its room number"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @Parameter(description = "Room number", example = "101")
    @GetMapping("/get/by/number")
    public ResponseEntity<Room> getRoomByNumber(@RequestParam(value = "nr") String number) {
        return roomService.findByNumber(number)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update room",
            description = "Updates an existing room identified by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @Parameter(description = "Id of the room", example = "1")
    @PutMapping("/update")
    public ResponseEntity<Room> updateRoom(
            @RequestParam(value = "id") int id,
            @Valid @RequestBody Room room) {
        return ResponseEntity.ok(roomService.updateRoom(id, room));
    }
    @Operation(
            summary = "Delete room",
            description = "Deletes a room by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @Parameter(description = "Id of the room", example = "1")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRoom(@RequestParam(value = "id") int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Get employees for a room",
            description = "Returns all employees assigned to a specific room"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @Parameter(description = "Id of the room", example = "1")
    @GetMapping("/get/by/employee")
    public ResponseEntity<List<Employee>> getEmployeesForRoom(@RequestParam(value = "roomId" ) Integer roomId) {
        List<Employee> employees= employeeRoomService.getEmployeesForRoom(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }


}
