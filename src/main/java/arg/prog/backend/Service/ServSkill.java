package arg.prog.backend.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import arg.prog.backend.DTO.SkillDTO;
import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Entity.Skill;
import arg.prog.backend.Repository.PerfilRepository;
import arg.prog.backend.Repository.SkillRepository;
import arg.prog.backend.Service.Exceptions.PerfilException;
import arg.prog.backend.Service.Exceptions.SkillException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ServSkill {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SkillRepository skillrepo;
    @Autowired
    private PerfilRepository perfilRepository;
    
    public List<Skill> getAllByPerfil(Perfil perfil) {
        return skillrepo.findByPerfil(perfil).get();
    }

    ///
    public List<SkillDTO> getAllByDNI(Long dni) throws PerfilException {
        List<SkillDTO> listDTO = new ArrayList<SkillDTO>();
        List<Skill> list = new ArrayList<Skill>();
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException("dni :" + dni.toString());
        }
        Perfil perfil = perfilRepository.findByDni(dni).get();
        list = skillrepo.findByPerfil(modelMapper.map(perfil, Perfil.class)).get();
        for (Skill Skill : list) {
            listDTO.add(modelMapper.map(Skill, SkillDTO.class));
        }
        return listDTO;
    }

    ///
    public SkillDTO saveDTO(SkillDTO pDto, Long dni) throws SkillException, PerfilException {
        Skill Skill = new Skill();

        Perfil perfil = new Perfil();
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException(dni.toString() + " no existe");
        }
        perfil = perfilRepository.findByDni(dni).get();
        if (skillrepo.findBySkillAndPerfil(
                pDto.getSkill(),perfil).isPresent()) {

            throw new SkillException(pDto.getSkill() + " ya exite");
        }
        Skill = modelMapper.map(pDto, Skill.class);
        Skill.setPerfil(perfil);
        skillrepo.save(Skill);
        return modelMapper.map(Skill, SkillDTO.class);
    }

    ///
    public SkillDTO getDTO(Long id) {
        if (!skillrepo.findById(id).isPresent())
            throw new SkillException(id.toString() + " no existe");
        Skill Skill = skillrepo.getById(id);
        SkillDTO SkillDTO = modelMapper.map(Skill, SkillDTO.class);
        return SkillDTO;
    }

    ///
    public SkillDTO delete(Long id) {
        if (!skillrepo.findById(id).isPresent())
            throw new SkillException(id.toString() + " no existe");
        Skill Skill = skillrepo.getById(id);
        skillrepo.delete(Skill);
        SkillDTO SkillDTO = modelMapper.map(Skill, SkillDTO.class);
        return SkillDTO;
    }

    public SkillDTO update(SkillDTO pDto) {
        Skill Skill = new Skill();
        Perfil perfil;
        if (!skillrepo.findById(pDto.getId()).isPresent())
            throw new SkillException(pDto.getId() + " no existe");
        Skill = skillrepo.getById(pDto.getId());
        perfil = Skill.getPerfil();
        Skill = modelMapper.map(pDto, Skill.class);// skillrepo.getById(pDto.getId());
        Skill.setPerfil(perfil);
        skillrepo.save(Skill);
        SkillDTO SkillDTO = modelMapper.map(Skill, SkillDTO.class);
        return SkillDTO;
    }

    public List<SkillDTO> getAll() {
        List<SkillDTO> listDTO = new ArrayList<SkillDTO>();
        List<Skill> list = new ArrayList<Skill>();

        list = skillrepo.findAll();
        for (Skill Skill : list) {
            listDTO.add(modelMapper.map(Skill, SkillDTO.class));
        }
        return listDTO;
    }
}
