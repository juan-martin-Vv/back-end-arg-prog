package arg.prog.backend.Entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import arg.prog.backend.Enum.ImageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Entity
@Data
@NoArgsConstructor
public class Imagenes {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String imagenSrc;
    @NonNull
    @Enumerated(EnumType.STRING)
    private ImageType type;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
    public Imagenes(String imagenSrc, @NonNull ImageType type, Perfil perfil) {
        this.imagenSrc = imagenSrc;
        this.type = type;
        this.perfil = perfil;
    }
    
    
}

