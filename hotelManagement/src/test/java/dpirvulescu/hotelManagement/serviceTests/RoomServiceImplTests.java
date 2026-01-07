package dpirvulescu.hotelManagement.serviceTests;

import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.repository.JPAHotelPackageRepository;
import dpirvulescu.hotelManagement.repository.JPARoomRepository;
import dpirvulescu.hotelManagement.service.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTests {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private JPARoomRepository roomRepository;

    @Mock
    private JPAHotelPackageRepository hotelPackageRepository;

    private Room room1;
    private Room room2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        room1 = new Room(1, "101", 120.5, 2);
        room2 = new Room(2, "102", 150.0, 3);
    }



    @Test
    void testCreateRoom() {
        when(roomRepository.save(room1)).thenReturn(room1);

        Room result = roomService.createRoom(room1);

        assertEquals(room1, result);
    }



    @Test
    void testGetAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(room1, room2));

        List<Room> rooms = roomService.getAllRooms();

        assertEquals(2, rooms.size());
        assertTrue(rooms.contains(room1));
        assertTrue(rooms.contains(room2));
    }



    @Test
    void testUpdateRoom_Success() {
        Room updated = new Room(null, "201", 200.0, 4);

        when(roomRepository.findById(1)).thenReturn(Optional.of(room1));
        when(roomRepository.save(any(Room.class))).thenAnswer(i -> i.getArgument(0));

        Room result = roomService.updateRoom(1, updated);

        assertNotNull(result);
        assertEquals("201", result.getNumber());
        assertEquals(200.0, result.getPrice());
        assertEquals(4, result.getCapacity());
    }

    @Test
    void testUpdateRoom_NotFound() {
        when(roomRepository.findById(99)).thenReturn(Optional.empty());

        Room result = roomService.updateRoom(99, room1);

        assertNull(result);
        verify(roomRepository, never()).save(any());
    }



    @Test
    void testDeleteRoom() {
        doNothing().when(roomRepository).deleteById(1);

        roomService.deleteRoom(1);

        verify(roomRepository, times(1)).deleteById(1);
    }



    @Test
    void testGetRoomById_Found() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(room1));

        Optional<Room> result = roomService.getRoomById(1);

        assertTrue(result.isPresent());
        assertEquals(room1, result.get());
    }

    @Test
    void testGetRoomById_NotFound() {
        when(roomRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Room> result = roomService.getRoomById(99);

        assertFalse(result.isPresent());
    }



    @Test
    void testFindByNumber_Found() {
        when(roomRepository.findByNumber("101")).thenReturn(Optional.of(room1));

        Optional<Room> result = roomService.findByNumber("101");

        assertTrue(result.isPresent());
        assertEquals(room1, result.get());
    }

    @Test
    void testFindByNumber_NotFound() {
        when(roomRepository.findByNumber("999")).thenReturn(Optional.empty());

        Optional<Room> result = roomService.findByNumber("999");

        assertFalse(result.isPresent());
    }
}
