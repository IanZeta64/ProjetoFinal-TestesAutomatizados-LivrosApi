package br.com.ada.projetofinaltestesautomatizados.models;

import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;


public class LivroEntityTest {


    @ParameterizedTest
    @MethodSource("gerarEntities")
    @DisplayName("Deve retornar Response - Teste Unitario")
    void deveRetornarResponse(LivroEntity livroEntity) {
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

    @ParameterizedTest
    @MethodSource("gerarEntities")
    @DisplayName("Deve atualizar entidade pelo request recebido - Teste Unitario")
    void deveAtualizarAEntidadePeloRequestRecebido(LivroEntity livroEntity) {
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
    @ParameterizedTest
    @MethodSource("gerarEntities")
    @DisplayName("Deve testar construtor personalizado com valores nulos e nao nulos - Teste unitario")
    void deveTestarValoresNulosENaoNulosDoConstrutorPersonalizado(LivroEntity livroEntity){
//        LivroEntity livro = new LivroEntity("Lorem Ipsum", BigDecimal.valueOf(50.05), "lorem ipsum", "1. Introdução\n2. Desenvolvimento\n3. Conclusão", 999, LocalDate.of(2033,12,31));;
        assertNotNull(livroEntity.getIsbn());
        assertNotNull(livroEntity.getDisponivel());
        assertNotNull(livroEntity.getCriacao());
        assertNull(livroEntity.getId());
        assertNull(livroEntity.getModificacao());
    }

    private static Stream<Arguments> gerarEntities(){
        return Stream.of( Arguments.of(new LivroEntity("O Cortiço", BigDecimal.valueOf(23.34), "resumo", "sumario", 101, LocalDate.of(2026,10,1)),
                Arguments.of(new LivroEntity("O Hobbit", BigDecimal.valueOf(77.31), "resumo", "sumario", 202, LocalDate.of(2025,10,1))),
                Arguments.of(new LivroEntity("O Alto da compadecida", BigDecimal.valueOf(44.85), "resumo", "sumario", 303, LocalDate.of(2024,10,1)))));
    }



}
