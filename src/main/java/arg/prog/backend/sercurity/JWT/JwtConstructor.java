package arg.prog.backend.sercurity.JWT;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import arg.prog.backend.sercurity.Entity.UsuarioPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtConstructor {
    @Value("${config.jwt.secret}")
    private String Secreto;
    @Value("${config.jwt.expiration}")
    private int expiracion;
    @Value("${config.jwt.interval.expiration}")
    private int interval;
    ///
    public String crearToken(Authentication auth) {
        UsuarioPrincipal useP = (UsuarioPrincipal) auth.getPrincipal();
        log.info("secret: {} expricaion time: {} min", Secreto, expiracion);
        String token = Jwts.builder()
                .setSubject(useP.getUsername())
                .claim("email", useP.getMail())
                .claim("privilegios", useP.getAuthorities())
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expiracion).toInstant()))
                .signWith(SignatureAlgorithm.HS512, Secreto.getBytes())
                .compact();

        return token;
    }

    public JwtConstructor() {
    }

    public String getNomUserFromToken(String token) {
        if (validaToken(token)) {
            return getClaims(token).getSubject();
        }
        return null;
    }

    public Collection<? extends GrantedAuthority> getAuthFromJwt(String token) {
        if (!validaToken(token)) {
            return null;
        }
        Claims cuerpo = getClaims(token);
        ArrayList<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

        ArrayList<?> aut = (ArrayList<?>) cuerpo.get("privilegios", ArrayList.class);
        aut.forEach(
                (p) -> {
                    log.info("privilegios: {}", p.toString());
                    if (p.toString().contains("ROLL_USER"))
                        auths.add(new SimpleGrantedAuthority("ROLL_USER"));
                    if (p.toString().contains("ROLL_ADMIN"))
                        auths.add(new SimpleGrantedAuthority("ROLL_ADMIN"));
                });
        auths.forEach(p -> {
            log.info("authority: {}", p.getAuthority());
        });
        return auths;
    }

    public Boolean isExpiredToken(String token) throws ExpiredJwtException {
        Claims body = Jwts.claims();
        try {
            Jwts.parser().setSigningKey(Secreto.getBytes()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            body = e.getClaims();
        }
        Date creado = body.getIssuedAt();
        Date expirado = body.getExpiration();
        Long periodo = ChronoUnit.MINUTES.between(
                LocalDateTime.ofInstant(creado.toInstant(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.systemDefault()));
        log.info("creado :{}", creado);
        log.info("expirado :{}", expirado);
        log.info("periodo :{}", periodo);
        if (periodo <= (expiracion * interval)) {
            log.info("refresing...");
            return true;
        }
        log.info("No se puede Refrescar ...");
        return false;

    }

    public String getRefresToken(String token) throws ExpiredJwtException {
        Claims tokenExpired = Jwts.claims();
        try {
            Jwts.parser().setSigningKey(Secreto.getBytes()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            tokenExpired = e.getClaims();
        }
        String refres_token = Jwts.builder()
                .setSubject(tokenExpired.getSubject())
                .claim("email", tokenExpired.get("email"))
                .claim("privilegios", tokenExpired.get("privilegios"))
                .setIssuedAt(tokenExpired.getIssuedAt())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expiracion).toInstant()))
                .signWith(SignatureAlgorithm.HS512, Secreto.getBytes())
                .compact();
        return refres_token;
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(Secreto.getBytes()).parseClaimsJws(token).getBody();

    }

    public boolean validaToken(String token) throws ExpiredJwtException {

        try {
            getClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("token mal formado");

        } catch (UnsupportedJwtException e) {
            log.error("token no soportado");

        } catch (ExpiredJwtException e) {
            log.error("token expirado");
        } catch (IllegalArgumentException e) {
            log.error("token vacío");

        } catch (SignatureException e) {
            log.error("fail en la firma");
        }
        return false;
    }

}
