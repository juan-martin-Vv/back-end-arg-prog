package arg.prog.backend.sercurity.Service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import arg.prog.backend.sercurity.DTO.JwtDto;
import arg.prog.backend.sercurity.DTO.UserDTO;
import arg.prog.backend.sercurity.DTO.loginUser;
import arg.prog.backend.sercurity.Entity.Roll;
import arg.prog.backend.sercurity.Entity.Usuario;
import arg.prog.backend.sercurity.Enums.RollEnum;
import arg.prog.backend.sercurity.JWT.JwtConstructor;
import arg.prog.backend.sercurity.Service.Exceptions.LoginException;
import arg.prog.backend.sercurity.Service.Exceptions.UserException;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class LoginService {
    @Value("${config.admin.mail}")
    private String MailAdmin;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RollService rollService;
    @Autowired
    JwtConstructor jwtConstructor;
    @Autowired
    ModelMapper mapper;

    public UserDTO CrearUsuario(UserDTO nuevoUsuario) throws UserException{
        Usuario user =  mapper.map(nuevoUsuario, Usuario.class);
        if (usuarioService.existsByEmail(user.getEmail())) {
            throw new UserException(user.getEmail()+" ya existe");
        }
        if (usuarioService.existsByNombreUser(user.getNombreUsuario())) {
            throw new UserException(user.getNombreUsuario()+" ya existe");
        }
        user.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));   //ciframos el password
        Set <Roll> roles = new HashSet<>();
        roles.add(rollService.getByRollName(RollEnum.ROLL_USER).get());     // todos son User
        
        // solo los que tengan el mail admin son administradores
        log.info("email :{}   MailAdmin : ",user.getEmail(),MailAdmin);
        if (user.getEmail().contentEquals(MailAdmin)) {
            roles.add(rollService.getByRollName(RollEnum.ROLL_ADMIN).get()); 
        }                                                                                       
        user.setRoles(roles);
        usuarioService.save(user);
        log.info("ususario ingresado: {}",nuevoUsuario.getNombreUsuario());
        log.info("ususario creado: {} privilegio: {}",user.getNombreUsuario(),user.getRoles().stream().map(r->
            new String(r.getRollName().name() )).toArray());
        
        nuevoUsuario=mapper.map(user, UserDTO.class);
        Set<String>roles_cargados=new HashSet<>();
        for(Roll r:user.getRoles()){
            roles_cargados.add(r.getRollName().name());
            log.info("{} tiene privilegios :{}",user.getNombreUsuario(), r.getRollName().name());
        }
        nuevoUsuario.setRoles(roles_cargados);
        return nuevoUsuario;
    }
    public JwtDto CargarUsuario(loginUser nuevoUsuario)  {
        log.info("login :{}",nuevoUsuario.getNombreUsuario());
        Authentication auth;
        try{
            auth = authenticationManager
        .authenticate(
            new UsernamePasswordAuthenticationToken(nuevoUsuario.getNombreUsuario(),nuevoUsuario.getPassword()));
        }catch(AuthenticationException e)
        {
            log.error("error en authenticacion :{}",e.getMessage());
            throw new LoginException("usuario o contraseña invalidos!!!");
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtConstructor.crearToken(auth);
        UserDetails userDetail=(UserDetails) auth.getPrincipal();
        log.info("privilegios userDetails:{}",userDetail.getAuthorities());
        log.info("privilegios jwtPlayLoad:{}",jwtConstructor.getAuthFromJwt(jwt));
        return new JwtDto(jwt);
    }
    public JwtDto refresToken(@Valid loginUser lUser) {
        
        return null;
    }
}
