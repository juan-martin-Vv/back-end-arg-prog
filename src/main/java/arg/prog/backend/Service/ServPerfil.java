package arg.prog.backend.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import arg.prog.backend.DTO.EducacionDTO;
import arg.prog.backend.DTO.ExperienciaDTO;
import arg.prog.backend.DTO.PerfilDTO;
import arg.prog.backend.DTO.ProyectoDTO;
import arg.prog.backend.DTO.SkillDTO;
import arg.prog.backend.Entity.Educacion;
import arg.prog.backend.Entity.Experiencia;
import arg.prog.backend.Entity.Imagenes;
import arg.prog.backend.Entity.Perfil;
import arg.prog.backend.Entity.Proyecto;
import arg.prog.backend.Entity.Skill;
import arg.prog.backend.Enum.ImageType;
import arg.prog.backend.Enum.PerfilType;
import arg.prog.backend.Repository.ImageRepository;
import arg.prog.backend.Repository.PerfilRepository;
import arg.prog.backend.Repository.SkillRepository;
import arg.prog.backend.Service.Exceptions.ErrorSQL;
import arg.prog.backend.Service.Exceptions.PerfilException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Validated
public class ServPerfil {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ServEducacion servEducacion;
    @Autowired
    private ServExperiencia servExperiencia;
    @Autowired
    private ServProyecto servProyecto;
    @Autowired
    private ImageRepository imgRepo;
    @Autowired
    private ServSkill servSkill;

    public PerfilDTO saveDTO(PerfilDTO perfilDTO) throws PerfilException, ErrorSQL {
        Perfil perfil = modelMapper.map(perfilDTO, Perfil.class);
        //////////
        // si el obj entrante quiere ser principal
        // verificamos si existe un perfil principal si lo hay se lo 
        // retipifica como 'storage' 
        if (perfil.getType()==PerfilType.principal) {
            if (perfilRepository.findByType(PerfilType.principal).isPresent()) {
                Perfil temp= perfilRepository.findByType(PerfilType.principal).get();
                temp.setType(PerfilType.storage);
                perfilRepository.save(temp);
            }
        } 
        List<Imagenes> temp = new ArrayList<Imagenes>();
        List<Imagenes> back = new ArrayList<Imagenes>();
        List<String> back_ground = perfilDTO.getImage_background_header();
        List<String> perfil_img = perfilDTO.getImage_perfil();
        log.info("pasondo por save");
        //log.info("pasondo por save perfil_img:{}",perfil_img);
        //log.info("pasondo por save back ground:{}",back_ground);
        //
        if (perfilRepository.findByDniOrEmail(perfil.getDni(), perfil.getEmail()).isPresent()) {
            throw new PerfilException("ya existe :" + perfil.getDni() + " " + perfil.getEmail());
        }
        try {
            perfil = perfilRepository.save(perfil);
            // se hace esto para no tener ocupado a la base de datos
            // if (imgRepo.findByPerfilAndType(perfil, ImageType.BackGround).isEmpty()) {
            //     temp = imgRepo.findByPerfilAndType(perfil, ImageType.BackGround).get();
            //     log.info("no bacio");
            //     for (Imagenes t : temp) {
            //         if (back_ground.indexOf(t.getImagenSrc()) != -1) { // buscamos objetos repetidos
            //             back_ground.remove(back_ground.indexOf(t.getImagenSrc())); // si lo hay se remueven
            //         }
            //     }
            // }
            if (back_ground != null)
            {
                back=new ArrayList<Imagenes>();
                for (String back_img : back_ground) { // guerdamos las imagenes una por una
                    back.add(new Imagenes(back_img,ImageType.BackGround,perfil));
                }
                imgRepo.saveAll(back);
            }
            // if (imgRepo.findByPerfilAndType(perfil, ImageType.Face).isEmpty()) {
            //     temp = imgRepo.findByPerfilAndType(perfil, ImageType.Face).get();
            //     for (Imagenes t : temp) {
            //         if (perfil_img.indexOf(t.getImagenSrc()) != -1) { // buscamos objetos repetidos
            //             perfil_img.remove(perfil_img.indexOf(t.getImagenSrc())); // si lo hay se remueven
            //         }
            //     }
            // }
            if (perfil_img != null)
            {
                back=new ArrayList<Imagenes>();
                for (String back_img : perfil_img) { // guerdamos las imagenes una por una
                    back.add(new Imagenes(back_img,ImageType.Face,perfil));
                }
                imgRepo.saveAll(back);
            }

        } catch (Exception e) {
            throw new ErrorSQL(e.getMessage());
        }
        log.info("guardo :{}", perfil.getDni());
        perfilDTO = modelMapper.map(perfil, PerfilDTO.class);
        List<String> tt=new ArrayList<String>();
        for (Imagenes ti :imgRepo.findByPerfilAndType(perfil, ImageType.BackGround).get()) {
            tt.add(ti.getImagenSrc());
        }
        perfilDTO.setImage_background_header(tt);
        tt=new ArrayList<String>();
        for (Imagenes ti :imgRepo.findByPerfilAndType(perfil, ImageType.Face).get()) {
            tt.add(ti.getImagenSrc());
        }
        perfilDTO.setImage_perfil(tt);
        return perfilDTO;
    }

