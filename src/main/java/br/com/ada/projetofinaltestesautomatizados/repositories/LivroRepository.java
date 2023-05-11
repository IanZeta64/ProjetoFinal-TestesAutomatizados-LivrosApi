package br.com.ada.projetofinaltestesautomatizados.repositories;

import br.com.ada.projetofinaltestesautomatizados.models.Livro;
import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity, Long> {

    List<LivroEntity> findByTituloStartingWith(String titulo);

    Optional<LivroEntity> findByIsbn(UUID isbn);

    Boolean existsByIsbn(UUID isbn);

    Boolean existsByDataPublicacaoAndTitulo(LocalDate localDate, String titulo);

}
