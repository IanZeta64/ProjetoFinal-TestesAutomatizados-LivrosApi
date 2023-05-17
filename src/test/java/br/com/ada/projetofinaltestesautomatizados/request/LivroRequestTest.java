package br.com.ada.projetofinaltestesautomatizados.request;

import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Deve retornar entidade - Teste unitario")
    void deveRetornarEntidade() {
        LivroEntity livroRequestToEntity = this.livroRequest.toEntity();
        assertEquals(livroRequest.getTitulo(), livroRequestToEntity.getTitulo());
        assertEquals(livroRequest.getSumario(), livroRequestToEntity.getSumario());
        assertEquals(livroRequest.getResumo(), livroRequestToEntity.getResumo());
        assertEquals(livroRequest.getPreco(), livroRequestToEntity.getPreco());
        assertEquals(livroRequest.getDataPublicacao(), livroRequestToEntity.getDataPublicacao());
        assertEquals(livroRequest.getNumeroPaginas(), livroRequestToEntity.getNumeroPaginas());
        assertEquals(UUID.class, livroRequestToEntity.getIsbn().getClass());
        assertEquals(LivroEntity.class, livroRequestToEntity.getClass());
        assertNotNull(livroRequestToEntity.getCriacao());
        assertNull(livroRequestToEntity.getModificacao());
        assertTrue(livroRequestToEntity.getDisponivel());
    }

    @Test
    @DisplayName("Deve Testar Construtor Vazio - Teste unitario")
    void deveTestarConstrutorVazio() {
        LivroRequest livroReq = new LivroRequest();
        assertNull(livroReq.getTitulo());
        assertNull(livroReq.getNumeroPaginas());
        assertNull(livroReq.getResumo());
        assertNull(livroReq.getPreco());
        assertNull(livroReq.getSumario());
        assertNull(livroReq.getDataPublicacao());
    }
}
