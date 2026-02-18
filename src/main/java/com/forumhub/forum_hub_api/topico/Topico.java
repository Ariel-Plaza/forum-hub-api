package com.forumhub.forum_hub_api.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name="topicos")
@Entity(name="Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private String autor;
    private String curso;

    public Topico(DatosRegistroTopico datos){
        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = datos.fechaCreacion();
        this.status = datos.status();
        this.autor = datos.autor();
        this.curso = datos.curso();
    }
}