package br.com.ada.projetofinaltestesautomatizados.modelsTest;

import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivroEntityTest {
    private LivroEntity livroEntity;

    @BeforeEach
    void setUp(){
        this.livroEntity = new LivroEntity(1L, UUID.randomUUID(), "O Cortiço", BigDecimal.valueOf(23.34), "resumo",
                "sumario", 101, LocalDate.of(1993,10,1), Instant.now(), null, true);
    }
    @Test
    void toResponse() {

    }

    @Test
    void update() {
        LivroRequest livroRequest = new LivroRequest("O Hobbit", BigDecimal.valueOf(50.05), "lorem ipsum", "1. Introdução\n2. Desenvolvimento\n3. Conclusão", 101, LocalDate.of(2005,12,31));
        LivroEntity livroEntityUpdate = livroEntity.update(livroRequest);
        assertEquals(livroRequest.toEntity() ,livroEntityUpdate);
        assertEquals("O Hobbit", livroEntityUpdate.getTitulo());
        assertEquals(BigDecimal.valueOf(50.05), livroEntityUpdate.getPreco());
        assertEquals("1. Introdução\n2. Desenvolvimento\n3. Conclusão", livroEntityUpdate.getSumario());
        assertEquals("lorem ipsum", livroEntityUpdate.getResumo());
        assertEquals(LocalDate.of(2005,12,31), livroEntityUpdate.getDataPublicacao());
    }
}
