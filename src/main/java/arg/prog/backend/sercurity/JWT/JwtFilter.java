package arg.prog.backend.sercurity.JWT;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import arg.prog.backend.sercurity.DTO.JwtDto;
import arg.prog.backend.sercurity.Service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtConstructor jwtConstructor;
    @Autowired
    UserDetailsServiceImpl userDetailImp;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getToken(request);
            if (token != null && jwtConstructor.validaToken(token)) {
                String nombreUsuario = jwtConstructor.getNomUserFromToken(token);
                log.info("peticion entrante de :{}", nombreUsuario);
                UserDetails userDetails = userDetailImp.loadUserByUsername(nombreUsuario);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else if (token != null && jwtConstructor.isExpiredToken(token)) {
                log.info("Generando RefresToken....");
                String refresToken = jwtConstructor.getRefresToken(token);
                log.debug("token refresf: {}", refresToken);
                String nombreUsuario = jwtConstructor.getNomUserFromToken(refresToken);
                log.info("peticion entrante de refresco de :{}", nombreUsuario);
                //
                //habilitar CORS en el navegador para que sea visto por por la app
                response.addHeader("Access-Control-Expose-Headers", "Autorization-r"); 
                //
                response.addHeader("Autorization-r", "Bearer " + refresToken);
                
            }
        } catch (Exception e) {
            log.error("error en filter e.clas:{}", e.getClass());
            log.error("error en filter mensaje:{}", e.getMessage());
        }
        log.info("metodo :{}", request.getMethod());
        if (request.getMethod()=="OPTIONS") {
            response.setStatus(HttpStatus.OK.value());
           log.info("metodo :{}", request.getMethod());
           response.sendError(200);
           return;
        }
        filterChain.doFilter(request, response);
    }

    public String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        String token;
        if (header != null && header.startsWith(JwtDto.bearer)) {
            log.debug("Header :{}", header);
            token = header.replace(JwtDto.bearer + " ", "");
            log.debug("Token :{}", token);
            return token;
        }
        return null;
    }
}
