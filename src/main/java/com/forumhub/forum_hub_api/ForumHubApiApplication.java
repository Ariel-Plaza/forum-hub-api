package com.forumhub.forum_hub_api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumHubApiApplication {

	public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .directory("./")  // Busca en la raíz del proyecto
                .ignoreIfMissing()
                .load();

        // Imprime para verificar que se cargó (quita después)
        System.out.println("DB_HOST cargado: " + dotenv.get("DB_HOST"));

        // Cargar como propiedades del sistema
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
		SpringApplication.run(ForumHubApiApplication.class, args);
	}

}
