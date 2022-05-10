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
    @Temporal(TemporalType.DATE)
    private Date inicio;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date fin;
}
