package br.com.ada.projetofinaltestesautomatizados.controllers;

import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/livros")

public interface LivroController {

    @PostMapping
    ResponseEntity<LivroResponse> salvar(@RequestBody LivroRequest livroRequest);
    @GetMapping("/{isbn}")
    ResponseEntity<LivroResponse> buscarPorIsbn(@PathVariable String isbn);
    @GetMapping(params = "titulo")
    ResponseEntity<Optional<LivroResponse>> buscarPorTitulo(@RequestParam String titulo);
    @GetMapping
    ResponseEntity<List<LivroResponse>> buscarTodos();
    @PutMapping("/{isbn}")
    ResponseEntity<LivroResponse> atualizar(@PathVariable("isbn")String isbn, @RequestBody LivroRequest livroRequest);
    @DeleteMapping("/{isbn}")
    ResponseEntity<Void> deletar(@PathVariable("isbn") String isbn);
}
