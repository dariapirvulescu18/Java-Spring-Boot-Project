package dpirvulescu.hotelManagement.dto;

import dpirvulescu.hotelManagement.model.Customer;
import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.model.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CreateReservationRequest {
    @NotNull
    @Schema(description = "Reservation check-in date", example = "2026-01-15", required = true)
    private LocalDate checkIn;

    @NotNull
    @Schema(description = "Reservation check-out date", example = "2026-01-20", required = true)
    private LocalDate checkOut;

    @NotNull
    @Schema(description = "ID of the room being reserved", example = "101", required = true)
    private Integer roomId;

    @NotNull
    @Schema(description = "ID of the customer making the reservation", example = "1", required = true)
    private Integer customerId;

    @Schema(description = "ID of the optional hotel package to include in the reservation", example = "5")
    private Integer hotelPackageId;

    public CreateReservationRequest(LocalDate checkIn, LocalDate checkOut, Integer customerId, Integer roomId, Integer hotelPackageId) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.customerId = customerId;
        this.roomId = roomId;
        this.hotelPackageId = hotelPackageId;
    }
    public CreateReservationRequest() {}

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
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

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
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
