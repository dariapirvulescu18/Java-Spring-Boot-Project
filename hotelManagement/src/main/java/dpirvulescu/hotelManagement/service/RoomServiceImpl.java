package dpirvulescu.hotelManagement.service;


import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.repository.JPAHotelPackageRepository;
import dpirvulescu.hotelManagement.repository.JPARoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private  JPARoomRepository roomRepository;
    @Autowired
    private  JPAHotelPackageRepository hotelPackageRepository;


    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room updateRoom(int id, Room room) {
        Optional<Room> existingOpt = roomRepository.findById(id);
        if (existingOpt.isPresent()) {
            Room existing = existingOpt.get();
            existing.setNumber(room.getNumber());
            existing.setPrice(room.getPrice());
            existing.setCapacity(room.getCapacity());


            return roomRepository.save(existing);
        }
        else {
            return null;
        }

    }

    @Override
    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Optional<Room> getRoomById(int id) {
        return roomRepository.findById(id);
    }


    @Override
    public Optional<Room> findByNumber(String roomNumber){
        return roomRepository.findByNumber(roomNumber);
    }



}
