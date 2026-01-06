package dpirvulescu.hotelManagement.controller;

import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.service.EmployeeRoomService;
import dpirvulescu.hotelManagement.service.EmployeeRoomServiceImpl;
import dpirvulescu.hotelManagement.service.RoomServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/room")
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private EmployeeRoomServiceImpl employeeRoomService;

    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        return ResponseEntity.ok(roomService.createRoom(room));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/get/one")
    public ResponseEntity<Room> getRoom(@RequestParam(value = "id") int id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/by/number")
    public ResponseEntity<Room> getRoomByNumber(@RequestParam(value = "nr") String number) {
        return roomService.findByNumber(number)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/update")
    public ResponseEntity<Room> updateRoom(
            @RequestParam(value = "id") int id,
            @Valid @RequestBody Room room) {
        return ResponseEntity.ok(roomService.updateRoom(id, room));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRoom(@RequestParam(value = "id") int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/by/employee")
    public ResponseEntity<List<Employee>> getEmployeesForRoom(@RequestParam(value = "roomId" ) Integer roomId) {
        List<Employee> employees= employeeRoomService.getEmployeesForRoom(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }


}
