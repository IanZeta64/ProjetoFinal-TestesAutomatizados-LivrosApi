package br.com.ada.projetofinaltestesautomatizados.requestTest;

import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LivroRequestTest {
    private LivroRequest livroRequest;

    @BeforeEach
    void setUp(){
        this.livroRequest = new LivroRequest("O Cortiço", BigDecimal.valueOf(23.34), "resumo", "sumario", 101, LocalDate.of(1993,10,1));
    }
    @Test
    void toEntity() {
        LivroEntity livroRequestToEntity = this.livroRequest.toEntity();
        assertEquals(UUID.class, livroRequestToEntity.getIsbn().getClass());
        assertNotNull(livroRequestToEntity.getCriacao());
        assertNull(livroRequestToEntity.getModificacao());
        assertTrue(livroRequestToEntity.getDisponivel());
    }
}
