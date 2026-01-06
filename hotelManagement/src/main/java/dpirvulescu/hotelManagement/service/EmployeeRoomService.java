package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.model.Employee;
import dpirvulescu.hotelManagement.model.EmployeeRoom;
import dpirvulescu.hotelManagement.model.EmployeeRoomId;
import dpirvulescu.hotelManagement.model.Room;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRoomService {

    EmployeeRoom assignEmployeeToRoom(Integer employeeId, Integer roomId);

    void unassignEmployeeFromRoom(Integer employeeId, Integer roomId);

    List<Room> getRoomsForEmployee(Integer employeeId);

    List<Employee> getEmployeesForRoom(Integer roomId);


}
