package cs544_2020_01_light_attendanceproject.exceptions;

public class ObjectNotFoundException extends RuntimeException {
	public ObjectNotFoundException(String message) {
		super("An error occurs " + message);
	}
}
