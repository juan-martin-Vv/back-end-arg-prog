package arg.prog.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Enum.PerfilType;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    Optional<Perfil> findByNombre(String nombre);

    Optional<Perfil> findByDni(Long dni);

    Optional<Perfil> findByEmail(String email);

    Optional<Perfil> findByDniOrEmail(Long dni, String email);
    
    Optional<Perfil> findByType(PerfilType type);

}
