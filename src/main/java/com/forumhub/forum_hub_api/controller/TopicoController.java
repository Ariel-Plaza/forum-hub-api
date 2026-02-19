package com.forumhub.forum_hub_api.controller;
import com.forumhub.forum_hub_api.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")

public class TopicoController {
    @Autowired
    private TopicoRepository repository;
    @Transactional

    //Guardar datos en repositorio
    @PostMapping
    public  void registrar(@RequestBody @Valid DatosRegistroTopico datos){
        repository.save(new Topico(datos));
        System.out.println(datos);
    }

    //Leer datos de BD
    @Transactional
    @GetMapping
    public Page<DatosListaTopico> listar(@PageableDefault(size=10,page=0, sort={"fechaCreacion"}) Pageable paginacion){
        return repository.findByActivoTrue(paginacion).map(DatosListaTopico::new);
    }

    //Actualizar topico
    @Transactional
    @PutMapping
    public void actualizar(@RequestBody @Valid DatosActualizacionTopico datos){
        var topico = repository.getReferenceById(datos.id());
        topico.actualizarTopico(datos);
    }

    //Eliminar topico
    @Transactional
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        var topico = repository.getReferenceById(id);
        topico.eliminar();
    }
}
