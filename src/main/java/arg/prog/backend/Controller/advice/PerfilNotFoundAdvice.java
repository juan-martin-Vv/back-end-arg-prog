package arg.prog.backend.Controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


import arg.prog.backend.Service.Exceptions.PerfilException;

@ControllerAdvice
public class PerfilNotFoundAdvice {
    
        @ResponseBody
        @ExceptionHandler(PerfilException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String perfilNotFoundHandler(PerfilException ex) {
            return ex.getMessage();
    }
}
