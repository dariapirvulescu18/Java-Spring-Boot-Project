package dpirvulescu.hotelManagement.serviceTests;

import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.model.EmployeeRoom;
import dpirvulescu.hotelManagement.model.EmployeeRoomId;
import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.repository.JPAEmployeeRepository;
import dpirvulescu.hotelManagement.repository.JPAEmployeeRoomRepository;
import dpirvulescu.hotelManagement.repository.JPARoomRepository;
import dpirvulescu.hotelManagement.service.EmployeeRoomServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeRoomServiceImplTest {

    @InjectMocks
    private EmployeeRoomServiceImpl employeeRoomService;

    @Mock
    private JPAEmployeeRoomRepository employeeRoomRepository;

    @Mock
    private JPAEmployeeRepository employeeRepository;

    @Mock
    private JPARoomRepository roomRepository;

    private Employee employee;
    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee(1, "John Doe", "Receptionist");
        room = new Room(1, "101", 120.5, 2);
    }

    // --- assignEmployeeToRoom tests ---

    @Test
    void testAssignEmployeeToRoom_Success() {
        when(employeeRoomRepository.existsByEmployeeIdAndRoomId(employee.getId(), room.getId()))
                .thenReturn(false);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));

        EmployeeRoom saved = new EmployeeRoom(employee, room);
        when(employeeRoomRepository.save(any(EmployeeRoom.class))).thenReturn(saved);

        EmployeeRoom result = employeeRoomService.assignEmployeeToRoom(employee.getId(), room.getId());

        assertNotNull(result);
        assertEquals(employee, result.getEmployee());
        assertEquals(room, result.getRoom());
    }

    @Test
    void testAssignEmployeeToRoom_AlreadyAssigned() {
        when(employeeRoomRepository.existsByEmployeeIdAndRoomId(employee.getId(), room.getId()))
                .thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                employeeRoomService.assignEmployeeToRoom(employee.getId(), room.getId())
        );
        assertEquals("Employee already assigned to this room", ex.getMessage());
    }

    @Test
    void testAssignEmployeeToRoom_EmployeeNotFound() {
        when(employeeRoomRepository.existsByEmployeeIdAndRoomId(employee.getId(), room.getId()))
                .thenReturn(false);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                employeeRoomService.assignEmployeeToRoom(employee.getId(), room.getId())
        );
        assertEquals("Employee not found", ex.getMessage());
    }

    @Test
    void testAssignEmployeeToRoom_RoomNotFound() {
        when(employeeRoomRepository.existsByEmployeeIdAndRoomId(employee.getId(), room.getId()))
                .thenReturn(false);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(roomRepository.findById(room.getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                employeeRoomService.assignEmployeeToRoom(employee.getId(), room.getId())
        );
        assertEquals("Room not found", ex.getMessage());
    }

    // --- unassignEmployeeFromRoom test ---

    @Test
    void testUnassignEmployeeFromRoom() {
        EmployeeRoomId id = new EmployeeRoomId(employee.getId(), room.getId());

        doNothing().when(employeeRoomRepository).deleteById(id);

        assertDoesNotThrow(() ->
                employeeRoomService.unassignEmployeeFromRoom(employee.getId(), room.getId())
        );

        verify(employeeRoomRepository, times(1)).deleteById(id);
    }

    // --- getRoomsForEmployee test ---

    @Test
    void testGetRoomsForEmployee() {
        EmployeeRoom er = new EmployeeRoom(employee, room);
        when(employeeRoomRepository.findByEmployeeId(employee.getId()))
                .thenReturn(List.of(er));

        List<Room> rooms = employeeRoomService.getRoomsForEmployee(employee.getId());

        assertEquals(1, rooms.size());
        assertEquals(room, rooms.get(0));
    }

    // --- getEmployeesForRoom test ---

    @Test
    void testGetEmployeesForRoom() {
        EmployeeRoom er = new EmployeeRoom(employee, room);
        when(employeeRoomRepository.findByRoomId(room.getId()))
                .thenReturn(List.of(er));

        List<Employee> employees = employeeRoomService.getEmployeesForRoom(room.getId());

        assertEquals(1, employees.size());
        assertEquals(employee, employees.get(0));
    }
}
