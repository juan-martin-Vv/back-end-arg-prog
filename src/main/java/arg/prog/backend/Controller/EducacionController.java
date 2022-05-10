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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import arg.prog.backend.DTO.EducacionDTO;
import arg.prog.backend.Service.ServEducacion;

@RestController
@RequestMapping("/education")
@CrossOrigin
public class EducacionController {
    @Autowired
    private ServEducacion servEducacion;

    ///
    @GetMapping("")
    @PermitAll
    public ResponseEntity<?> getDTO(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Boolean full,
            @RequestParam(required = false) Long dni) {
        if (id != null && id > 0) {
            return new ResponseEntity<>(servEducacion.getDTO(id), HttpStatus.ACCEPTED);
        }
        if (dni != null) {
            return new ResponseEntity<>(servEducacion.getAllByDNI(dni), HttpStatus.ACCEPTED);
        }
        if (full) {
            return new ResponseEntity<>(servEducacion.getAll(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("no ser recone dni:" + dni + "o id:" + id, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("")
    public ResponseEntity<?> saveDto(@Valid @RequestBody EducacionDTO experienciaDTO, @RequestParam long dni) {
        return new ResponseEntity<>(servEducacion.saveDTO(experienciaDTO, dni), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(@RequestParam(required = true) Integer id) {
        return new ResponseEntity<>(servEducacion.delete(id), HttpStatus.ACCEPTED);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody EducacionDTO pDto) {
        return new ResponseEntity<>(servEducacion.update(pDto), HttpStatus.ACCEPTED);
    }
}
