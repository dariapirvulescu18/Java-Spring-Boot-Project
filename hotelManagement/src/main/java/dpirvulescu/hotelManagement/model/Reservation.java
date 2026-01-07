package dpirvulescu.hotelManagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(name = "Reservation", description = "Represents a hotel room reservation made by a customer, optionally including a hotel package")
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the reservation", example = "1")
    private Integer id;

    @NotNull
    @Schema(description = "Check-in date for the reservation", example = "2026-02-15")
    private LocalDate checkIn;

    @NotNull
    @Schema(description = "Check-out date for the reservation", example = "2026-02-20")
    private LocalDate checkOut;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @Schema(description = "Room assigned to the reservation")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @Schema(description = "Customer who made the reservation")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "hotel_package_id", nullable = true)
    @Schema(description = "Optional hotel package included in the reservation")
    private HotelPackage hotelPackage;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the reservation", example = "CANCELLED")
    private ReservationStatus status;


    public Reservation( LocalDate checkIn, LocalDate checkOut, Room room, Customer customer, ReservationStatus status) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
        this.customer = customer;
        this.status = status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Reservation() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHotelPackage(HotelPackage hotelPackage) {
        this.hotelPackage = hotelPackage;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public HotelPackage getHotelPackage() {
        return hotelPackage;
    }
}
