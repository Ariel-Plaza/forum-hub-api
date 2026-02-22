package com.forumhub.forum_hub_api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// Indica a Spring que esta clase manejará errores de TODOS los controllers REST
    @RestControllerAdvice
    public class GestorDeErrores {

        // Error 404
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity getionarError404() {
            return ResponseEntity.notFound().build();
        }

        //Error 400
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity getionarError400(MethodArgumentNotValidException ex) {
            var errores = ex.getFieldErrors();
            return ResponseEntity.badRequest()
                    .body(errores.stream()
                            .map(DatosErrorValidacion::new)
                            .toList());
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity gestionarErrorTipoArgumento(MethodArgumentTypeMismatchException ex){
            return ResponseEntity.badRequest()
                    .body(new DatosErrorTipo(ex.getName(), ex.getValue(),"Debe ser un número válido"));
        }

        private record DatosErrorValidacion(String campo, String error){
            public DatosErrorValidacion(FieldError error) {
                this(error.getField(), error.getDefaultMessage());
            }
        }

        private record DatosErrorTipo(String campo, Object valorRecibido, String error){}

    }

