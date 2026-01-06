package dpirvulescu.hotelManagement.repository;

import dpirvulescu.hotelManagement.model.Customer;
import dpirvulescu.hotelManagement.model.Review;
import dpirvulescu.hotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAReviewRepository  extends JpaRepository<Review, Integer> {
    boolean existsByCustomerAndRoom(Customer customer, Room room);
}
