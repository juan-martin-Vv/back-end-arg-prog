package arg.prog.backend.Service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import arg.prog.backend.DTO.ExperienciaDTO;
import arg.prog.backend.Entity.Experiencia;
import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Repository.ExperienciaRepository;
import arg.prog.backend.Repository.PerfilRepository;
import arg.prog.backend.Service.Exceptions.ErrorSQL;
import arg.prog.backend.Service.Exceptions.ExperienciaException;
import arg.prog.backend.Service.Exceptions.PerfilException;

@Service
@Validated
public class ServExperiencia {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ExperienciaRepository eRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    ///
    public List<Experiencia> getAllByPerfil(Perfil perfil) {
        return eRepository.findByPerfil(perfil).get();
    }

    ////
    public ExperienciaDTO saveDto(ExperienciaDTO eDto, Long dni) throws PerfilException, ErrorSQL {
        Experiencia experiencia = new Experiencia();
        Perfil perfil = new Perfil();
        //
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException(dni.toString() + " no existe");
        }
        perfil = perfilRepository.findByDni(dni).get();
        if (eRepository.findByInstitucionAndPuestoAndPerfil(eDto.getInstitucion(), eDto.getPuesto(), perfil)
                .isPresent()) {

            throw new ExperienciaException(eDto.getPuesto() + " en " + eDto.getInstitucion() + " ya exite");
        }
        experiencia = modelMapper.map(eDto, Experiencia.class);
        experiencia.setPerfil(perfil);
        try {
            eRepository.save(experiencia);
        } catch (Exception e) {
            throw new ErrorSQL(e.getMessage());
        }
        return modelMapper.map(experiencia, ExperienciaDTO.class);
    }

    ////
    public ExperienciaDTO getDTO(Integer id) {
        if (!eRepository.findById(id).isPresent())
            throw new ExperienciaException(id.toString() + " no existe");
        Experiencia experiencia = eRepository.getById(id);
        return modelMapper.map(experiencia, ExperienciaDTO.class);
    }

    ////
    ///
    public List<ExperienciaDTO> getAllByDNI(Long dni) throws PerfilException {
        List<ExperienciaDTO> listDTO = new ArrayList<ExperienciaDTO>();
        List<Experiencia> list = new ArrayList<Experiencia>();
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException("dni :" + dni.toString());
        }
        Perfil perfil = perfilRepository.findByDni(dni).get();
        list = eRepository.findByPerfil(modelMapper.map(perfil, Perfil.class)).get();
        for (Experiencia proyecto : list) {
            listDTO.add(modelMapper.map(proyecto, ExperienciaDTO.class));
        }
        return listDTO;
    }

    ///
    public ExperienciaDTO delete(Integer id) throws ExperienciaException {
        if (!eRepository.findById(id).isPresent())
            throw new ExperienciaException(id.toString() + " no existe");
        Experiencia proyecto = eRepository.getById(id);
        eRepository.delete(proyecto);
        ExperienciaDTO proyectoDTO = modelMapper.map(proyecto, ExperienciaDTO.class);
        return proyectoDTO;
    }

    public ExperienciaDTO update(ExperienciaDTO pDto) throws ExperienciaException {
        Experiencia proyecto = new Experiencia();
        Perfil perfil;
        if (!eRepository.findById(pDto.getId()).isPresent())
            throw new ExperienciaException(pDto.getId() + " no existe");
        proyecto = eRepository.getById(pDto.getId());
        perfil = proyecto.getPerfil();
        proyecto = modelMapper.map(pDto, Experiencia.class);// pRepository.getById(pDto.getId());
        proyecto.setPerfil(perfil);
        eRepository.save(proyecto);
        ExperienciaDTO proyectoDTO = modelMapper.map(proyecto, ExperienciaDTO.class);
        return proyectoDTO;
    }

    public List<ExperienciaDTO> getAll() {
        List<ExperienciaDTO> listDTO = new ArrayList<ExperienciaDTO>();
        List<Experiencia> list = new ArrayList<Experiencia>();
        list = eRepository.findAll();
        for (Experiencia proyecto : list) {
            listDTO.add(modelMapper.map(proyecto, ExperienciaDTO.class));
        }
        return listDTO;
    }
}
