package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.dto.AddPackageRequest;
import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.exception.CancelReservationException;
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
    public Reservation makeReservation(CreateReservationRequest reservationRequest) {
        if (reservationRequest.getCheckIn() == null || reservationRequest.getCheckOut() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates must not be null");
        }

        if (!reservationRequest.getCheckOut().isAfter(reservationRequest.getCheckIn())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
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
    public Reservation addPackage(AddPackageRequest addPackageRequest) {
        Customer customer = customerRepository.findById(addPackageRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        HotelPackage hotelPackage = packageRepository.findById(addPackageRequest.getHotelPackageId())
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        Room room = roomRepository.findById(addPackageRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        Reservation reservation = reservationRepository
                .findByCustomerIdAndRoomId(
                        addPackageRequest.getCustomerId(),
                        addPackageRequest.getRoomId()
                )
                .orElseThrow(() -> new IllegalStateException(
                        "Customer has no active reservation for this room"
                ));

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
    public Review leaveReview (WriteReviewRequest writeReviewRequest){
        if (writeReviewRequest.getRating() < 1 || writeReviewRequest.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Customer customer = customerRepository.findById(writeReviewRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Room room = roomRepository.findById(writeReviewRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        boolean alreadyReviewed = reviewRepository.existsByCustomerAndRoom(customer, room);
        if (alreadyReviewed) {
            throw new IllegalStateException("Customer has already left a review for this room");
        }
        Review review = new Review();
        review.setRating(writeReviewRequest.getRating());
        review.setComment(writeReviewRequest.getComment());
        review.setCustomer(customer);
        review.setRoom(room);
        review.setDate(LocalDate.from(LocalDateTime.now()));


        return reviewRepository.save(review);
    }

}
