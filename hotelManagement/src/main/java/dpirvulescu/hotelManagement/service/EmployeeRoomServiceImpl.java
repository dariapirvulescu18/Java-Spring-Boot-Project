package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.model.EmployeeRoom;
import dpirvulescu.hotelManagement.model.EmployeeRoomId;
import dpirvulescu.hotelManagement.model.Room;
import dpirvulescu.hotelManagement.repository.JPAEmployeeRepository;
import dpirvulescu.hotelManagement.repository.JPAEmployeeRoomRepository;
import dpirvulescu.hotelManagement.repository.JPARoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeRoomServiceImpl {

    @Autowired
    private  JPAEmployeeRoomRepository employeeRoomRepository;
    @Autowired
    private  JPAEmployeeRepository employeeRepository;
    @Autowired
    private  JPARoomRepository roomRepository;



    @Transactional(propagation = Propagation.REQUIRED)
    public EmployeeRoom assignEmployeeToRoom(Integer employeeId, Integer roomId) {

        if (employeeRoomRepository.existsByEmployeeIdAndRoomId(employeeId, roomId)) {
            throw new IllegalStateException("Employee already assigned to this room");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        EmployeeRoom employeeRoom = new EmployeeRoom(employee, room);
        return employeeRoomRepository.save(employeeRoom);
    }

    @Transactional
    public void unassignEmployeeFromRoom(Integer employeeId, Integer roomId) {
        employeeRoomRepository.deleteById(
                new EmployeeRoomId(employeeId, roomId)
        );
    }

    public List<Room> getRoomsForEmployee(Integer employeeId) {
        return employeeRoomRepository.findByEmployeeId(employeeId)
                .stream()
                .map(EmployeeRoom::getRoom)
                .toList();
    }

    public List<Employee> getEmployeesForRoom(Integer roomId) {
        return employeeRoomRepository.findByRoomId(roomId)
                .stream()
                .map(EmployeeRoom::getEmployee)
                .toList();
    }


}
