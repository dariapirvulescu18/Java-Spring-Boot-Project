package dpirvulescu.hotelManagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(name = "EmployeeRoom", description = "Represents the assignment of an employee to a room")
@Entity
@Table(name = "\"EMPLOYEEROOM\"")
public class EmployeeRoom {

    @EmbeddedId
    @Schema(description = "Composite primary key containing employeeId and roomId")
    private EmployeeRoomId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    @Schema(description = "Employee assigned to the room")
    private Employee employee;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    @Schema(description = "Room assigned to the employee")
    private Room room;

    public EmployeeRoom() {}

    public EmployeeRoom(Employee employee, Room room) {
        this.employee = employee;
        this.room = room;
        this.id = new EmployeeRoomId(employee.getId(), room.getId());
    }

    public Employee getEmployee() {
        return employee;
    }

    public Room getRoom() {
        return room;
    }
}
