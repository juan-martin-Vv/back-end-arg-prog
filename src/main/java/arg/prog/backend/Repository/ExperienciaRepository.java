package arg.prog.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arg.prog.backend.Entity.Experiencia;
import arg.prog.backend.Entity.Perfil;

@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Integer> {
    Optional<List<Experiencia>> findByPerfil(Perfil perfil);
    Optional<Experiencia> findByInstitucionAndPuestoAndPerfil(String inst, String puesto, Perfil perfil);
}
