package dpirvulescu.hotelManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class AddPackageRequest {
    @NotNull
    @Schema(description = "ID of the room to which the package is added", example = "101")
    private Integer roomId;

    @NotNull
    @Schema(description = "ID of the customer receiving the package", example = "1")
    private Integer customerId;

    @NotNull
    @Schema(description = "ID of the hotel package being added", example = "2")
    private Integer hotelPackageId;

    public AddPackageRequest(Integer roomId, Integer customerId, Integer hotelPackageId) {
        this.roomId = roomId;
        this.customerId = customerId;
        this.hotelPackageId = hotelPackageId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getHotelPackageId() {
        return hotelPackageId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setHotelPackageId(Integer hotelPackageId) {
        this.hotelPackageId = hotelPackageId;
    }
}
