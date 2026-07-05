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

    @Autowired 
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        
        try{
            var authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave());
            var autenticacion =  authenticationManager.authenticate(authenticationToken);
            var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());
            return ResponseEntity.ok(new DatosJWTToken(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciales inválidas");
        }
    }
    }


