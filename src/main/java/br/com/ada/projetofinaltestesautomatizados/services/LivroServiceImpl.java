package br.com.ada.projetofinaltestesautomatizados.services;

import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import br.com.ada.projetofinaltestesautomatizados.repositories.LivroRepository;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService{
    private final LivroRepository repository;
    @Override
    public LivroResponse salvar(LivroRequest livroRequest) {
//        LivroEntity livroEntity = new LivroEntity(livroRequest);
        return repository.save(new LivroEntity(livroRequest)).toResponse();
    }

    @Override
    public LivroResponse buscarPorIsbn(String isbn) {
        return null;
    }

    @Override
    public Optional<LivroResponse> buscarPorTitulo(String titulo) {
        return Optional.empty();
    }

    @Override
    public List<LivroResponse> buscarTodos() {
        return null;
    }

    @Override
    public LivroResponse atualizar(String isbn, LivroRequest livroRequest) {
        return null;
    }

    @Override
    public void deletar(String isbn) {

    }
}
