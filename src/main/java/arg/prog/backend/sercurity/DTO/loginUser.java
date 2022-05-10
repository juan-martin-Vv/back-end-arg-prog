package arg.prog.backend.sercurity.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class loginUser {
    @NotNull
    private String nombreUsuario;
    @NotNull
    private String password;
}
