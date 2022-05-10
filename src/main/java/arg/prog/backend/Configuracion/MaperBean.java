package arg.prog.backend.Configuracion;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MaperBean {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
