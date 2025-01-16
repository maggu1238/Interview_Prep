package Exceptions;

public class NoProblemFoundException  extends RuntimeException {
    public NoProblemFoundException(String message) {
        super(message);
    }
}