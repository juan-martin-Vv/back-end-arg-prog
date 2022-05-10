package arg.prog.backend.sercurity.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arg.prog.backend.sercurity.Entity.Usuario;
import arg.prog.backend.sercurity.Repository.UserRepository;
import arg.prog.backend.sercurity.Service.Exceptions.UserException;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    UserRepository userRepository;
    public Optional <Usuario> getByNombreUser(String nomb){
        return userRepository.findByNombreUsuario(nomb);
    }
    public Optional <Usuario> getByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public boolean existsByNombreUser(String nomb_user){
        return userRepository.existsByNombreUsuario(nomb_user);
    }
    public boolean existsByEmail(String mail){
        return userRepository.existsByEmail(mail);
    }
    public void save(Usuario usuario){
        try {
            System.out.println("usuario para guardar :"+usuario);
            userRepository.save(usuario);    
        } catch (Exception e) {
            throw new UserException("error al guardar el usuario");
        }
        
    }
}
