package arg.prog.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Entity.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    Optional<List<Proyecto>> findByPerfil(Perfil perfil);

    Optional<List<Proyecto>> findByLenguaje(String lenguaje);

    Optional<Proyecto> findByNombreAndLenguajeAndPerfil(String nombre, String lenguaje, Perfil perfil);
}
