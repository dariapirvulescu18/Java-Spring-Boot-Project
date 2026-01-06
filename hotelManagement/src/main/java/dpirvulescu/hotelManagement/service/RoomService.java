package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room createRoom(Room room);
    List<Room> getAllRooms();
    Room updateRoom(int id, Room room);
    void deleteRoom(int id);
    Optional<Room> getRoomById(int id);
    Optional<Room> findByNumber(String roomNumber);

}
