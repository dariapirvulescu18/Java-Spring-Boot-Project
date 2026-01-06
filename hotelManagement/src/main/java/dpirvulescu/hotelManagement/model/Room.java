    package dpirvulescu.hotelManagement.model;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;

    @Entity
    public class Room {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(name = "number")
        @NotBlank
        private String number;
        @Positive
        private Double price;
        @NotNull
        @Min(1)
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

