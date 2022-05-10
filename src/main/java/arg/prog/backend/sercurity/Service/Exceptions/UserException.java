package arg.prog.backend.sercurity.Service.Exceptions;

public class UserException extends RuntimeException{
    public UserException(int id, Throwable causa){
        super("No se puede ver el Perfil id:"+id+", devido a:",causa);
    }
    public UserException(String nombre, Throwable causa){
        super("No se puede ver el Perfil nombre:"+nombre+", devido a:",causa);
    }
    public UserException(String msg){
        super(msg);
    }
}
