package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.model.HotelPackage;

import java.util.List;
import java.util.Optional;

public interface HotelPackageService {

    List<HotelPackage> getAllPackages();

    Optional<HotelPackage> getPackageById(Integer id);

    HotelPackage createPackage(HotelPackage hotelPackage);

    Optional<HotelPackage> updatePackage(Integer id, HotelPackage hotelPackage);

    boolean deletePackage(Integer id);
}