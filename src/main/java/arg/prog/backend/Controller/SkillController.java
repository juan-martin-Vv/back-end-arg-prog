package arg.prog.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import arg.prog.backend.DTO.SkillDTO;
import arg.prog.backend.Service.ServSkill;

@RestController
@RequestMapping("/skill")
// @CrossOrigin
public class SkillController {
    @Autowired
    private ServSkill servSkill;

    ///
    @GetMapping("")
    public ResponseEntity<?> getDTO(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Boolean full,
            @RequestParam(required = false) Long dni) {
        if (id != null && id > 0) {
            return new ResponseEntity<>(servSkill.getDTO(id), HttpStatus.ACCEPTED);
        }
        if (dni != null && dni >10) {
            return new ResponseEntity<>(servSkill.getAllByDNI(dni), HttpStatus.ACCEPTED);
        }
        if (full !=null && full==true) {
            return new ResponseEntity<>(servSkill.getAll(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("no ser recone dni:" + dni + "o id:" + id, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("")
    public SkillDTO saveDto( @RequestBody SkillDTO skillDTO, @RequestParam long dni) {
        return servSkill.saveDTO(skillDTO, dni);
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(@RequestParam(required = true) Long id) {
        return new ResponseEntity<>(servSkill.delete(id), HttpStatus.ACCEPTED);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody SkillDTO pDto) {
        return new ResponseEntity<>(servSkill.update(pDto), HttpStatus.ACCEPTED);
    }
}