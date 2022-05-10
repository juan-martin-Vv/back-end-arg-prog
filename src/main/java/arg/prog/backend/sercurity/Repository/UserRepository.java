package arg.prog.backend.sercurity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arg.prog.backend.sercurity.Entity.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);//nombreUsuario

    Optional<Usuario> findByEmail(String email);

    boolean existsByNombreUsuario(String nomb_user);

    boolean existsByEmail(String mail);

}
