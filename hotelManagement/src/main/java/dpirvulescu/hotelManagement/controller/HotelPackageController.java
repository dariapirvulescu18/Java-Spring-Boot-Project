package dpirvulescu.hotelManagement.controller;

import dpirvulescu.hotelManagement.model.HotelPackage;
import dpirvulescu.hotelManagement.service.HotelPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Hotel Package Controller",
        description = "APIs for managing hotel packages"
)
@RestController
@RequestMapping("/rest/packages")
public class HotelPackageController {
    @Autowired
    private  HotelPackageService packageService;


    @Operation(
            summary = "Get all hotel packages",
            description = "Returns a list of all available hotel packages"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Packages retrieved successfully")
    })
    @GetMapping("/get/all")
    public List<HotelPackage> getAllPackages() {
        return packageService.getAllPackages();
    }


    @Operation(
            summary = "Get hotel package by ID",
            description = "Returns a hotel package identified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Package found"),
            @ApiResponse(responseCode = "404", description = "Package not found")
    })
    @GetMapping("/get")
    public ResponseEntity<HotelPackage> getPackageById(@RequestParam(value = "id")  Integer id) {
        Optional<HotelPackage> hotelPackage = packageService.getPackageById(id);
        return hotelPackage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new hotel package",
            description = "Creates a new hotel package with included services"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Package created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping("/create")
    public HotelPackage createPackage(@Valid @RequestBody HotelPackage hotelPackage) {
        return packageService.createPackage(hotelPackage);
    }

    @Operation(
            summary = "Update hotel package",
            description = "Updates an existing hotel package identified by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Package updated successfully"),
            @ApiResponse(responseCode = "404", description = "Package not found"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @Parameter(description = "Id of the hotel package", example = "1")
    @PutMapping("/update")
    public ResponseEntity<HotelPackage> updatePackage(@RequestParam(value = "id") Integer id, @Valid @RequestBody HotelPackage packageDetails) {
        Optional<HotelPackage> updatedPackage = packageService.updatePackage(id, packageDetails);
        return updatedPackage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete hotel package",
            description = "Deletes a hotel package by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Package deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Package not found")
    })
    @Parameter(description = "Id of the hotel package", example = "1")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePackage(@RequestParam(value = "id")  Integer id) {
        boolean deleted = packageService.deletePackage(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
