package br.com.ada.projetofinaltestesautomatizados.models;

import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;

public class LivroEntityTest {
    private LivroEntity livroEntity;

    @BeforeEach
    void setUp(){
        this.livroEntity = new LivroEntity(1L, UUID.randomUUID(), "O Cortiço", BigDecimal.valueOf(23.34), "resumo",
                "sumario", 101, LocalDate.of(1993,10,1), Instant.now(), null, true);
    }
    @Test
    @DisplayName("Deve retornar Response - Teste Unitario")
    void deveRetornarResponse() {
       LivroResponse livroResponse = livroEntity.toResponse();
        assertEquals(livroEntity.getTitulo(), livroResponse.titulo());
        assertEquals(livroEntity.getPreco(), livroResponse.preco());
        assertEquals(livroEntity.getResumo(), livroResponse.resumo());
        assertEquals(livroEntity.getSumario(), livroResponse.sumario());
        assertEquals(livroEntity.getDataPublicacao(), LocalDate.parse(livroResponse.dataPublicacao(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertEquals(livroEntity.getIsbn(), livroResponse.isbn());
        assertEquals(livroEntity.getNumeroPaginas(), livroResponse.numeroPaginas());
        assertEquals(LivroResponse.class, livroResponse.getClass());

    }

    @Test
    @DisplayName("Deve atualizar entidade pelo request recebido - Teste Unitario")
    void deveAtualizarAEntidadePeloRequestRecebido() {
        LivroRequest livroRequest = new LivroRequest("O Hobbit", BigDecimal.valueOf(50.05), "lorem ipsum", "1. Introdução\n2. Desenvolvimento\n3. Conclusão", 101, LocalDate.of(2005,12,31));
        LivroEntity livroEntityUpdate = livroEntity.update(livroRequest);
        assertEquals("O Hobbit", livroEntityUpdate.getTitulo());
        assertEquals(BigDecimal.valueOf(50.05), livroEntityUpdate.getPreco());
        assertEquals("1. Introdução\n2. Desenvolvimento\n3. Conclusão", livroEntityUpdate.getSumario());
        assertEquals("lorem ipsum", livroEntityUpdate.getResumo());
        assertEquals(LocalDate.of(2005,12,31), livroEntityUpdate.getDataPublicacao());
        assertEquals(101, livroEntityUpdate.getNumeroPaginas());
        assertEquals(livroEntity.getCriacao(), livroEntityUpdate.getCriacao());
        assertEquals(true, livroEntityUpdate.getDisponivel());
        assertNotNull(livroEntityUpdate.getModificacao());

    }
    @Test
    @DisplayName("Deve testar construtor personalizado com valores nulos e nao nulos - Teste unitario")
    void deveTestarValoresNulosENaoNulosDoConstrutorPersonalizado(){
        LivroEntity livro = new LivroEntity("O Hobbit", BigDecimal.valueOf(50.05), "lorem ipsum", "1. Introdução\n2. Desenvolvimento\n3. Conclusão", 101, LocalDate.of(2005,12,31));;
        assertNotNull(livro.getIsbn());
        assertNotNull(livro.getDisponivel());
        assertNotNull(livro.getCriacao());
        assertNull(livro.getId());
        assertNull(livro.getModificacao());
    }

    @Test
    @DisplayName("Deve Testar Construtor Vazio - Teste Unitario")
    void deveTestarConstrutorVazio() {
        LivroEntity livroEnt = new LivroEntity();
        assertNull(livroEnt.getTitulo());
        assertNull(livroEnt.getNumeroPaginas());
        assertNull(livroEnt.getResumo());
        assertNull(livroEnt.getPreco());
        assertNull(livroEnt.getSumario());
        assertNull(livroEnt.getDataPublicacao());
    }

}
