package br.com.ada.projetofinaltestesautomatizados.controllers;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/livros")

public interface LivroController {

    @PostMapping
    LivroResponse salvar(@Validated @RequestBody LivroRequest livroRequest);
    @GetMapping("/{isbn}")
    LivroResponse buscarPorIsbn(@PathVariable("isbn") String isbn);
    @GetMapping(params = "titulo")
    List<LivroResponse> buscarPorTitulo(@RequestParam String titulo);
    @GetMapping
    List<LivroResponse> buscarTodos();
    @PutMapping("/{isbn}")
    LivroResponse atualizar(@PathVariable("isbn")String isbn,@Validated @RequestBody LivroRequest livroRequest);
    @DeleteMapping("/{isbn}")
    void deletar(@PathVariable("isbn") String isbn);
}
