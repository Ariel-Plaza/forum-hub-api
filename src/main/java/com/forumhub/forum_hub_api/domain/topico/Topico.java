package com.forumhub.forum_hub_api.domain.topico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
    @Column(unique = true)
    private String titulo;
    @Column(unique = true)
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private String autor;
    private String curso;
    private Boolean activo;

    //Registro Topico
    public Topico(DatosRegistroTopico datos){
        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = datos.status();
        this.autor = datos.autor();
        this.curso = datos.curso();
        this.activo = true;
    }

//    Actualizacion Topico
    public void actualizarTopico(@Valid DatosActualizacionTopico datos){
        if(datos.titulo() != null){
            this.titulo = datos.titulo();
        }
        if(datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }
        if(datos.fechaCreacion() != null){
            this.fechaCreacion = datos.fechaCreacion();
        }
        if(datos.status() != null){
            this.status = datos.status();
        }
        if(datos.autor() != null){
            this.autor = datos.autor();
        }
        if(datos.curso() != null){
            this.curso = datos.curso();
        }
    }

    //Eliminar
    public void eliminar () {
        this.activo = false;
    }
}