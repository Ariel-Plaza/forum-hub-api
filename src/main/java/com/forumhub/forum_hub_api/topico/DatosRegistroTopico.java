package com.forumhub.forum_hub_api.topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull LocalDateTime fechaCreacion,
        @NotBlank String status,
        @NotBlank String autor,
        @NotBlank String curso
) {

}