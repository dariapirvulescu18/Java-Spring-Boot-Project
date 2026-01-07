package dpirvulescu.hotelManagement.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import dpirvulescu.hotelManagement.controller.EmployeeController;
import dpirvulescu.hotelManagement.model.*;
import dpirvulescu.hotelManagement.service.EmployeeRoomServiceImpl;
import dpirvulescu.hotelManagement.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeServiceImpl employeeService;

    @MockitoBean
    private EmployeeRoomServiceImpl employeeRoomService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Room createRoom(Integer id) {
        Room r = new Room();
        r.setId(id);
        r.setNumber("101");
        r.setPrice(120.5);
        r.setCapacity(2);
        return r;
    }
    private Employee createEmployee(Integer id) {
       Employee e = new Employee();
       e.setId(id);
       e.setName("John Doe");
       e.setRole("RECEPTIONIST");
       return e;
    }




    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(createEmployee(1));
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/rest/employee/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testGetEmployeeById_Found() throws Exception {
        Mockito.when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(createEmployee(1)));

        mockMvc.perform(get("/rest/employee/get/one?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetEmployeeById_NotFound() throws Exception {
        Mockito.when(employeeService.getEmployeeById(3)).thenReturn(Optional.empty());

        mockMvc.perform(get("/rest/employee/get/one?id=3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetEmployeeByName() throws Exception {
        Mockito.when(employeeService.findEmployeeByName("John")).thenReturn(List.of(createEmployee(1)));

        mockMvc.perform(get("/rest/employee/get/by/name?name=John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testCreateEmployee() throws Exception {
        Employee input = createEmployee(null);
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(createEmployee(1));

        mockMvc.perform(post("/rest/employee/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testAssignRoom() throws Exception {
        EmployeeRoom employeeRoom = new EmployeeRoom(createEmployee(1), createRoom(1));

        Mockito.when(employeeRoomService.assignEmployeeToRoom(1, 1)).thenReturn(employeeRoom);

        mockMvc.perform(post("/rest/employee/assign/room?employeeId=1&roomId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee.id").value(1))
                .andExpect(jsonPath("$.room.id").value(1));
    }

    @Test
    void testUnassignRoom() throws Exception {
        Mockito.doNothing().when(employeeRoomService).unassignEmployeeFromRoom(1, 1);

        mockMvc.perform(delete("/rest/employee/unassign/room?employeeId=1&roomId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Room was unassigned successfully"));
    }

    @Test
    void testGetRoomsForEmployee() throws Exception {
        List<Room> rooms = List.of(createRoom(1));

        Mockito.when(employeeRoomService.getRoomsForEmployee(1)).thenReturn(rooms);

        mockMvc.perform(get("/rest/employee/get/by/room?employeeId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value( 1));
    }
}
