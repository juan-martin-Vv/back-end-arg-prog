package arg.prog.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arg.prog.backend.Entity.Educacion;
import arg.prog.backend.Entity.Perfil;

@Repository
public interface EducacionRepository extends JpaRepository<Educacion, Integer> {
    Optional<List<Educacion>> findByPerfil(Perfil perfil);

    Optional<Educacion> findByInstitucionAndTituloAndPerfil(String inst, String titulo, Perfil perfil);
}
