package arg.prog.backend.Service.Exceptions;

public class SkillException extends RuntimeException{
    public SkillException(String msg,Throwable causa){
        super("Hubo un error en :"+msg+" devido",causa);
    }
    public SkillException(int id, Throwable causa){
        super("No se puede ver el proyecto id:"+id+", devido a:",causa);
    }
    public SkillException(String msg){
        super(msg);
    }
}