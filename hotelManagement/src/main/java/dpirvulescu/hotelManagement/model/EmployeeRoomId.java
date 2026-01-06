package dpirvulescu.hotelManagement.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeRoomId implements Serializable {

    private Integer employeeId;
    private Integer roomId;

    public EmployeeRoomId() {}

    public EmployeeRoomId(Integer employeeId, Integer roomId) {
        this.employeeId = employeeId;
        this.roomId = roomId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeRoomId)) return false;
        EmployeeRoomId that = (EmployeeRoomId) o;
        return Objects.equals(employeeId, that.employeeId)
                && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, roomId);
    }
}
