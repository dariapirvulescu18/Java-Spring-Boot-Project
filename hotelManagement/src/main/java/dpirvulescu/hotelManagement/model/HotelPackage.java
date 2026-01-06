package dpirvulescu.hotelManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "\"HOTELPACKAGE\"")
public class HotelPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    private Boolean breakfastIncluded;
    private Boolean spaIncluded;
    private Boolean poolIncluded;
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
