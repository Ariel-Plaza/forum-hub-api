package com.forumhub.forum_hub_api.controller;

import com.forumhub.forum_hub_api.domain.usuarios.DatosAutenticacionUsuario;
import com.forumhub.forum_hub_api.domain.usuarios.Usuario;
import com.forumhub.forum_hub_api.domain.usuarios.UsuarioRepository;
import com.forumhub.forum_hub_api.infra.security.DatosJWTToken;
import com.forumhub.forum_hub_api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired // <--- AGREGA ESTO
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Importa org.springframework.security.crypto.password.PasswordEncoder

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {

        System.out.println(">>> Intentando login con: " + datosAutenticacionUsuario.login());
        System.out.println(">>> Clave enviada (raw): " + datosAutenticacionUsuario.clave());


        var usuarioEnDB = repository.findByLogin(datosAutenticacionUsuario.login());
        if (usuarioEnDB != null) {
            System.out.println(">>> Usuario encontrado en DB");
            System.out.println(">>> Hash en DB: " + usuarioEnDB.getPassword());
            System.out.println("NUEVO HASH GENERADO: " + passwordEncoder.encode("123456"));
            // Comparación manual rápida para probar el bean
            boolean coinciden = passwordEncoder.matches(datosAutenticacionUsuario.clave(), usuarioEnDB.getPassword());
            System.out.println(">>> ¿Coinciden según BCrypt?: " + coinciden);
        } else {
            System.out.println(">>> USUARIO NO ENCONTRADO EN DB");
        }
        try{
            var authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave());
            var autenticacion =  authenticationManager.authenticate(authenticationToken);

            var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());

            return ResponseEntity.ok(new DatosJWTToken(tokenJWT));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    }


