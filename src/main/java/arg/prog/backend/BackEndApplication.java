package arg.prog.backend;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import arg.prog.backend.DTO.PerfilDTO;
import arg.prog.backend.Enum.PerfilType;
import arg.prog.backend.Service.ServPerfil;
import arg.prog.backend.sercurity.Entity.Roll;
import arg.prog.backend.sercurity.Enums.RollEnum;
import arg.prog.backend.sercurity.Service.RollService;

@SpringBootApplication
public class BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RollService service, ServPerfil perfil ){
		return args ->{
			if(service.sinValores()){
				System.out.println("inicializando DB");
				service.save(new Roll(RollEnum.ROLL_USER));
				service.save(new Roll(RollEnum.ROLL_ADMIN));
				perfil.saveDTO(new PerfilDTO(
					"Homerin",
					"SimpsomG",
					20300300,new Date(),
					"Donalandia",
					"hoo@hoo.ho",
					PerfilType.principal
					));
			}
		};
	}
}
