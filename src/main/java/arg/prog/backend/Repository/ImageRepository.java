package arg.prog.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arg.prog.backend.Entity.Imagenes;
import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Enum.ImageType;

@Repository
public interface ImageRepository extends JpaRepository<Imagenes,Long>{
    Optional <List<Imagenes>> findByPerfilAndType(Perfil perfil,ImageType type);
}
