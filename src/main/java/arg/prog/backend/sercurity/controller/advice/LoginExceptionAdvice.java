package arg.prog.backend.sercurity.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import arg.prog.backend.sercurity.Service.Exceptions.LoginException;

@ControllerAdvice
public class LoginExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String logExpHandler(LoginException e){
        return e.getMessage();
    }
}
