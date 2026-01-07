package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.dto.AddPackageRequest;
import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.model.Customer;
import dpirvulescu.hotelManagement.model.Reservation;
import dpirvulescu.hotelManagement.dto.CreateReservationRequest;
import dpirvulescu.hotelManagement.model.Review;
import dpirvulescu.hotelManagement.model.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Integer id);

    Customer createCustomer(Customer customer);

    List<Customer> findCustomerByName(String name);

    Reservation makeReservation(Reservation reservation);

    Reservation cancelReservation(Integer id);

    Reservation addPackage(Integer reservationId, Integer packageId);

    Optional<Room> getOneRoomAvailability (Integer roomId, LocalDate checkIn, LocalDate checkOut);

    List<Room> getAllRoomsAvailability(LocalDate checkIn, LocalDate checkOut);

    Review leaveReview (Review review);

}
