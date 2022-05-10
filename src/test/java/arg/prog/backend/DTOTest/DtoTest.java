package arg.prog.backend.DTOTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Date;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import arg.prog.backend.DTO.PerfilDTO;
import arg.prog.backend.Enum.PerfilType;

@SpringBootTest
public class DtoTest {

    @Autowired
    private Validator validator;
    @Test
    public void testPerfil() {

       

        PerfilDTO perfilDTO = new PerfilDTO("nombre", "", 20L, new Date(), "nacionalidad", "email",PerfilType.principal);
        Set<ConstraintViolation<PerfilDTO>>  violations = validator.validate(perfilDTO);
        

        
        System.out.println(violations);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<PerfilDTO> constraintViolation : violations) {
                //sb.append(constraintViolation.getMessage());
                sb.append(constraintViolation.getPropertyPath()+" :"+constraintViolation.getMessage()+"\n");
                //System.out.println(constraintViolation.getMessage());
            }
            
            System.out.println(sb);
        }

        
        assertEquals(perfilDTO.getNombre(), "nombre");
    }
}
