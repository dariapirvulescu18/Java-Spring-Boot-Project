package dpirvulescu.hotelManagement.controller;

import dpirvulescu.hotelManagement.dto.AddPackageRequest;
import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.model.*;
import dpirvulescu.hotelManagement.service.CustomerServiceImpl;
import dpirvulescu.hotelManagement.dto.CreateReservationRequest;
import dpirvulescu.hotelManagement.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/customers")
@Tag(
        name = "Customer Controller",
        description = "APIs for managing customers, reservations, room availability and reviews"
)
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @Operation(
            summary = "Get all customers",
            description = "Returns a list of all registered customers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
    })
    @GetMapping("/get/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(
            summary = "Get customer by ID",
            description = "Returns a customer by their unique identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @Parameter(name = "id", description = "Id of the customer", required = true)
    @GetMapping("/get/one")
    public ResponseEntity<Customer> getCustomerById(@RequestParam(value = "id" )Integer id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Search customers by name",
            description = "Returns all customers whose name matches the given value"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
    })
    @Parameter(name = "name", description = "Name of the customer", required = true)
    @GetMapping("/get/by/name")
    public ResponseEntity<List<Customer>> getCustomerByName(@Size(max = 100)@RequestParam(value = "name" ) @Size(max = 100) String name) {
        List<Customer> customers = customerService.findCustomerByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @Operation(
            summary = "Create a new customer",
            description = "Creates a customer with validated name, email and Romanian phone number"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Email/phone number already exists")
    })
    @PostMapping("/create")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @Operation(
            summary = "Create a reservation",
            description = "Creates a reservation for a customer and a room in the given date interval"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or request body"),
            @ApiResponse(responseCode = "404", description = "Customer or room not found"),
            @ApiResponse(responseCode = "409", description = "Room is not available in the selected period")
    })
    @PostMapping("/make/reservation")
    public Reservation makeReservation(@Valid @RequestBody CreateReservationRequest reservationRequest) {
        return customerService.makeReservation(reservationRequest);
    }

    @Operation(
            summary = "Cancel a reservation",
            description = "Cancels an existing reservation by reservation ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation cancelled successfully"),
            @ApiResponse(responseCode = "409", description = "Cancellation not allowed"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PutMapping("/cancel/reservation")
    public ResponseEntity<Reservation> cancelReservation(@RequestParam(value = "idReservation") int id){
        return ResponseEntity.ok(customerService.cancelReservation(id));
    }

    @Operation(
            summary = "Add a package to a reservation",
            description = "Adds an extra package (e.g. breakfast, spa) to an existing reservation"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Package added successfully"),
            @ApiResponse(responseCode = "409", description = "Package limit reached or customer has no active reservation for this room"),
            @ApiResponse(responseCode = "404", description = "Reservation or package or customer not found")
    })
    @PostMapping("/addpackage")
    public Reservation addPackage (@Valid @RequestBody AddPackageRequest addPackageRequest) {
        return customerService.addPackage(addPackageRequest);
    }

    @Operation(
            summary = "Check availability for one room",
            description = "Checks if a specific room is available in the given date range"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room available"),
            @ApiResponse(responseCode = "404", description = "Room not available")
    })
    @Parameter(name = "roomId", description = "Id of the room required", required = true)
    @Parameter(name = "checkIn", description = "Check-in date (YYYY-MM-DD)", required = true)
    @Parameter(name = "checkOut", description = "Check-out date (YYYY-MM-DD)", required = true)
    @GetMapping("/get/one/room/availability")
    public ResponseEntity<Room> getOneRoomAvailability
                                                (@RequestParam(value = "roomId" ) Integer roomId,
                                                 @FutureOrPresent   @RequestParam(value = "checkIn" ) LocalDate checkIn,
                                                 @FutureOrPresent @RequestParam(value = "checkOut" ) LocalDate checkOut) {
        Optional<Room> room = customerService.getOneRoomAvailability(roomId, checkIn,checkOut);
        return room.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get available rooms",
            description = "Returns all rooms available in the given date interval"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Available rooms retrieved")
    })
    @Parameter(name = "checkIn", description = "Check-in date (YYYY-MM-DD)", required = true)
    @Parameter(name = "checkOut", description = "Check-out date (YYYY-MM-DD)", required = true)
    @GetMapping("/get/all/rooms/availability")
    public List<Room> getAllRoomsAvailability (@FutureOrPresent @RequestParam(value = "checkIn" ) LocalDate checkIn,
                                               @FutureOrPresent @RequestParam(value = "checkOut" ) LocalDate checkOut)  {
        return customerService.getAllRoomsAvailability(checkIn,checkOut);
    }

    @Operation(
            summary = "Leave a review",
            description = "Creates a review for a completed reservation"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid rating or duplicate review"),
            @ApiResponse(responseCode = "404", description = "Customer or room not found")
    })
    @PostMapping("/leave/review")
    public Review leaveReview(@Valid @RequestBody WriteReviewRequest  writeReviewRequest) {
        return customerService.leaveReview(writeReviewRequest);
    }


}
