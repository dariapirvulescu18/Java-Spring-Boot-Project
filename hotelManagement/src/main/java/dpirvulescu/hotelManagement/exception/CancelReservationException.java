package dpirvulescu.hotelManagement.exception;

public class CancelReservationException extends RuntimeException {
    public CancelReservationException(String name) {
        super("Customer " + name + " cannot cancel reservation: check-in is less than 10 days from today");
    }
}
