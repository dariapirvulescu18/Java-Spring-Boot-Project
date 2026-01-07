    package dpirvulescu.hotelManagement.model;
    import io.swagger.v3.oas.annotations.media.Schema;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;

@Schema(name = "Room", description = "Represents a hotel room")
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the room", example = "101")
    private Integer id;

    @Column(name = "number")
    @NotBlank
    @Schema(description = "Room number", example = "101")
    private String number;

    @Positive
    @Schema(description = "Price per night for the room", example = "120.5")
    private Double price;

    @NotNull
    @Min(1)
    @Schema(description = "Maximum number of guests allowed in the room", example = "2")
    private Integer capacity;

    public Room(Integer id, String number, Double price, Integer capacity) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.capacity = capacity;

    }
    public Room() {
    }


    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getCapacity() {
        return capacity;
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }



}

