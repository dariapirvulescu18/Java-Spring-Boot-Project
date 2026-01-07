package dpirvulescu.hotelManagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
@Schema(name = "Customer", description = "Table for customers")
@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the customer", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Full name of the customer", example = "John Doe", required = true)
    private String name;

    @Column(unique = true, length = 150)
    @Schema(description = "Email address of the customer, must be unique", example = "john.doe@example.com", required = true)
    private String email;

    @Pattern(
            regexp = "^(\\+407|07)\\d{8}$",
            message = "Phone number must be a valid Romanian number"
    )
    @Schema(description = "Phone number of the customer in Romanian format", example = "+40712345678", required = true)
    @Column(name = "phone_number", unique = true, nullable = false, length = 13)
    private String phoneNumber;

    public Customer() {
    }

    public Customer(Integer id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