    public Perfil getByDni(Long dni) throws PerfilException {
        if (!perfilRepository.findByDni(dni).isPresent())
            throw new PerfilException("dni :" + dni.toString());

        return perfilRepository.findByDni(dni).get();
    }

    public PerfilDTO getDTO(String nombre) throws PerfilException {
        PerfilDTO perfilDTO = new PerfilDTO();
        List<String> temp = new ArrayList<String>();
        try {
            Perfil perfil = perfilRepository.findByNombre(nombre).get();
            perfilDTO = modelMapper.map(perfil, PerfilDTO.class);
            if (!imgRepo.findByPerfilAndType(perfil, ImageType.BackGround).isEmpty()) {
                for (Imagenes t : imgRepo.findByPerfilAndType(perfil, ImageType.BackGround).get()) {
                    temp.add(t.getImagenSrc());
                    // log.info("get back:{}",t.getImagenSrc());
                }
            }
            perfilDTO.setImage_background_header(temp);
            temp=new ArrayList<String>();
            if (!imgRepo.findByPerfilAndType(perfil, ImageType.Face).isEmpty()) {
                for (Imagenes t : imgRepo.findByPerfilAndType(perfil, ImageType.Face).get()) {
                    temp.add(t.getImagenSrc());
                    // log.info("get back:{}",t.getImagenSrc());
                }
            }
            perfilDTO.setImage_perfil(temp);
        } catch (Exception e) {
            throw new PerfilException(nombre, e);
        }

        // PerfilDTO perfilDTO=modelMapper.map(perfil, PerfilDTO.class);
        return perfilDTO;
    }

