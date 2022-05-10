package arg.prog.backend.sercurity.DTO;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String nombre;
    private String nombreUsuario;
    private String password;
    private String email;
    private Set<String> roles= new HashSet<>();

}
