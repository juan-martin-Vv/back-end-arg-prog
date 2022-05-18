package arg.prog.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Entity.Skill;
@Repository
public interface SkillRepository extends JpaRepository<Skill,Long>{
    Optional<List<Skill>> findByPerfil(Perfil perfil);
    Optional<Skill> findBySkill(String skill);
    Optional<Skill> findBySkillAndPerfil(String skill,Perfil perfil);
}
