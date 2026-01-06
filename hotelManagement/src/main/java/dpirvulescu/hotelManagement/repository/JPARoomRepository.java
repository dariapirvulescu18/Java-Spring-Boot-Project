package dpirvulescu.hotelManagement.repository;

import dpirvulescu.hotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JPARoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByNumber(String roomNumber);

}
