package cs544_2020_01_light_attendanceproject.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("Could not find user " + username);
    }
}
