package ir.maktab.exceptions;

public class WrongUserEmailException extends RuntimeException {

    public WrongUserEmailException(String message) {
        super(message);
    }
}
