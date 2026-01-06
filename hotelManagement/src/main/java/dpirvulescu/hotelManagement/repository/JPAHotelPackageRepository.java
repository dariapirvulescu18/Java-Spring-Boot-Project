package dpirvulescu.hotelManagement.repository;

import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAHotelPackageRepository extends JpaRepository<HotelPackage, Integer> {

}
