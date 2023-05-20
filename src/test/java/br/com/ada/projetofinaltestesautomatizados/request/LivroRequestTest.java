package br.com.ada.projetofinaltestesautomatizados.request;

import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
public class LivroRequestTest {

    @ParameterizedTest
    @MethodSource("gerarRequests")
    @DisplayName("Deve retornar entidade - Teste unitario")
    void deveRetornarEntidade(LivroRequest livroRequest) {
        LivroEntity livroRequestToEntity = livroRequest.toEntity();
        assertEquals(livroRequest.getTitulo(), livroRequestToEntity.getTitulo());
        assertEquals(livroRequest.getSumario(), livroRequestToEntity.getSumario());
        assertEquals(livroRequest.getResumo(), livroRequestToEntity.getResumo());
        assertEquals(livroRequest.getPreco(), livroRequestToEntity.getPreco());
        assertEquals(livroRequest.getDataPublicacao(), livroRequestToEntity.getDataPublicacao());
        assertEquals(livroRequest.getNumeroPaginas(), livroRequestToEntity.getNumeroPaginas());
        assertEquals(LivroEntity.class, livroRequestToEntity.getClass());
        assertNotNull(livroRequestToEntity.getIsbn().getClass());
        assertNotNull(livroRequestToEntity.getCriacao());
        assertNull(livroRequestToEntity.getModificacao());
        assertTrue(livroRequestToEntity.getDisponivel());
    }


    private static Stream<Arguments> gerarRequests(){
        return Stream.of( Arguments.of(new LivroRequest("O Cortiço", BigDecimal.valueOf(23.34), "resumo", "sumario", 101, LocalDate.of(2026,10,1))),
                Arguments.of(new LivroRequest("O Hobbit", BigDecimal.valueOf(77.31), "resumo", "sumario", 202, LocalDate.of(2025,10,1))),
                Arguments.of(new LivroRequest("O Alto da compadecida", BigDecimal.valueOf(44.85), "resumo", "sumario", 303, LocalDate.of(2024,10,1))));
    }
}
