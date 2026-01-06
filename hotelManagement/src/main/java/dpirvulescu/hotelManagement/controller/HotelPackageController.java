package dpirvulescu.hotelManagement.controller;

import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.service.HotelPackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/packages")
public class HotelPackageController {
    @Autowired
    private  HotelPackageService packageService;



    @GetMapping("/get/all")
    public List<HotelPackage> getAllPackages() {
        return packageService.getAllPackages();
    }


    @GetMapping("/get")
    public ResponseEntity<HotelPackage> getPackageById(@RequestParam(value = "id")  Integer id) {
        Optional<HotelPackage> hotelPackage = packageService.getPackageById(id);
        return hotelPackage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/create")
    public HotelPackage createPackage(@Valid @RequestBody HotelPackage hotelPackage) {
        return packageService.createPackage(hotelPackage);
    }


    @PutMapping("/update")
    public ResponseEntity<HotelPackage> updatePackage(@RequestParam(value = "id") Integer id, @Valid @RequestBody HotelPackage packageDetails) {
        Optional<HotelPackage> updatedPackage = packageService.updatePackage(id, packageDetails);
        return updatedPackage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePackage(@RequestParam(value = "id")  Integer id) {
        boolean deleted = packageService.deletePackage(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
