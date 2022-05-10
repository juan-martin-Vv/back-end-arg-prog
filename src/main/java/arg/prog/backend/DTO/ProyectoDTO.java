package arg.prog.backend.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ProyectoDTO {
    private int id;
    @NotBlank
    @NonNull
    private String nombre;

    @NotBlank
    @NonNull
    private String lenguaje;

    @NotBlank
    @NonNull
    private String descripcion;

    @NotBlank
    private String image;

}
