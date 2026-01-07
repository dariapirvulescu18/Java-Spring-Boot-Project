package dpirvulescu.hotelManagement.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import dpirvulescu.hotelManagement.controller.EmployeeController;
import dpirvulescu.hotelManagement.controller.HotelPackageController;
import dpirvulescu.hotelManagement.controller.RoomController;
import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.service.EmployeeRoomServiceImpl;
import dpirvulescu.hotelManagement.service.HotelPackageService;
import dpirvulescu.hotelManagement.service.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private  final ObjectMapper objectMapper= new ObjectMapper();

    @MockitoBean
    private RoomServiceImpl roomService;

    @MockitoBean
    private EmployeeRoomServiceImpl employeeRoomService;

    private Room room1;
    private Room room2;
    private Employee employee1;

    @BeforeEach
    void setUp() {
        room1 = new Room(101, "101", 120.0, 2);
        room2 = new Room(102, "102", 150.0, 3);
        employee1 = new Employee(1, "John Doe", "RECEPTIONIST");
    }

    @Test
    void testCreateRoom() throws Exception {
        Room newRoom = new Room(103, "103", 200.0, 2);
        Room savedRoom = new Room(103, "103", 200.0, 2);

        Mockito.when(roomService.createRoom(Mockito.argThat(room ->
                room.getId() == 103 &&
                        room.getNumber().equals("103") &&
                        room.getPrice() == 200.0 &&
                        room.getCapacity() == 2
        ))).thenReturn(savedRoom);

        mockMvc.perform(post("/rest/room/create")
                        .contentType(String.valueOf((MediaType.APPLICATION_JSON)))
                        .content(objectMapper.writeValueAsString(newRoom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(103))
                .andExpect(jsonPath("$.number").value("103"))
                .andExpect(jsonPath("$.price").value(200.0))
                .andExpect(jsonPath("$.capacity").value(2));
    }


    @Test
    void testGetAllRooms() throws Exception {
        List<Room> rooms = Arrays.asList(room1, room2);
        Mockito.when(roomService.getAllRooms()).thenReturn(rooms);

        mockMvc.perform(get("/rest/room/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].number").value("101"))
                .andExpect(jsonPath("$[1].number").value("102"));
    }

    @Test
    void testGetRoomById_Found() throws Exception {
        Mockito.when(roomService.getRoomById(101)).thenReturn(Optional.of(room1));

        mockMvc.perform(get("/rest/room/get/one?id=101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("101"));
    }

    @Test
    void testGetRoomById_NotFound() throws Exception {
        Mockito.when(roomService.getRoomById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/rest/room/get/one?id=999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetRoomByNumber_Found() throws Exception {
        Mockito.when(roomService.findByNumber("101")).thenReturn(Optional.of(room1));

        mockMvc.perform(get("/rest/room/get/by/number?nr=101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101));
    }

    @Test
    void testGetRoomByNumber_NotFound() throws Exception {
        Mockito.when(roomService.findByNumber("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/rest/room/get/by/number?nr=999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateRoom() throws Exception {
        Room updatedRoom = new Room(101, "101", 180.0, 2);

        Mockito.when(roomService.updateRoom(eq(101), any(Room.class))).thenReturn(updatedRoom);

        mockMvc.perform(put("/rest/room/update?id=101")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(updatedRoom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(180.0));
    }

    @Test
    void testDeleteRoom() throws Exception {
        Mockito.doNothing().when(roomService).deleteRoom(101);

        mockMvc.perform(delete("/rest/room/delete?id=101"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetEmployeesForRoom() throws Exception {
        List<Employee> employees = List.of(employee1);
        Mockito.when(employeeRoomService.getEmployeesForRoom(101)).thenReturn(employees);

        mockMvc.perform(get("/rest/room/get/by/employee?roomId=101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }
}

