package dpirvulescu.hotelManagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Schema(name = "Employee", description = "Table for employees")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the employee", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 100)
    @Schema(description = "Full name of the employee", example = "Jane Smith", required = true)
    private String name;

    @NotNull
    @Schema(description = "Role or position of the employee", example = "Receptionist", required = true)
    private String role;


    public Employee(Integer id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }


    public Employee() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
