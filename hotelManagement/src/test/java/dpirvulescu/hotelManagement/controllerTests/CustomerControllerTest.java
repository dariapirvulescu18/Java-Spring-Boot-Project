package dpirvulescu.hotelManagement.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import dpirvulescu.hotelManagement.controller.CustomerController;
import dpirvulescu.hotelManagement.dto.CreateReservationRequest;
import dpirvulescu.hotelManagement.dto.WriteReviewRequest;
import dpirvulescu.hotelManagement.mapper.CreateReservationMapper;
import dpirvulescu.hotelManagement.mapper.WriteReviewMapper;
import dpirvulescu.hotelManagement.model.*;
import dpirvulescu.hotelManagement.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerServiceImpl customerService;

    @MockitoBean
    private CreateReservationMapper createReservationMapper;

    @MockitoBean
    private WriteReviewMapper writeReviewMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private Customer createCustomer(Integer id) {
        Customer c = new Customer();
        c.setId(id);
        c.setName("John Doe");
        c.setEmail("john@example.com");
        c.setPhoneNumber("+40712345678");
        return c;
    }

    private Room createRoom(Integer id) {
        Room r = new Room();
        r.setId(id);
        r.setNumber("101");
        r.setPrice(120.5);
        r.setCapacity(2);
        return r;
    }

    private Reservation createReservation(Integer id, Customer customer, Room room) {
        Reservation r = new Reservation();
        r.setId(id);
        r.setCustomer(customer);
        r.setRoom(room);
        r.setCheckIn(LocalDate.now().plusDays(1));
        r.setCheckOut(LocalDate.now().plusDays(3));
        r.setStatus(ReservationStatus.ONGOING);
        return r;
    }

    private Review createReview(Integer id, Customer customer, Room room) {
        Review r = new Review();
        r.setId(id);
        r.setCustomer(customer);
        r.setRoom(room);
        r.setRating(5);
        r.setComment("Excellent stay!");
        return r;
    }



    @Test
    void getAllCustomers_shouldReturn200() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(List.of(createCustomer(1), createCustomer(2)));

        mockMvc.perform(get("/rest/customers/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                        .andExpect(jsonPath("$[1].name").value("John Doe"));
    }

    @Test
    void getCustomerById_found_shouldReturn200() throws Exception {
        when(customerService.getCustomerById(1)).thenReturn(Optional.of(createCustomer(1)));

        mockMvc.perform(get("/rest/customers/get/one?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getCustomerById_notFound_shouldReturn404() throws Exception {
        when(customerService.getCustomerById(1)).thenReturn(Optional.of(createCustomer(1)));

        mockMvc.perform(get("/rest/customers/get/one?id=2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCustomer_valid_shouldReturn200() throws Exception {
        Customer input = createCustomer(null);
        when(customerService.createCustomer(any(Customer.class))).thenReturn(createCustomer(1));

        mockMvc.perform(post("/rest/customers/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void makeReservation_valid_shouldReturn200() throws Exception {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setCustomerId(1);
        request.setRoomId(1);
        request.setCheckIn(LocalDate.of(2026, 1, 8));
        request.setCheckOut(LocalDate.of(2026, 1, 10));
        Reservation reservation = createReservation(1, createCustomer(1), createRoom(1));
        reservation.setCheckIn(LocalDate.of(2026, 1, 8));
        reservation.setCheckOut(LocalDate.of(2026, 1, 10));

        when(createReservationMapper.createReservation(any(CreateReservationRequest.class)))
                .thenReturn(reservation);
        when(customerService.makeReservation(any(Reservation.class)))
                .thenReturn(reservation);

        mockMvc.perform(post("/rest/customers/make/reservation")
                        .contentType("application/json")
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void cancelReservation_shouldReturn200() throws Exception {
        Reservation reservation = createReservation(1, createCustomer(1), createRoom(1));
        when(customerService.cancelReservation(1)).thenReturn(reservation);

        mockMvc.perform(put("/rest/customers/cancel/reservation?idReservation=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void addPackage_shouldReturn200() throws Exception {
        Reservation reservation = createReservation(1, createCustomer(1), createRoom(1));
        when(customerService.addPackage(1, 1)).thenReturn(reservation);

        mockMvc.perform(post("/rest/customers/addpackage?reservationId=1&packageId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getOneRoomAvailability_found_shouldReturn200() throws Exception {
        Room room = createRoom(1);
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(3);

        when(customerService.getOneRoomAvailability(1, checkIn, checkOut))
                .thenReturn(Optional.of(room));

        mockMvc.perform(get("/rest/customers/get/one/room/availability")
                        .param("roomId", "1")
                        .param("checkIn", checkIn.toString())
                        .param("checkOut", checkOut.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getOneRoomAvailability_notFound_shouldReturn404() throws Exception {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(3);

        when(customerService.getOneRoomAvailability(1, checkIn, checkOut))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/rest/customers/get/one/room/availability")
                        .param("roomId", "1")
                        .param("checkIn", checkIn.toString())
                        .param("checkOut", checkOut.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRoomsAvailability_shouldReturn200() throws Exception {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(3);

        when(customerService.getAllRoomsAvailability(checkIn, checkOut))
                .thenReturn(List.of(createRoom(1)));

        mockMvc.perform(get("/rest/customers/get/all/rooms/availability")
                        .param("checkIn", checkIn.toString())
                        .param("checkOut", checkOut.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void leaveReview_shouldReturn200() throws Exception {
        WriteReviewRequest request = new WriteReviewRequest();
        request.setCustomerId(1);
        request.setRoomId(1);
        request.setComment("Excellent stay!");
        request.setRating(5);
        Review review = createReview(1, createCustomer(1), createRoom(1));

        when(writeReviewMapper.writeReview(any(WriteReviewRequest.class))).thenReturn(review);
        when(customerService.leaveReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/rest/customers/leave/review")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}

