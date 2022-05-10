package arg.prog.backend.Service.Exceptions;

public class PerfilException extends RuntimeException{
    public PerfilException(int id, Throwable causa){
        super("No se puede ver el Perfil id:"+id+", devido a:",causa);
    }
    public PerfilException(String nombre, Throwable causa){
        super("No se puede ver el Perfil nombre:"+nombre+", devido a:",causa);
    }
    public PerfilException(String msg){
        super(msg);
    }
}
