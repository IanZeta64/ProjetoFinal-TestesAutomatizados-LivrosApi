package br.com.ada.projetofinaltestesautomatizados.controllers;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import br.com.ada.projetofinaltestesautomatizados.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LivroControllerImpl implements LivroController {

    private final LivroService service;
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public LivroResponse salvar(@RequestBody LivroRequest livroRequest) {
        return service.salvar(livroRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public LivroResponse buscarPorIsbn(String isbn) {
        return service.buscarPorIsbn(isbn);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> buscarPorTitulo(String titulo) {
        return service.buscarPorTitulo(titulo);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> buscarTodos() {
        return service.buscarTodos();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public LivroResponse atualizar(String isbn, LivroRequest livroRequest) {
        return service.atualizar(isbn, livroRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(String isbn) {
        service.deletar(isbn);
    }
}
