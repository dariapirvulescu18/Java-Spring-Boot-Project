package dpirvulescu.hotelManagement.serviceTests;

import dpirvulescu.hotelManagement.exception.CancelReservationException;
import dpirvulescu.hotelManagement.model.*;
import dpirvulescu.hotelManagement.repository.*;
import dpirvulescu.hotelManagement.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private JPACustomerRepository customerRepository;
    @Mock
    private JPAReservationRepository reservationRepository;
    @Mock
    private JPARoomRepository roomRepository;
    @Mock
    private JPAHotelPackageRepository packageRepository;
    @Mock
    private JPAReviewRepository reviewRepository;

    private Customer customer;
    private Reservation reservation;
    private Room room;
    private HotelPackage hotelPackage;
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer(1, "John Doe", "john@example.com", "+40712345678");
        room = new Room(1, "101", 120.5, 2);
        reservation = new Reservation();
        reservation.setId(1);
        reservation.setCustomer(customer);
        reservation.setCheckIn(LocalDate.now().plusDays(15));
        reservation.setCheckOut(LocalDate.now().plusDays(20));
        reservation.setStatus(ReservationStatus.ONGOING);

        hotelPackage = new HotelPackage();
        hotelPackage.setId(1);
        hotelPackage.setName("Breakfast");
        hotelPackage.setQuantity(10);

        review = new Review();
        review.setId(1);
        review.setCustomer(customer);
        review.setRoom(room);
        review.setRating(5);
        review.setComment("Excellent");
    }



    @Test
    void testCreateCustomer_Success() {
        when(customerRepository.existsByEmailIgnoreCase(customer.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhoneNumberIgnoreCase(customer.getPhoneNumber())).thenReturn(false);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);
        assertEquals(customer, result);
    }

    @Test
    void testCreateCustomer_EmailExists() {
        when(customerRepository.existsByEmailIgnoreCase(customer.getEmail())).thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                customerService.createCustomer(customer)
        );
        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    void testCreateCustomer_PhoneExists() {
        when(customerRepository.existsByEmailIgnoreCase(customer.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhoneNumberIgnoreCase(customer.getPhoneNumber())).thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                customerService.createCustomer(customer)
        );
        assertEquals("Phone number already exists", ex.getMessage());
    }



    @Test
    void testMakeReservation_Success() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = customerService.makeReservation(reservation);
        assertEquals(reservation, result);
    }

    @Test
    void testMakeReservation_CheckOutBeforeCheckIn() {
        reservation.setCheckOut(LocalDate.now().plusDays(10));
        reservation.setCheckIn(LocalDate.now().plusDays(15));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                customerService.makeReservation(reservation)
        );
        assertEquals("Check-out date must be after check-in date", ex.getMessage());
    }

    @Test
    void testCancelReservation_Success() {
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = customerService.cancelReservation(reservation.getId());
        assertEquals(ReservationStatus.CANCELLED, result.getStatus());
    }

    @Test
    void testCancelReservation_AlreadyCancelled() {
        reservation.setStatus(ReservationStatus.CANCELLED);
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                customerService.cancelReservation(reservation.getId())
        );
        assertEquals("Reservation is already cancelled", ex.getMessage());
    }

    @Test
    void testCancelReservation_TooCloseToCheckIn() {
        reservation.setCheckIn(LocalDate.now().plusDays(5));
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        assertThrows(CancelReservationException.class, () ->
                customerService.cancelReservation(reservation.getId())
        );
    }



    @Test
    void testAddPackage_Success() {
        when(packageRepository.findById(hotelPackage.getId())).thenReturn(Optional.of(hotelPackage));
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
        when(reservationRepository.existsByIdAndStatusNot(reservation.getId(), ReservationStatus.CANCELLED))
                .thenReturn(true);
        when(packageRepository.save(hotelPackage)).thenReturn(hotelPackage);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = customerService.addPackage(reservation.getId(), hotelPackage.getId());

        assertEquals(hotelPackage, result.getHotelPackage());
        assertEquals(9, hotelPackage.getQuantity());
    }

    @Test
    void testAddPackage_ReservationCancelled() {
        when(packageRepository.findById(hotelPackage.getId())).thenReturn(Optional.of(hotelPackage));
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
        when(reservationRepository.existsByIdAndStatusNot(reservation.getId(), ReservationStatus.CANCELLED))
                .thenReturn(false);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                customerService.addPackage(reservation.getId(), hotelPackage.getId())
        );
        assertEquals("The reservation is cancelled!", ex.getMessage());
    }

    @Test
    void testAddPackage_PackageNotAvailable() {
        hotelPackage.setQuantity(0);
        when(packageRepository.findById(hotelPackage.getId())).thenReturn(Optional.of(hotelPackage));
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));
        when(reservationRepository.existsByIdAndStatusNot(reservation.getId(), ReservationStatus.CANCELLED))
                .thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                customerService.addPackage(reservation.getId(), hotelPackage.getId())
        );
        assertEquals("Package is no longer available", ex.getMessage());
    }



    @Test
    void testLeaveReview_Success() {
        when(reviewRepository.existsByCustomerAndRoom(customer, room)).thenReturn(false);
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = customerService.leaveReview(review);
        assertEquals(review, result);
    }

    @Test
    void testLeaveReview_AlreadyReviewed() {
        when(reviewRepository.existsByCustomerAndRoom(customer, room)).thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                customerService.leaveReview(review)
        );
        assertEquals("Customer has already left a review for this room", ex.getMessage());
    }

}
