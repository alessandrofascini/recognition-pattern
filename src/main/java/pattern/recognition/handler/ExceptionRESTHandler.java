package pattern.recognition.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pattern.recognition.exceptions.ExistingPointException;
import pattern.recognition.exceptions.InvalidCoordinatesException;

/*
* Exception Handler
* */
@RestControllerAdvice
public class ExceptionRESTHandler {
    @ExceptionHandler(ExistingPointException.class)
    public String handleExistingPointException(ExistingPointException exception) {
        return exception.getLocalizedMessage();
    }

    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormatException(NumberFormatException exception) {
        return exception.getLocalizedMessage();
    }

    @ExceptionHandler(InvalidCoordinatesException.class)
    public String handleNumberFormatException(InvalidCoordinatesException exception) {
        return exception.getLocalizedMessage();
    }
}
