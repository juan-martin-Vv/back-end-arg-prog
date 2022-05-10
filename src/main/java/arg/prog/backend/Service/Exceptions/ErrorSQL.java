package arg.prog.backend.Service.Exceptions;

public class ErrorSQL extends RuntimeException{
    public ErrorSQL (String causa){
        super("error en SQL"+causa);
    }
    
}
