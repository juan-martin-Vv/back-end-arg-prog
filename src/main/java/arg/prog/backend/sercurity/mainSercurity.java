package arg.prog.backend.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import arg.prog.backend.sercurity.JWT.JwtEntryPoint;
import arg.prog.backend.sercurity.JWT.JwtFilter;
import arg.prog.backend.sercurity.Service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class mainSercurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtFilter jwtTokenFilter() {
        return new JwtFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    ////
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        // https://www.baeldung.com/spring-security-csrf
        http.csrf().disable();// .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/**").permitAll();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/skill", "/education", "/perfil", "/proyect", "/experience")
                .hasAuthority("ROLL_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/skill", "/education", "/perfil", "/proyect", "/experience")
                .hasAuthority("ROLL_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.PATCH, "/skill", "/education", "/perfil", "/proyect", "/experience")
                .hasAuthority("ROLL_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/skill", "/education", "/perfil", "/proyect", "/experience")
                .hasAuthority("ROLL_ADMIN");
        http.authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(jwtEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {

        return super.authenticationManager();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

}
