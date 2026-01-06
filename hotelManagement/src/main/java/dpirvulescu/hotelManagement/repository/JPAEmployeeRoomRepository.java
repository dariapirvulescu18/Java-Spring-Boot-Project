package dpirvulescu.hotelManagement.repository;

import dpirvulescu.hotelManagement.model.EmployeeRoom;
import dpirvulescu.hotelManagement.model.EmployeeRoomId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAEmployeeRoomRepository extends JpaRepository<EmployeeRoom, EmployeeRoomId> {

    List<EmployeeRoom> findByEmployeeId(Integer employeeId);

    List<EmployeeRoom> findByRoomId(Integer roomId);

    boolean existsByEmployeeIdAndRoomId(Integer employeeId, Integer roomId);
}
