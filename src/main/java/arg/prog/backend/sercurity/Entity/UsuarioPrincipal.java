package arg.prog.backend.sercurity.Entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails {
    private String Nombre;
    private String NombreUsuario;
    private String password;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private String mail;
    private Collection<? extends GrantedAuthority> autoridad;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return autoridad;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> aut) {
        this.autoridad = aut;
    }

    //
    @Override
    public String getPassword() {
        
        return password;
    }

    @Override
    public String getUsername() {
        
        return NombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }

    @Override
    public boolean isEnabled() {
        
        return true;
    }

    public UsuarioPrincipal() {
    }

    public UsuarioPrincipal(String nombre, String nombreUsuario, String password, String mail,
            Collection<? extends GrantedAuthority> autoridad) {
        this.Nombre = nombre;
        this.NombreUsuario = nombreUsuario;
        this.password = password;
        this.mail = mail;
        this.autoridad = autoridad;
    }

    public static UsuarioPrincipal build(Usuario user) {

        // List<GrantedAuthority> autoridad = new ArrayList<>();
        // for (Roll r : user.getRoles()) {
        // autoridad.add(new SimpleGrantedAuthority(r.getRollName().name()));
        // }
        List<GrantedAuthority> autoridad = user.getRoles()
                .stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRollName().name()))
                .collect(Collectors.toList());
        return new UsuarioPrincipal(
                user.getNombre(),
                user.getNombreUsuario(),
                user.getPassword(),
                user.getEmail(),
                autoridad);
    }
}