    public PerfilDTO getFullDTO(Long dni) throws PerfilException {
        //
        List<Educacion> educacions;
        List<Experiencia> experiencias;
        List<Proyecto> proyectos;
        List<Skill> skills;
        //
        List<EducacionDTO> educacionDTOs = new ArrayList<EducacionDTO>();
        List<ExperienciaDTO> expDTOs = new ArrayList<ExperienciaDTO>();
        List<ProyectoDTO> proyDTOs = new ArrayList<ProyectoDTO>();
        List<SkillDTO> skillDTOs = new ArrayList<SkillDTO>();
        PerfilDTO perfilDTO = new PerfilDTO();
        //
        Perfil perfil;
        //
        if (!perfilRepository.findByDni(dni).isPresent()) {
            throw new PerfilException(dni.toString());
        }
        try {

            perfil = perfilRepository.findByDni(dni).get();
            perfilDTO = modelMapper.map(perfil, PerfilDTO.class);
            perfilDTO = getDTO(perfilDTO.getNombre());

        } catch (Exception e) {
            throw new PerfilException(dni.toString(), e);
        }
        try {
            educacions = servEducacion.getAllByPerfil(perfil);
            experiencias = servExperiencia.getAllByPerfil(perfil);
            proyectos = servProyecto.getAllByPerfil(perfil);
            skills = servSkill.getAllByPerfil(perfil);
        } catch (Exception e) {
            throw new ErrorSQL(e.getMessage());
        }
        for (Experiencia experiencia : experiencias) {
            expDTOs.add(modelMapper.map(experiencia, ExperienciaDTO.class));
        }
        for (Proyecto proyecto : proyectos) {
            proyDTOs.add(modelMapper.map(proyecto, ProyectoDTO.class));
        }
        for (Educacion educacion : educacions) {
            educacionDTOs.add(modelMapper.map(educacion, EducacionDTO.class));
        }
        for (Skill skill : skills) {
            skillDTOs.add(modelMapper.map(skill, SkillDTO.class));
        }


        perfilDTO.setExperiencia(expDTOs);
        perfilDTO.setProyectos(proyDTOs);
        perfilDTO.setEducacion(educacionDTOs);
        perfilDTO.setSkill(skillDTOs);
        return perfilDTO;
    }

