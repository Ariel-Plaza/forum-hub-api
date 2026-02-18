package com.forumhub.forum_hub_api.controller;

import com.forumhub.forum_hub_api.topico.DatosRegistroTopico;
import com.forumhub.forum_hub_api.topico.Topico;
import com.forumhub.forum_hub_api.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicos")

public class TopicoController {
    @Autowired
    private TopicoRepository repository;
    @Transactional
    @PostMapping
    public  void registrar(@RequestBody @Valid DatosRegistroTopico datos){
        repository.save(new Topico(datos));
        System.out.println(datos);
    }
}
