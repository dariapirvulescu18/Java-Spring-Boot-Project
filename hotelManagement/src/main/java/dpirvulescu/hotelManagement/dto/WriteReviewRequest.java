package dpirvulescu.hotelManagement.dto;

import dpirvulescu.hotelManagement.model.Customer;
import dpirvulescu.hotelManagement.model.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class WriteReviewRequest {

    @NotNull
    @Min(1)
    @Max(5)
    @Schema(description = "Rating given by the customer (1-5)", example = "4", required = true)
    private Integer rating;

    @Size(max = 500)
    @Schema(description = "Optional comment provided by the customer", example = "Very clean and comfortable room", required = false)
    private String comment;

    @NotNull
    @Schema(description = "Date the review is written", example = "2026-01-06", required = true)
    private LocalDate date;

    @NotNull
    @Schema(description = "ID of the room being reviewed", example = "101", required = true)
    private Integer roomId;

    @NotNull
    @Schema(description = "ID of the customer writing the review", example = "1", required = true)
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