    public PerfilDTO updateDTO(PerfilDTO perfilDTO) throws PerfilException, ErrorSQL {
        Perfil perfil = modelMapper.map(perfilDTO, Perfil.class);
        //////////
        // si el obj entrante quiere ser principal
        // verificamos si existe un perfil principal si lo hay se lo 
        // retipifica como 'storage' 
        if (perfil.getType()==PerfilType.principal) {
            if (perfilRepository.findByType(PerfilType.principal).isPresent()) {
                Perfil temp= perfilRepository.findByType(PerfilType.principal).get();
                temp.setType(PerfilType.storage);
                perfilRepository.save(temp);
            }
        } 
        List<Imagenes> temp = new ArrayList<Imagenes>();
        List<Imagenes> back = new ArrayList<Imagenes>();
        List<String> back_ground = perfilDTO.getImage_background_header();
        List<String> perfil_img = perfilDTO.getImage_perfil();
        List<Imagenes> toRemove=new ArrayList<Imagenes>();
        List<String> tList=new ArrayList<String>();
        log.info("pasondo por update");
        //log.info("pasondo por save perfil_img:{}",perfil_img);
        //log.info("pasondo por save back ground:{}",back_ground);
        //
        if(perfilRepository.findById(perfil.getId()).isEmpty())
        {
            throw new PerfilException("no existe este registro");
        }
        if (perfilRepository.findAllByDniOrEmail(perfil.getDni(), perfil.getEmail()).isPresent()) {
            Perfil tempAPerfil[]= perfilRepository.findAllByDniOrEmail(perfil.getDni(), perfil.getEmail()).get();
            for (Perfil tPerfil : tempAPerfil) {
                if(tPerfil.getId()!=perfil.getId())
                {
                    throw new PerfilException("Dni :" + perfil.getDni() + " o mail: " + perfil.getEmail()+"ya existen");
                }
            }
            
        }
        try {
            //perfil.setId(d());
            perfil = perfilRepository.save(perfil);
            // se hace esto para no tener ocupado a la base de datos
            if (imgRepo.findByPerfilAndType(perfil, ImageType.BackGround).get().size()>0) {
                // List<Imagenes> toRemove=new ArrayList<Imagenes>();
                // List<String> tList=new ArrayList<String>();
                tList.clear();
                tList.addAll(back_ground);
                temp = imgRepo.findByPerfilAndType(perfil, ImageType.BackGround).get();
                log.info("no bacio");
                log.info("back :{}",back_ground);
                for (Imagenes t : temp) {
                    if (back_ground.indexOf(t.getImagenSrc()) != -1) { // buscamos objetos repetidos
                        back_ground.remove(back_ground.indexOf(t.getImagenSrc())); // si lo hay se remueven
                    }
                    if (tList.indexOf(t.getImagenSrc()) == -1) { // buscamos objetos a remover
                        toRemove.add(t);
                    }
                }
                imgRepo.deleteAll(toRemove);
                log.info("entrante :{}", tList);
                log.info("almacenado :{}",temp);
                log.info("no repetidos :{}", back_ground);
                log.info("a borrar :{}", toRemove);
            }
            if (!imgRepo.findByPerfilAndType(perfil, ImageType.Face).isEmpty()) {
                // List<Imagenes> toRemove=new ArrayList<Imagenes>();
                temp = imgRepo.findByPerfilAndType(perfil, ImageType.Face).get();
                tList.clear();
                tList.addAll(perfil_img);
                for (Imagenes t : temp) {
                    if (perfil_img.indexOf(t.getImagenSrc()) != -1) { // buscamos objetos repetidos
                        perfil_img.remove(perfil_img.indexOf(t.getImagenSrc())); // si lo hay se remueven
                    }
                    if (tList.indexOf(t.getImagenSrc()) == -1) { // buscamos objetos a remover
                        toRemove.add(t);
                    }
                }
                imgRepo.deleteAll(toRemove);
            }

            if (back_ground != null)
            {
                back=new ArrayList<Imagenes>();
                for (String back_img : back_ground) { // guerdamos las imagenes una por una
                    back.add(new Imagenes(back_img,ImageType.BackGround,perfil));
                }
                imgRepo.saveAll(back);
            }
            if (!imgRepo.findByPerfilAndType(perfil, ImageType.Face).isEmpty()) {
                temp = imgRepo.findByPerfilAndType(perfil, ImageType.Face).get();
                for (Imagenes t : temp) {
                    if (perfil_img.indexOf(t.getImagenSrc()) != -1) { // buscamos objetos repetidos
                        perfil_img.remove(perfil_img.indexOf(t.getImagenSrc())); // si lo hay se remueven
                    }
                }
            }
            if (perfil_img != null)
            {
                back=new ArrayList<Imagenes>();
                for (String back_img : perfil_img) { // guerdamos las imagenes una por una
                    back.add(new Imagenes(back_img,ImageType.Face,perfil));
                } 
                imgRepo.saveAll(back);
            }

        } catch (Exception e) {
            throw new ErrorSQL(e.getMessage());
        }
        log.info("actualizado :{}", perfil.getDni());
        perfilDTO = modelMapper.map(perfil, PerfilDTO.class);
        List<String> tt=new ArrayList<String>();
        for (Imagenes ti :imgRepo.findByPerfilAndType(perfil, ImageType.BackGround).get()) {
            tt.add(ti.getImagenSrc());
        }
        log.info("back image:{}", tt);
        perfilDTO.setImage_background_header(tt);
        tt=new ArrayList<String>();
        for (Imagenes ti :imgRepo.findByPerfilAndType(perfil, ImageType.Face).get()) {
            tt.add(ti.getImagenSrc());
        }
        log.info("face image:{}", tt);
        perfilDTO.setImage_perfil(tt);
        return perfilDTO;
    }

    public PerfilDTO borrarDTO(long dni) throws PerfilException {
        PerfilDTO rDto=new PerfilDTO();
        Perfil      pDto;
        if (perfilRepository.findByDni(dni).isPresent()) {
            pDto=perfilRepository.findByDni(dni).get();
            perfilRepository.delete(pDto);
            return rDto;
        }
        throw new PerfilException("no existe" + dni);
    }
    public PerfilDTO getDefaultDTO(){
        long perfilPrincipal = perfilRepository.findByType(PerfilType.principal).get().getDni();
        log.info("perfil principal dni:{}",perfilPrincipal);
        return getFullDTO(perfilPrincipal);
    }
}
