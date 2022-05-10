package arg.prog.backend.sercurity.Service.Exceptions;

public class LoginException extends RuntimeException{
    public LoginException(int id, Throwable causa){
        super("No se puede ver el Perfil id:"+id+", devido a:",causa);
    }
    public LoginException(String nombre, Throwable causa){
        super("No se puede ver el Perfil nombre:"+nombre+", devido a:",causa);
    }
    public LoginException(String msg){
        super(msg);
    }
}
