package dpirvulescu.hotelManagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Schema(name = "EmployeeRoomId", description = "Composite key for EmployeeRoom, containing employeeId and roomId")
@Embeddable
public class EmployeeRoomId implements Serializable {
    @NotNull
    @Schema(description = "ID of the employee", example = "1")
    private Integer employeeId;
    @NotNull
    @Schema(description = "ID of the room", example = "101")
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
