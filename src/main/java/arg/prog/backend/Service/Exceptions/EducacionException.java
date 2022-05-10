package arg.prog.backend.Service.Exceptions;

public class EducacionException extends RuntimeException{
    public EducacionException(Throwable causa){
        super("Hubo un error en :",causa);
    }
    public EducacionException(int id, Throwable causa){
        super("No se puede ver el Educacion id:"+id+", devido a:",causa);
    }
    public EducacionException(String msg){
        super(msg);
    }
}
