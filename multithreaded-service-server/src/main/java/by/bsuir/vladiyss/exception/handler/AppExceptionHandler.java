package by.bsuir.vladiyss.exception.handler;

import by.bsuir.vladiyss.dto.ErrorOutDto;
import by.bsuir.vladiyss.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorOutDto handleEntityNotFoundException(final EntityNotFoundException exception) {
        return new ErrorOutDto(exception.getMessage(), LocalDateTime.now());
    }
}
