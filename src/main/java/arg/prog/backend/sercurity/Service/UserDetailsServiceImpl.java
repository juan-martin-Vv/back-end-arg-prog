package arg.prog.backend.sercurity.Service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import arg.prog.backend.sercurity.Entity.Usuario;
import arg.prog.backend.sercurity.Entity.UsuarioPrincipal;
import arg.prog.backend.sercurity.Service.Exceptions.LoginException;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UsuarioService uService;

    //construimos el usuario principal desde los datos de la base de datos
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, LoginException {
        log.info("user entry: "+username);
        Usuario u;
        try{
            u=uService.getByNombreUser(username).get();
            return UsuarioPrincipal.build(u);
        }
        catch(Exception e){

        log.error("No se pudo cargar: "+username+" error : "+e);
        throw new LoginException("No se pudo encontrar: "+username+" error : "+e.getMessage());
        }
    }
    
}
