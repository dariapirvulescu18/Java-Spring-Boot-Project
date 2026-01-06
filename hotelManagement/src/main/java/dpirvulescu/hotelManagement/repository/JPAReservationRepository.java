package dpirvulescu.hotelManagement.repository;

import dpirvulescu.hotelManagement.model.Reservation;
import dpirvulescu.hotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JPAReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findByCustomerIdAndRoomId(
            Integer customerId,
            Integer roomId
    );

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM Reservation r
        WHERE r.room.id = :roomId
          AND r.checkIn < :checkOut
          AND r.checkOut > :checkIn
    """)
    boolean existsOverlappingReservation(
            @Param("roomId") Integer roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

    @Query("""
    SELECT r FROM Room r
    WHERE r.id = :roomId
      AND NOT EXISTS (
          SELECT 1 FROM Reservation res
          WHERE res.room = r
            AND res.status != 'CANCELLED'
            AND res.checkIn < :checkOut
            AND res.checkOut > :checkIn
      )
""")
    Optional<Room> findAvailableRoom(@Param("roomId") Integer roomId,
                                     @Param("checkIn") LocalDate checkIn,
                                     @Param("checkOut") LocalDate checkOut);
    @Query("""
    SELECT r FROM Room r
    WHERE NOT EXISTS (
        SELECT 1 FROM Reservation res
        WHERE res.room = r
          AND res.status != 'CANCELLED'
          AND res.checkIn < :checkOut
          AND res.checkOut > :checkIn
    )
""")
    List<Room> findAllAvailableRooms(@Param("checkIn") LocalDate checkIn,
                                  @Param("checkOut") LocalDate checkOut);
}
