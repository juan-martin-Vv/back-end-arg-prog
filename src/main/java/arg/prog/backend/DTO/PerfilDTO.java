package arg.prog.backend.DTO;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import arg.prog.backend.Enum.PerfilType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerfilDTO {

    private int id;

    @NotBlank(message = "Por favor introdusca un Nombre valido")
    private String nombre;

    @NotBlank(message = "Por favor introdusca un Apellido valido")
    private String apellido;

    @Min(value = 4000000, message = "el DNI debe ser mayor a 4 millones")
    @EqualsAndHashCode.Include
    private long dni;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @NotBlank
    private String nacionalidad;

    @Email(message = "por favor introdusca una direccion de mail valida")
    private String email;

    private String descripcion;
    private String profesionalPerfil;
    private List<String> image_background_header;
    private List<String> image_perfil;
    @NotBlank
    @Enumerated(EnumType.STRING)
    private PerfilType type;
    ///
    private List<ExperienciaDTO> experiencia;

    private List<EducacionDTO> educacion;

    private List<ProyectoDTO> proyectos;

    private List<SkillDTO> skill;

    public PerfilDTO(@NotBlank String nombre, @NotBlank String apellido, @Min(4000000) long dni,
            Date fechaNacimiento, @NotBlank String nacionalidad, @Email String email, PerfilType type) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.email = email;
        this.type = type;
    }

    @Override
    public String toString() {
        return "PerfilDTO [ nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", email=" + email
                + ", fechaNacimiento="
                + fechaNacimiento + ", nacionalidad=" + nacionalidad + "]";
    }

}
