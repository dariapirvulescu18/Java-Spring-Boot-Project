package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.dto.AddPackageRequest;
import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.exception.CancelReservationException;
import dpirvulescu.hotelManagement.mapper.WriteReviewMapper;
import dpirvulescu.hotelManagement.model.*;
import dpirvulescu.hotelManagement.repository.*;
import dpirvulescu.hotelManagement.dto.CreateReservationRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private JPACustomerRepository customerRepository;
    @Autowired
    private JPAReservationRepository reservationRepository;
    @Autowired
    private JPARoomRepository roomRepository;

    @Autowired
    private  JPAHotelPackageRepository packageRepository;

    @Autowired
    private JPAReviewRepository reviewRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if(customerRepository.existsByEmailIgnoreCase(customer.getEmail())){
            throw new IllegalStateException("Email already exists");
        }

        if(customerRepository.existsByPhoneNumberIgnoreCase(customer.getPhoneNumber())){
            throw new IllegalStateException("Phone number already exists");
        }
        return customerRepository.save(customer);
    }

    @Override

    public List<Customer> findCustomerByName(String name){
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Reservation makeReservation(Reservation reservation) {

        if (!reservation.getCheckOut().isAfter(reservation.getCheckIn())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        return reservationRepository.save(reservation);

    }
    @Override
    public Reservation cancelReservation(Integer id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled");
        }
        LocalDate today = LocalDate.now();
        if (!reservation.getCheckIn().isAfter(today.plusDays(10))) {
            throw new CancelReservationException(reservation.getCustomer().getName());
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Reservation addPackage(Integer reservationId, Integer packageId) {

        HotelPackage hotelPackage = packageRepository.findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        boolean result = reservationRepository.existsByIdAndStatusNot(
                reservationId,
                ReservationStatus.CANCELLED
        );

        if (!result) {
            throw new IllegalStateException(
                    "The reservation is cancelled!"
            );
        }

        if (hotelPackage.getQuantity() <= 0) {
            throw new IllegalStateException("Package is no longer available");
        }
        reservation.setHotelPackage(hotelPackage);
        hotelPackage.setQuantity(hotelPackage.getQuantity() - 1);
        packageRepository.save(hotelPackage);


        return reservationRepository.save(reservation);

    }

    @Override
    public Optional<Room> getOneRoomAvailability (Integer roomId, LocalDate checkIn, LocalDate checkOut){
        return reservationRepository.findAvailableRoom(roomId, checkIn, checkOut);
    }

    @Override
    public List<Room> getAllRoomsAvailability(LocalDate checkIn, LocalDate checkOut){
        return reservationRepository.findAllAvailableRooms(checkIn, checkOut);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Review leaveReview (Review review){
        boolean alreadyReviewed = reviewRepository.existsByCustomerAndRoom(review.getCustomer(), review.getRoom());
        if (alreadyReviewed) {
            throw new IllegalStateException("Customer has already left a review for this room");
        }

        return reviewRepository.save(review);
    }

}
