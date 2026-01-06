package dpirvulescu.hotelManagement.dto;

import dpirvulescu.hotelManagement.model.Customer;
import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.model.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CreateReservationRequest {
    @NotNull
    private LocalDate checkIn;

    @NotNull
    private LocalDate checkOut;

    @NotNull
    private Integer roomId;

    @NotNull
    private Integer customerId;

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
