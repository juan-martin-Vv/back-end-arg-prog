package arg.prog.backend.Service.Exceptions;

public class ProyectoException extends RuntimeException{
    public ProyectoException(String msg,Throwable causa){
        super("Hubo un error en :"+msg+" devido",causa);
    }
    public ProyectoException(int id, Throwable causa){
        super("No se puede ver el proyecto id:"+id+", devido a:",causa);
    }
    public ProyectoException(String msg){
        super(msg);
    }
}
