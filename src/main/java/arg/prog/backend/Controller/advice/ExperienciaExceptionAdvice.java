package arg.prog.backend.Controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import arg.prog.backend.Service.Exceptions.ExperienciaException;

@ControllerAdvice
public class ExperienciaExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(ExperienciaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String expericiaExceptionHandler(ExperienciaException e) {
        return e.getMessage();
    }
}
