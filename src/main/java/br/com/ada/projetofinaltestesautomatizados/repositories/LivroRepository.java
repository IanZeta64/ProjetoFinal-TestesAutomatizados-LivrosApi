package br.com.ada.projetofinaltestesautomatizados.repositories;

import br.com.ada.projetofinaltestesautomatizados.models.Livro;
import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity, UUID> {
}
