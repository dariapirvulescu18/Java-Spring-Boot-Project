package dpirvulescu.hotelManagement.mapper;

import dpirvulescu.hotelManagement.dto.CreateReservationRequest;
import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.model.*;
import dpirvulescu.hotelManagement.repository.JPACustomerRepository;
import dpirvulescu.hotelManagement.repository.JPAReservationRepository;
import dpirvulescu.hotelManagement.repository.JPARoomRepository;
import jakarta.persistence.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateReservationMapper {
    @Autowired
    private JPACustomerRepository customerRepository;
    @Autowired
    private JPAReservationRepository reservationRepository;
    @Autowired
    private JPARoomRepository roomRepository;
    public Reservation createReservation(CreateReservationRequest reservationRequest) {
        Customer customer = customerRepository.findById(reservationRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));


        Room room = roomRepository.findById(reservationRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        boolean overlapping = reservationRepository.existsOverlappingReservation(
                room.getId(),
                reservationRequest.getCheckIn(),
                reservationRequest.getCheckOut()
        );

        if (overlapping) {
            throw new IllegalStateException("Room is not available for the selected dates");
        }

        Reservation reservation = new Reservation();
        reservation.setCheckIn(reservationRequest.getCheckIn());
        reservation.setCheckOut(reservationRequest.getCheckOut());
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setStatus(ReservationStatus.ONGOING);
        return reservation;
    }
}
