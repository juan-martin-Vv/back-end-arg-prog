package arg.prog.backend.sercurity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arg.prog.backend.sercurity.Entity.Roll;
import arg.prog.backend.sercurity.Enums.RollEnum;

@Repository
public interface RollRepository extends JpaRepository<Roll, Integer> {

    Optional<Roll> findByRollName(RollEnum rollNombre);
}
