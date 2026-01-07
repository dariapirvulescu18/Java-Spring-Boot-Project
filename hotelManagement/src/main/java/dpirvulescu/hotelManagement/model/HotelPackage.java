package dpirvulescu.hotelManagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "HotelPackage", description = "Represents a hotel package with optional services and quantity available")
@Entity
@Table(name = "\"HOTELPACKAGE\"")
public class HotelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the hotel package", example = "1")
    private Integer id;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "Name of the package", example = "Romantic Getaway")
    private String name;

    @Schema(description = "Indicates if breakfast is included in the package", example = "true")
    private Boolean breakfastIncluded;

    @Schema(description = "Indicates if spa access is included in the package", example = "true")
    private Boolean spaIncluded;

    @Schema(description = "Indicates if pool access is included in the package", example = "false")
    private Boolean poolIncluded;

    @Schema(description = "Number of packages available", example = "10")
    private Integer quantity;

    public HotelPackage(Integer id, String name, Boolean breakfastIncluded, Boolean spaIncluded, Boolean poolIncluded, Integer quantity) {
        this.id = id;
        this.name = name;
        this.breakfastIncluded = breakfastIncluded;
        this.spaIncluded = spaIncluded;
        this.poolIncluded = poolIncluded;
        this.quantity = quantity;
    }
    public HotelPackage() {}

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getBreakfastIncluded() {
        return breakfastIncluded;
    }

    public Boolean getSpaIncluded() {
        return spaIncluded;
    }

    public Boolean getPoolIncluded() {
        return poolIncluded;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreakfastIncluded(Boolean breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded;
    }

    public void setSpaIncluded(Boolean spaIncluded) {
        this.spaIncluded = spaIncluded;
    }

    public void setPoolIncluded(Boolean poolIncluded) {
        this.poolIncluded = poolIncluded;
    }
}
