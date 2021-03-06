package arg.prog.backend.DTO;

import javax.persistence.Basic;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class ExperienciaDTO {
    private int id;

    @NotBlank
    @NonNull
    private String institucion;
    @NotBlank
    private String puesto;
    private String descripcion;
    private String image;
    @Basic
    
    private String inicio;
    @Basic
    private String fin;

}
