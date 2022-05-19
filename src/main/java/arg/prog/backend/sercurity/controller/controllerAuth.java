package arg.prog.backend.sercurity.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import arg.prog.backend.sercurity.DTO.UserDTO;
import arg.prog.backend.sercurity.DTO.loginUser;
import arg.prog.backend.sercurity.Service.LoginService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
// @CrossOrigin
public class controllerAuth {
    @Autowired
    LoginService loginService;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody UserDTO nuvo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("error en valid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("pasando por nuevo: " + nuvo);
        return new ResponseEntity<>(loginService.CrearUsuario(nuvo), HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@Valid @RequestBody loginUser lUser, BindingResult bindingResult) {
        log.info("pasando por login: " + lUser);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loginService.CargarUsuario(lUser), HttpStatus.ACCEPTED);
    }

}
