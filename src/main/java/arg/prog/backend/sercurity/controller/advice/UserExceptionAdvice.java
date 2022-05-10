package arg.prog.backend.sercurity.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import arg.prog.backend.sercurity.Service.Exceptions.UserException;

@ControllerAdvice
public class UserExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userExcepHandler( UserException e){
        return e.getMessage();
    }
}
