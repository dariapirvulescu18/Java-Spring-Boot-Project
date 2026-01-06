package dpirvulescu.hotelManagement.service;

import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.repository.JPAHotelPackageRepository;
import dpirvulescu.hotelManagement.service.HotelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelPackageServiceImpl implements HotelPackageService {
    @Autowired
    private  JPAHotelPackageRepository packageRepository;


    @Override
    public List<HotelPackage> getAllPackages() {
        return packageRepository.findAll();
    }

    @Override
    public Optional<HotelPackage> getPackageById(Integer id) {
        return packageRepository.findById(id);
    }

    @Override
    public HotelPackage createPackage(HotelPackage hotelPackage) {
        return packageRepository.save(hotelPackage);
    }

    @Override
    public Optional<HotelPackage> updatePackage(Integer id, HotelPackage hotelPackageDetails) {
        return packageRepository.findById(id).map(hotelPackage -> {
            hotelPackage.setName(hotelPackageDetails.getName());
            hotelPackage.setBreakfastIncluded(hotelPackageDetails.getBreakfastIncluded());
            hotelPackage.setSpaIncluded(hotelPackageDetails.getSpaIncluded());
            hotelPackage.setPoolIncluded(hotelPackageDetails.getPoolIncluded());
            return packageRepository.save(hotelPackage);
        });
    }

    @Override
    public boolean deletePackage(Integer id) {
        if (!packageRepository.existsById(id)) {
            return false;
        }
        packageRepository.deleteById(id);
        return true;
    }
}
