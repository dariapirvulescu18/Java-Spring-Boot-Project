package dpirvulescu.hotelManagement.dto;

import jakarta.validation.constraints.NotNull;

public class AddPackageRequest {
    @NotNull
    private Integer roomId;

    @NotNull
    private Integer customerId;

    @NotNull
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
