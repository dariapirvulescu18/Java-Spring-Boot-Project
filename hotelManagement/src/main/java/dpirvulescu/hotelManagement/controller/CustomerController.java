package dpirvulescu.hotelManagement.controller;

import dpirvulescu.hotelManagement.dto.AddPackageRequest;
import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.model.*;
import dpirvulescu.hotelManagement.service.CustomerServiceImpl;
import dpirvulescu.hotelManagement.dto.CreateReservationRequest;
import dpirvulescu.hotelManagement.service.RoomService;
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
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("/get/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/get/one")
    public ResponseEntity<Customer> getCustomerById(@RequestParam(value = "id" )Integer id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/by/name")
    public ResponseEntity<List<Customer>> getCustomerByName(@Size(max = 100)@RequestParam(value = "name" ) @Size(max = 100) String name) {
        List<Customer> customers = customerService.findCustomerByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }


    @PostMapping("/create")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/make/reservation")
    public Reservation makeReservation(@Valid @RequestBody CreateReservationRequest reservationRequest) {
        return customerService.makeReservation(reservationRequest);
    }
    @PutMapping("/cancel/reservation")
    public ResponseEntity<Reservation> cancelReservation(@RequestParam(value = "idReservation") int id){
        return ResponseEntity.ok(customerService.cancelReservation(id));
    }

    @PostMapping("/addpackage")
    public Reservation addPackage (@Valid @RequestBody AddPackageRequest addPackageRequest) {
        return customerService.addPackage(addPackageRequest);
    }

    @GetMapping("/get/one/room/availability")
    public ResponseEntity<Room> getOneRoomAvailability
                                                (@RequestParam(value = "roomId" ) Integer roomId,
                                                 @FutureOrPresent   @RequestParam(value = "checkIn" ) LocalDate checkIn,
                                                 @FutureOrPresent @RequestParam(value = "checkOut" ) LocalDate checkOut) {
        Optional<Room> room = customerService.getOneRoomAvailability(roomId, checkIn,checkOut);
        return room.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all/rooms/availability")
    public List<Room> getAllRoomsAvailability (@FutureOrPresent @RequestParam(value = "checkIn" ) LocalDate checkIn,
                                               @FutureOrPresent @RequestParam(value = "checkOut" ) LocalDate checkOut)  {
        return customerService.getAllRoomsAvailability(checkIn,checkOut);
    }

    @PostMapping("/leave/review")
    public Review leaveReview(@Valid @RequestBody WriteReviewRequest  writeReviewRequest) {
        return customerService.leaveReview(writeReviewRequest);
    }


}
