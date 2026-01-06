package dpirvulescu.hotelManagement.repository;

import dpirvulescu.hotelManagement.model.Customer;
import dpirvulescu.hotelManagement.model.HotelPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPACustomerRepository extends JpaRepository<Customer, Integer> {
    public List<Customer> findByNameContainingIgnoreCase(String name);
}
