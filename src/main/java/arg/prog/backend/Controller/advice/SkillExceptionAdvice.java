package arg.prog.backend.Controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import arg.prog.backend.Service.Exceptions.SkillException;

@ControllerAdvice
public class SkillExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(SkillException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String skillExceptionHandler(SkillException e){
        return e.getMessage();
    }
}
