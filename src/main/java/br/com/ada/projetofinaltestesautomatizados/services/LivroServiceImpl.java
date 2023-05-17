package br.com.ada.projetofinaltestesautomatizados.services;
import br.com.ada.projetofinaltestesautomatizados.exceptions.LivroDuplicadoException;
import br.com.ada.projetofinaltestesautomatizados.exceptions.LivroNaoEncontradoException;
import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import br.com.ada.projetofinaltestesautomatizados.repositories.LivroRepository;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService{
    private final LivroRepository repository;

    @Override
    public LivroResponse salvar(LivroRequest livroRequest) {
       if (!repository.existsByDataPublicacaoAndTituloAndDisponivelTrue(livroRequest.getDataPublicacao(), livroRequest.getTitulo())){
           LivroEntity livro =  livroRequest.toEntity();
           return repository.save(livro).toResponse();
       }else {
           throw new LivroDuplicadoException("Livro ja cadastrado");
       }
    }

    @Override
    public LivroResponse buscarPorId(String isbn) {
        return repository.findByIsbnAndDisponivelTrue(UUID.fromString(isbn))
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro nao encontrado")).toResponse();
    }

    @Override
    public List<LivroResponse> buscarPorTitulo(String titulo) {
        return repository.findByTituloStartingWithAndDisponivelTrue(titulo).stream().map(LivroEntity::toResponse).toList();
    }

    @Override
    public List<LivroResponse> buscarTodos() {
        return repository.findAllByDisponivelTrue().stream().map(LivroEntity::toResponse).toList();
    }

    @Override
    public LivroResponse atualizar(String isbn, LivroRequest livroRequest) {
        LivroEntity livroEntity = repository.findByIsbnAndDisponivelTrue(UUID.fromString(isbn)).orElseThrow(() -> new LivroNaoEncontradoException("Livro nao encontrado"));
        return repository.save(livroEntity.update(livroRequest)).toResponse();
        }


    @Override
    public void deletar(String isbn) {
        repository.findByIsbnAndDisponivelTrue(UUID.fromString(isbn))
                .ifPresentOrElse(livroEntity -> {livroEntity.setDisponivel(false);
                    repository.save(livroEntity);}
                    , () -> {
                    throw new LivroNaoEncontradoException("Livro nao Encontrado");
                });
    }
}
