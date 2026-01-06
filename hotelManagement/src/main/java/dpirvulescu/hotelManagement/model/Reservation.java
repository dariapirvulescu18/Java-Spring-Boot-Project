package dpirvulescu.hotelManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDate checkIn;

    @NotNull
    private LocalDate checkOut;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "hotel_package_id", nullable = true)
    private HotelPackage hotelPackage;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;


    public Reservation(Integer id, LocalDate checkIn, LocalDate checkOut, Room room, Customer customer, ReservationStatus status) {
        this.id = id;
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
