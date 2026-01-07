package dpirvulescu.hotelManagement.mapper;

import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.model.Customer;
import dpirvulescu.hotelManagement.model.Review;
import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.repository.JPACustomerRepository;
import dpirvulescu.hotelManagement.repository.JPAReservationRepository;
import dpirvulescu.hotelManagement.repository.JPARoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class WriteReviewMapper {
    @Autowired
    private JPACustomerRepository customerRepository;
    @Autowired
    private JPARoomRepository roomRepository;
    public Review writeReview(WriteReviewRequest writeReviewRequest) {
        Customer customer = customerRepository.findById(writeReviewRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Room room = roomRepository.findById(writeReviewRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        Review review = new Review();
        review.setRating(writeReviewRequest.getRating());
        review.setComment(writeReviewRequest.getComment());
        review.setCustomer(customer);
        review.setRoom(room);
        review.setDate(LocalDate.from(LocalDateTime.now()));
        return review;
    }
}


