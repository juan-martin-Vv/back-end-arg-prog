package arg.prog.backend.Controller;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import arg.prog.backend.DTO.ProyectoDTO;
import arg.prog.backend.Service.ServProyecto;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/proyect")
// @CrossOrigin
public class ProyectoController {
    @Autowired
    private ServProyecto servProyecto;

    ///
    @GetMapping("")
    @PermitAll
    public ResponseEntity<?> getDTO(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Boolean full,
            @RequestParam(required = false) Long dni) {
        if (id != null && id > 0) {
            return new ResponseEntity<>(servProyecto.getDTO(id), HttpStatus.ACCEPTED);
        }
        if (dni != null && dni >10) {
            return new ResponseEntity<>(servProyecto.getAllByDNI(dni), HttpStatus.ACCEPTED);
        }
        if (full !=null && full==true) {
            return new ResponseEntity<>(servProyecto.getAll(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("no ser recone dni:" + dni + "o id:" + id, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("")
    public ProyectoDTO saveDto(@Valid @RequestBody ProyectoDTO proyectoDTO, @RequestParam long dni) {
        return servProyecto.saveDTO(proyectoDTO, dni);
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(@RequestParam(required = true) Integer id) {
        return new ResponseEntity<>(servProyecto.delete(id), HttpStatus.ACCEPTED);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody ProyectoDTO pDto) {
        return new ResponseEntity<>(servProyecto.update(pDto), HttpStatus.ACCEPTED);
    }
}
