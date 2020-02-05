package cs544_2020_01_light_attendanceproject.exceptions;

public class AdminsCannotDeleteThemselvesException extends RuntimeException{
    public AdminsCannotDeleteThemselvesException(String username) { super(username + " you can't delete yourself."); }
}
