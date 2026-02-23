package com.forumhub.forum_hub_api.domain.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Usuario implements UserDetails {

    // Marca este campo como la clave primaria de la entidad
    @Id
    // Indica que el ID se generará automáticamente (auto-increment en la BD)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo que almacena el email/nombre de usuario
    private String login;

    // Campo que almacena la contraseña hasheada con BCrypt
    private String clave;

    /**
     * Retorna los roles/permisos del usuario     * Spring Security usa este metodo para verificar qué puede hacer el usuario     * @return Lista con el rol "ROLE_USER" asignado a todos los usuarios
     */    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    /**
     * Retorna la contraseña hasheada del usuario     * Spring Security usa este metodo para comparar la contraseña ingresada     * con el hash almacenado en la base de datos usando BCrypt     * @return El hash de la contraseña (ej: $2a$10$xHj...)
     */    @Override
    public String getPassword() {
        return clave;
    }
    /**
     * Retorna el nombre de usuario (login/email)     * Spring Security usa este metodo para identificar al usuario     * @return El email/login del usuario
     */    @Override
    public String getUsername() {
        return login;
    }
    /**
     * Indica si la cuenta del usuario no ha expirado     * @return true - la cuenta nunca expira
     */    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Indica si la cuenta del usuario no está bloqueada     * @return true - la cuenta nunca se bloquea
     */    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
       @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
       @Override
    public boolean isEnabled() {
        return true;
    }}
