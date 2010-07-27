package exceptions;

public class DomainObjectValidatorException extends Exception{

    private final String _msg;

    public DomainObjectValidatorException(String msg) { _msg = msg; }

    @Override
    public String getMessage() {
        return _msg;
    }
}
