package br.com.ada.projetofinaltestesautomatizados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProjetoFinalTestesAutomatizadosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoFinalTestesAutomatizadosApplication.class, args);
    }
}
