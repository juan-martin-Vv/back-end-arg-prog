package arg.prog.backend.Service.Exceptions;

public class ExperienciaException extends RuntimeException{
    public ExperienciaException(Throwable causa){
        super("Hubo un error en :",causa);
    }
    public ExperienciaException(int id, Throwable causa){
        super("No se puede ver el Experiencia id:"+id+", devido a:",causa);
    }
    public ExperienciaException(String msg){
        super(msg);
    }
}
