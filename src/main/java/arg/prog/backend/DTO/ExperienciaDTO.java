package arg.prog.backend.DTO;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Temporal(TemporalType.DATE)
    private Date inicio;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date fin;

}
