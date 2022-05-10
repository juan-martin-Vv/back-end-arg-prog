package arg.prog.backend.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arg.prog.backend.DTO.ProyectoDTO;
import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Entity.Proyecto;
import arg.prog.backend.Repository.PerfilRepository;
import arg.prog.backend.Repository.ProyectoRepository;
import arg.prog.backend.Service.Exceptions.PerfilException;
import arg.prog.backend.Service.Exceptions.ProyectoException;

@Service
@Transactional
public class ServProyecto {
    @Autowired
    private ProyectoRepository pRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PerfilRepository perfilRepository;

    ///
    public List<Proyecto> getAllByPerfil(Perfil perfil) {
        return pRepository.findByPerfil(perfil).get();
    }

    ///
    public List<ProyectoDTO> getAllByDNI(Long dni) throws PerfilException {
        List<ProyectoDTO> listDTO = new ArrayList<ProyectoDTO>();
        List<Proyecto> list = new ArrayList<Proyecto>();
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException("dni :" + dni.toString());
        }
        Perfil perfil = perfilRepository.findByDni(dni).get();
        list = pRepository.findByPerfil(modelMapper.map(perfil, Perfil.class)).get();
        for (Proyecto proyecto : list) {
            listDTO.add(modelMapper.map(proyecto, ProyectoDTO.class));
        }
        return listDTO;
    }

    ///
    public ProyectoDTO saveDTO(ProyectoDTO pDto, Long dni) throws ProyectoException, PerfilException {
        Proyecto proyecto = new Proyecto();

        Perfil perfil = new Perfil();
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException(dni.toString() + " no existe");
        }
        perfil = perfilRepository.findByDni(dni).get();
        if (pRepository.findByNombreAndLenguajeAndPerfil(
                pDto.getNombre(),
                pDto.getLenguaje(),
                perfil).isPresent()) {

            throw new ProyectoException(pDto.getNombre() + " ya exite");
        }
        proyecto = modelMapper.map(pDto, Proyecto.class);
        proyecto.setPerfil(perfil);
        pRepository.save(proyecto);
        return modelMapper.map(proyecto, ProyectoDTO.class);
    }

    ///
    public ProyectoDTO getDTO(Integer id) {
        if (!pRepository.findById(id).isPresent())
            throw new ProyectoException(id.toString() + " no existe");
        Proyecto proyecto = pRepository.getById(id);
        ProyectoDTO proyectoDTO = modelMapper.map(proyecto, ProyectoDTO.class);
        return proyectoDTO;
    }

    ///
    public ProyectoDTO delete(Integer id) {
        if (!pRepository.findById(id).isPresent())
            throw new ProyectoException(id.toString() + " no existe");
        Proyecto proyecto = pRepository.getById(id);
        pRepository.delete(proyecto);
        ProyectoDTO proyectoDTO = modelMapper.map(proyecto, ProyectoDTO.class);
        return proyectoDTO;
    }

    public ProyectoDTO update(ProyectoDTO pDto) {
        Proyecto proyecto = new Proyecto();
        Perfil perfil;
        if (!pRepository.findById(pDto.getId()).isPresent())
            throw new ProyectoException(pDto.getId() + " no existe");
        proyecto = pRepository.getById(pDto.getId());
        perfil = proyecto.getPerfil();
        proyecto = modelMapper.map(pDto, Proyecto.class);// pRepository.getById(pDto.getId());
        proyecto.setPerfil(perfil);
        pRepository.save(proyecto);
        ProyectoDTO proyectoDTO = modelMapper.map(proyecto, ProyectoDTO.class);
        return proyectoDTO;
    }

    public List<ProyectoDTO> getAll() {
        List<ProyectoDTO> listDTO = new ArrayList<ProyectoDTO>();
        List<Proyecto> list = new ArrayList<Proyecto>();

        list = pRepository.findAll();
        for (Proyecto proyecto : list) {
            listDTO.add(modelMapper.map(proyecto, ProyectoDTO.class));
        }
        return listDTO;
    }
}
