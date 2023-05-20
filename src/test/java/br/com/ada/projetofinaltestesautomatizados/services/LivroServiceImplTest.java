package br.com.ada.projetofinaltestesautomatizados.services;

import br.com.ada.projetofinaltestesautomatizados.exceptions.LivroDuplicadoException;
import br.com.ada.projetofinaltestesautomatizados.exceptions.LivroNaoEncontradoException;
import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import br.com.ada.projetofinaltestesautomatizados.repositories.LivroRepository;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class LivroServiceImplTest {

    @InjectMocks
    private LivroServiceImpl livroService;
    @Mock
    private LivroRepository livroRepository;


    private static Stream<Arguments> gerarRequests() {
        return Stream.of(Arguments.of(new LivroRequest("O Cortiço", BigDecimal.valueOf(23.34), "resumo", "sumario", 101, LocalDate.of(2026, 10, 1))),
                Arguments.of(new LivroRequest("O Hobbit", BigDecimal.valueOf(77.31), "resumo", "sumario", 202, LocalDate.of(2025, 10, 1))),
                Arguments.of(new LivroRequest("O Alto da compadecida", BigDecimal.valueOf(44.85), "resumo", "sumario", 303, LocalDate.of(2024, 10, 1))));
    }

    private static Stream<Arguments> gerarUUID() {
        return Stream.of(Arguments.of(UUID.randomUUID().toString()), Arguments.of(UUID.randomUUID().toString()), Arguments.of(UUID.randomUUID().toString()));
    }

    @ParameterizedTest
    @MethodSource("gerarRequests")
    @DisplayName("Deve salvar livro - Service Mock")
    void deveSalvarLivro(LivroRequest livroRequest) {

        doReturn(livroRequest.toEntity()).when(livroRepository).save(any(LivroEntity.class));
        LivroResponse livroRetornado = livroService.salvar(livroRequest);

        assertTrue(assertEqualsMethod(livroRequest, livroRetornado));
    }


    @ParameterizedTest
    @MethodSource("gerarRequests")
    @DisplayName("Deve lancar Exceção ao salvar Livro Duplicado - Service Mock")
    void deveLancarExcecaoSalvarDuplicado(LivroRequest livroRequest) {
        doThrow(LivroDuplicadoException.class).when(livroRepository).save(any(LivroEntity.class));
        assertThrows(LivroDuplicadoException.class, () -> livroService.salvar(livroRequest));
    }

    @ParameterizedTest
    @MethodSource("gerarRequests")
    @DisplayName("Deve encontrar Livro por ISBN - Service mock")
    void deveBuscarLivroPorIsbn(LivroRequest livroRequest) {
        LivroEntity livroEntity = livroRequest.toEntity();
        doReturn(Optional.of(livroEntity)).when(livroRepository).findByIsbnAndDisponivelTrue(any(UUID.class));
        LivroResponse livroRetornado = livroService.buscarPorIsbn(livroEntity.getIsbn().toString());

        assertTrue(assertEqualsMethod(livroRequest, livroRetornado));
    }

    @ParameterizedTest
    @MethodSource("gerarUUID")
    @DisplayName("Deve lancar Exceção livro nao encontrado - Service mock")
    void deveLancarExcecaoBuscaIsbn(String isbn){
        doThrow(LivroNaoEncontradoException.class).when(livroRepository).findByIsbnAndDisponivelTrue(any(UUID.class));
        assertThrows(LivroNaoEncontradoException.class, () -> livroService.buscarPorIsbn(isbn));
    }

    @ParameterizedTest
    @MethodSource("gerarRequests")
    @DisplayName("Deve encontrar Livro por parte do tiulo - Service mock")
    void deveBuscarPorTitulo(LivroRequest livroRequest) {

        LivroEntity livroEntity = livroRequest.toEntity();
        doReturn(List.of(livroEntity)).when(livroRepository).findByTituloStartingWithAndDisponivelTrue(anyString());
        List<LivroResponse> livrosRetornados = livroService.buscarPorTitulo(livroEntity.getTitulo().substring(0,3));

        for (LivroResponse livroRetornado : livrosRetornados) {
            assertTrue(assertEqualsMethod(livroRequest, livroRetornado));
        }
    }

    @ParameterizedTest
    @MethodSource("gerarRequests")
    @DisplayName("Deve buscar todos os livros salvos - Service mock")
    void deveBuscarTodosOsLivros(LivroRequest livroRequest) {

        doReturn(List.of(livroRequest.toEntity())).when(livroRepository).findAllByDisponivelTrue();
        List<LivroResponse> livrosRetornados = livroService.buscarTodos();

        for (LivroResponse livroRetornado : livrosRetornados) {
            assertTrue(assertEqualsMethod(livroRequest, livroRetornado));
        }
    }

    @ParameterizedTest
    @MethodSource("gerarRequests")
    @DisplayName("Deve atualizar livro - Service mock")
    void deveAtualizarLivro(LivroRequest livroRequest) {

        LivroEntity livroEntity = livroRequest.toEntity();
        LivroRequest livroRequestAtualizado = new LivroRequest("O Cortico DOIS", BigDecimal.valueOf(22.22), "resumo2", "sumario2", 2022, LocalDate.of(2029,10,1));

        doReturn(livroEntity.update(livroRequestAtualizado)).when(livroRepository).save(any(LivroEntity.class));
        doReturn(Optional.of(livroEntity)).when(livroRepository).findByIsbnAndDisponivelTrue(any(UUID.class));
        LivroResponse livroRetornado = livroService.atualizar(livroEntity.getIsbn().toString(),livroRequestAtualizado);

        assertEquals(livroEntity.getIsbn(), livroRetornado.isbn());
        assertTrue(assertEqualsMethod(livroRequestAtualizado, livroRetornado));
    }

    @ParameterizedTest
    @MethodSource("gerarRequests")
    @DisplayName("Deve deletar livro - Service mock")
    void deveDeletarLivro(LivroRequest livroRequest) {
        LivroEntity livroEntity = livroRequest.toEntity();
        assertTrue(livroEntity.getDisponivel());
        doReturn(Optional.of(livroEntity)).when(livroRepository).findByIsbnAndDisponivelTrue(any(UUID.class));
        doReturn(livroEntity).when(livroRepository).save(any(LivroEntity.class));
        livroService.deletar(livroEntity.getIsbn().toString());
        assertFalse(livroEntity.getDisponivel());
    }
    @ParameterizedTest
    @MethodSource("gerarUUID")
    @DisplayName("Deve lancar Exceção livro nao encontrado - Service mock")
    void deveLancarExcecaoDeletar(String isbn){
        doThrow(LivroNaoEncontradoException.class).when(livroRepository).findByIsbnAndDisponivelTrue(any(UUID.class));
        assertThrows(LivroNaoEncontradoException.class, () -> livroService.deletar(isbn));
    }

    private static boolean assertEqualsMethod(LivroRequest livroRequest, LivroResponse livroRetornado) {
        try {
            assertEquals(livroRequest.getTitulo(), livroRetornado.titulo());
            assertEquals(livroRequest.getPreco(), livroRetornado.preco());
            assertEquals(livroRequest.getResumo(), livroRetornado.resumo());
            assertEquals(livroRequest.getSumario(), livroRetornado.sumario());
            assertEquals(livroRequest.getNumeroPaginas(), livroRetornado.numeroPaginas());
            assertEquals(livroRequest.getDataPublicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), livroRetornado.dataPublicacao());
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }
}