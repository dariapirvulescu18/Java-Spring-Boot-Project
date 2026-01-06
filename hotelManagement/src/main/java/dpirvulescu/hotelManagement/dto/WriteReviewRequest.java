package dpirvulescu.hotelManagement.dto;

import dpirvulescu.hotelManagement.model.Customer;
import dpirvulescu.hotelManagement.model.Room;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class WriteReviewRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @Size(max = 500)
    private String comment;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer roomId;

    @NotNull
    private Integer customerId;

    public WriteReviewRequest(Integer rating, String comment, LocalDate date, Integer roomId, Integer customerId) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.roomId = roomId;
        this.customerId = customerId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
