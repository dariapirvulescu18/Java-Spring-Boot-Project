package dpirvulescu.hotelManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "\"EMPLOYEEROOM\"")
public class EmployeeRoom {

    @EmbeddedId
    private EmployeeRoomId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
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
