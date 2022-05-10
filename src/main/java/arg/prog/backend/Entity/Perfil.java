package arg.prog.backend.Entity;


import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import arg.prog.backend.Enum.PerfilType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    @Column(length = 25, nullable = false)
    private String nombre;

    @NotBlank
    @Column(length = 25, nullable = false)
    private String apellido;

    @Min(value = 4000000)
    @Column(unique = true, nullable = false)
    private long dni;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @NotBlank
    private String nacionalidad;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String descripcion;
    
    private String profesionalPerfil;
    // @ElementCollection
    // private List<String> image_background_header = new ArrayList<String>();
    
    // @ElementCollection
    // private List<String> image_perfil = new ArrayList<String>();

    private PerfilType type;

}
