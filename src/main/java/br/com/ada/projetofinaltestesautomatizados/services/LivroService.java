package br.com.ada.projetofinaltestesautomatizados.services;

import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface LivroService {
    LivroResponse salvar(LivroRequest livroRequest);
    LivroResponse buscarPorIsbn(String isbn);
    Optional<LivroResponse> buscarPorTitulo(String titulo);
    List<LivroResponse> buscarTodos();
    LivroResponse atualizar(String isbn, LivroRequest livroRequest);
    void deletar(String isbn);
}
