package br.com.ada.projetofinaltestesautomatizados.controllers;

import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import br.com.ada.projetofinaltestesautomatizados.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LivroControllerImpl implements LivroController {

    private final LivroService service;
    @Override
    @PostMapping
    public ResponseEntity<LivroResponse> salvar(@RequestBody LivroRequest livroRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(livroRequest));
    }

    @Override
    public ResponseEntity<LivroResponse> buscarPorIsbn(String isbn) {
        return null;
    }

    @Override
    public ResponseEntity<Optional<LivroResponse>> buscarPorTitulo(String titulo) {
        return null;
    }

    @Override
    public ResponseEntity<List<LivroResponse>> buscarTodos() {
        return null;
    }

    @Override
    public ResponseEntity<LivroResponse> atualizar(String isbn, LivroRequest livroRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletar(String isbn) {
        return null;
    }
}
