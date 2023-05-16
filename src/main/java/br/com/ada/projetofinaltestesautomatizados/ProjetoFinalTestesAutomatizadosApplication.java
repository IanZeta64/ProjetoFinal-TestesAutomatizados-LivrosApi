package br.com.ada.projetofinaltestesautomatizados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories
public class ProjetoFinalTestesAutomatizadosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoFinalTestesAutomatizadosApplication.class, args);
    }
}
