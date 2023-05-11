package br.com.ada.projetofinaltestesautomatizados.models;

import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;


public interface Livro {

    LivroResponse toResponse();
    LivroEntity update(LivroRequest livroRequest);

}
