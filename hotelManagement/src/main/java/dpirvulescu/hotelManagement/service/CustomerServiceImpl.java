package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.dto.AddPackageRequest;
import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.model.*;
import dpirvulescu.hotelManagement.repository.*;
import dpirvulescu.hotelManagement.dto.CreateReservationRequest;
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
                .orElseThrow(() -> new RuntimeException("Customer not found"));


        Room room = roomRepository.findById(reservationRequest.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        boolean overlapping = reservationRepository.existsOverlappingReservation(
                room.getId(),
                reservationRequest.getCheckIn(),
                reservationRequest.getCheckOut()
        );

        if (overlapping) {
            throw new RuntimeException("Room is not available for the selected dates");
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
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled");
        }
        LocalDate today = LocalDate.now();
        if (!reservation.getCheckIn().isAfter(today.plusDays(10))) {
            throw new IllegalStateException(
                    "Cannot cancel reservation: check-in is less than 10 days from today"
            );
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Reservation addPackage(AddPackageRequest addPackageRequest) {
        Customer customer = customerRepository.findById(addPackageRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        HotelPackage hotelPackage = packageRepository.findById(addPackageRequest.getHotelPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        Room room = roomRepository.findById(addPackageRequest.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Reservation reservation = reservationRepository
                .findByCustomerIdAndRoomId(
                        addPackageRequest.getCustomerId(),
                        addPackageRequest.getRoomId()
                )
                .orElseThrow(() -> new RuntimeException(
                        "Customer has no active reservation for this room"
                ));


        reservation.setHotelPackage(hotelPackage);

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
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Room room = roomRepository.findById(writeReviewRequest.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

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
