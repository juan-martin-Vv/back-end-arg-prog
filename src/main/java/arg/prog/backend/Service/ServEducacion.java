package arg.prog.backend.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arg.prog.backend.DTO.EducacionDTO;
import arg.prog.backend.Entity.Educacion;
import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Repository.EducacionRepository;
import arg.prog.backend.Repository.PerfilRepository;
import arg.prog.backend.Service.Exceptions.EducacionException;
import arg.prog.backend.Service.Exceptions.PerfilException;

@Service
@Transactional
public class ServEducacion {
    @Autowired
    private EducacionRepository educacionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PerfilRepository perfilRepository;

    // @Autowired
    // private ServPerfil servPerfil;
    /// devuelve todas las educ segun el perfil pasado
    public List<Educacion> getAllByPerfil(Perfil perfil) {
        return educacionRepository.findByPerfil(perfil).get();
    }

    ///
    ///
    public List<EducacionDTO> getAllByDNI(Long dni) throws PerfilException {
        List<EducacionDTO> listDTO = new ArrayList<EducacionDTO>();
        List<Educacion> list = new ArrayList<Educacion>();
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException("dni :" + dni.toString());
        }
        Perfil perfil = perfilRepository.findByDni(dni).get();
        list = educacionRepository.findByPerfil(modelMapper.map(perfil, Perfil.class)).get();
        for (Educacion proyecto : list) {
            listDTO.add(modelMapper.map(proyecto, EducacionDTO.class));
        }
        return listDTO;
    }

    ///
    public EducacionDTO saveDTO(EducacionDTO eDto, Long dni) throws EducacionException, PerfilException {
        Educacion educacion = new Educacion();

        Perfil perfil = new Perfil();
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException(dni.toString() + " no existe");
        }
        perfil = perfilRepository.findByDni(dni).get();
        if (educacionRepository.findByInstitucionAndTituloAndPerfil(eDto.getInstitucion(), eDto.getTitulo(), perfil)
                .isPresent()) {

            throw new EducacionException(eDto.getTitulo() + " ya exite");
        }
        educacion = modelMapper.map(eDto, Educacion.class);
        educacion.setPerfil(perfil);
        educacionRepository.save(educacion);
        return modelMapper.map(educacion, EducacionDTO.class);
    }

    ///
    public EducacionDTO getDTO(Integer id) {
        if (!educacionRepository.findById(id).isPresent())
            throw new EducacionException(id.toString() + " no existe");
        Educacion educacion = educacionRepository.getById(id);
        return modelMapper.map(educacion, EducacionDTO.class);
    }

    public EducacionDTO delete(Integer id) throws EducacionException {
        if (!educacionRepository.findById(id).isPresent())
            throw new EducacionException(id.toString() + " no existe");
        Educacion proyecto = educacionRepository.getById(id);
        educacionRepository.delete(proyecto);
        EducacionDTO proyectoDTO = modelMapper.map(proyecto, EducacionDTO.class);
        return proyectoDTO;
    }

    public EducacionDTO update(EducacionDTO pDto) throws EducacionException {
        Educacion proyecto = new Educacion();
        Perfil perfil;
        if (!educacionRepository.findById(pDto.getId()).isPresent())
            throw new EducacionException(pDto.getId() + " no existe");
        proyecto = educacionRepository.getById(pDto.getId());
        perfil = proyecto.getPerfil();
        proyecto = modelMapper.map(pDto, Educacion.class);// pRepository.getById(pDto.getId());
        proyecto.setPerfil(perfil);
        educacionRepository.save(proyecto);
        EducacionDTO proyectoDTO = modelMapper.map(proyecto, EducacionDTO.class);
        return proyectoDTO;
    }

    public List<EducacionDTO> getAll() {
        List<EducacionDTO> listDTO = new ArrayList<EducacionDTO>();
        List<Educacion> list = new ArrayList<Educacion>();

        list = educacionRepository.findAll();
        for (Educacion proyecto : list) {
            listDTO.add(modelMapper.map(proyecto, EducacionDTO.class));
        }
        return listDTO;
    }
}
