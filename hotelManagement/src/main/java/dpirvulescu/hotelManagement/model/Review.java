package dpirvulescu.hotelManagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "Review", description = "Represents a review left by a customer for a room")
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the review", example = "1")
    private Integer id;

    @NotNull
    @Min(1)
    @Max(5)
    @Schema(description = "Rating given by the customer (1 to 5)", example = "4")
    private Integer rating;

    @Size(max = 500)
    @Schema(description = "Optional comment provided by the customer", example = "The room was clean and comfortable.")
    private String comment;

    @NotNull
    @Schema(description = "Date when the review was left", example = "2026-01-10")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @Schema(description = "Room being reviewed")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @Schema(description = "Customer who wrote the review")
    private Customer customer;


    public Review() {}

    public Review(Integer rating, String comment, Room room, Customer customer, LocalDate date) {
        this.rating = rating;
        this.comment = comment;
        this.room = room;
        this.customer = customer;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
