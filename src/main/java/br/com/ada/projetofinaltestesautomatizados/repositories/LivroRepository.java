package br.com.ada.projetofinaltestesautomatizados.repositories;
import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity, Long> {
    List<LivroEntity> findByTituloStartingWithAndDisponivelTrue(String titulo);
    Optional<LivroEntity> findByIsbnAndDisponivelTrue(UUID isbn);
    Boolean existsByDataPublicacaoAndTituloAndDisponivelTrue(LocalDate localDate, String titulo);

    List<LivroEntity> findAllByDisponivelTrue();

    Boolean existsByIsbnAndDisponivelFalse(UUID isbn);

}
