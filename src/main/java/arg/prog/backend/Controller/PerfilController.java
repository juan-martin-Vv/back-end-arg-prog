package arg.prog.backend.Controller;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import arg.prog.backend.DTO.PerfilDTO;
import arg.prog.backend.Service.ServPerfil;


@RestController
@RequestMapping("/perfil")
// @CrossOrigin
public class PerfilController {
    @Autowired
    private ServPerfil servPerfil;
    
    @GetMapping("")
    @PermitAll
    public PerfilDTO perfil(@RequestParam(required = false) Long dni)
    {
        if (dni==null) 
        {
            return servPerfil.getDefaultDTO();
        }
        
            return servPerfil.getFullDTO(dni);
        
    }
    ////
    @PostMapping("")
    public PerfilDTO guardarPerfil(@RequestBody PerfilDTO pDto){
       return  servPerfil.saveDTO(pDto);
    }
    @PutMapping("")
    public PerfilDTO actualizarPerfil(@RequestBody PerfilDTO pDto){
       return  servPerfil.updateDTO(pDto);
    }
    @DeleteMapping("")
    public PerfilDTO borrarPerfil(@RequestParam(required = false) Long dni){
       return  servPerfil.borrarDTO(dni);
    }
}