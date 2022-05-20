package arg.prog.backend.DTO;

import javax.persistence.Basic;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EducacionDTO {
    private int id;
    @NotBlank
    @NonNull
    private String institucion;
    @NotBlank
    @NonNull
    private String titulo;
    private String image;
    private String carrera;
    @NonNull
    
    @Basic
    private String inicio;
    
    @Basic
    private String fin;
}
