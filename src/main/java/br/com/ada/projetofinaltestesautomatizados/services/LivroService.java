package br.com.ada.projetofinaltestesautomatizados.services;

import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;

import java.util.List;

public interface LivroService {
    LivroResponse salvar(LivroRequest livroRequest);
    LivroResponse buscarPorId(String isbn);
    List<LivroResponse> buscarPorTitulo(String titulo);
    List<LivroResponse> buscarTodos();
    LivroResponse atualizar(String isbn, LivroRequest livroRequest);
    void deletar(String isbn);
}
