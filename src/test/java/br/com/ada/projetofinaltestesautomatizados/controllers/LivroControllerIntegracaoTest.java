package br.com.ada.projetofinaltestesautomatizados.controllers;
import br.com.ada.projetofinaltestesautomatizados.ProjetoFinalTestesAutomatizadosApplication;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = ProjetoFinalTestesAutomatizadosApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LivroControllerIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;
    private LivroRequest livroRequest;
    private ObjectMapper mapper;
    private String livroRequestJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        livroRequest = new LivroRequest("O Cortico", BigDecimal.valueOf(23.34), "resumo", "sumario", 101, LocalDate.of(2023, 6, 5));
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.livroRequestJson = mapper.writeValueAsString(this.livroRequest);
    }

    @Test
    @DisplayName("Deve adicionar um livro - Teste Integração")
    void deveAdicionarUmLivroMockMvc() throws Exception {

        mockMvc.perform(post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("O Cortico"));

        mockMvc.perform(get("/api/v1/livros"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Deve remover um livro - Teste Integração")
    void deveRemoverUmLivroMockMvc() throws Exception {

        var mvcResult =
                mockMvc.perform(post("/api/v1/livros")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(livroRequestJson))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.titulo")
                                .value("O Cortico"))
                        .andReturn();

        mockMvc.perform(get("/api/v1/livros"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        var livroResponseRecuperado = mapper.readValue(mvcResult.getResponse().getContentAsString(), LivroResponse.class);
        mockMvc.perform(delete("/api/v1/livros/{isbn}", livroResponseRecuperado.isbn().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())

                .andExpect(status().isNoContent());


        mockMvc.perform(get("/api/v1/livros"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Deve buscar livros por ISBN - Teste Integração")
    void deveBuscarLivrosPorIsbn() throws Exception {

       MvcResult mvcResult = mockMvc.perform(post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

       LivroResponse livroResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), LivroResponse.class);

        mockMvc.perform(get("/api/v1/livros/{isbn}", livroResponse.isbn()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.isbn").value(livroResponse.isbn().toString()));

    }

    @Test
    @DisplayName("Deve buscar livros por titulo - Teste Integração")
    void deveBuscarLivrosPorTitulo() throws Exception {

        mockMvc.perform(post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/livros").param("titulo", livroRequest.getTitulo().substring(0, 4))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar um livro - Teste Integração")
    void deveAtualizarUmLivroMockMvc() throws Exception {

        var mvcResult =
                mockMvc.perform(post("/api/v1/livros")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(livroRequestJson))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.titulo")
                                .value("O Cortico"))
                        .andReturn();

        mockMvc.perform(get("/api/v1/livros"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());


        var livroResponseRecuperado = mapper.readValue(mvcResult.getResponse().getContentAsString(), LivroResponse.class);

        LivroRequest livroRequestAtualizado = new LivroRequest("O Cortico 2", BigDecimal.valueOf(22.22), "resumo2", "sumario2", 222, LocalDate.of(2222, 2, 2));
        String livroRequestAtualizadoJson = mapper.writeValueAsString(livroRequestAtualizado);
        mockMvc.perform(put("/api/v1/livros/{isbn}", livroResponseRecuperado.isbn().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestAtualizadoJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(livroResponseRecuperado.isbn().toString()))
                .andExpect(jsonPath("$.titulo").value("O Cortico 2"))
                .andExpect(jsonPath("$.dataPublicacao").value("02/02/2222"));

        mockMvc.perform(get("/api/v1/livros/{isbn}", livroResponseRecuperado.isbn().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Deve receber mensagem de livro ja dacastrado e retornar erro 409 - Teste Intgracao")
    void deveRecebermensagemdeLivroCadastrado() throws Exception {

            mockMvc.perform(post("/api/v1/livros")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(livroRequestJson))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.titulo").value("O Cortico"));

            mockMvc.perform(get("/api/v1/livros"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isNotEmpty());

        mockMvc.perform(post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").value("Livro ja cadastrado"));

    }

    @Test
    @DisplayName("Deve receber mensagem de livro ja nao encontrado e retornar erro 404 - Teste Intgracao")
    void deveRecebermensagemdeLivroNaoEncontrado() throws Exception {

        mockMvc.perform(get("/api/v1/livros/{isbn}", UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Livro não encontrado"));

    }

    @Test
    @DisplayName("Deve receber mensagem de campo invalido por valores invalidos - Teste Intgracao")
    void deveRecebermensagemdeCampoInvalidoPorValoresInvalidos() throws Exception {

        LivroRequest livroRequestSemValidacao = new LivroRequest("O Hobbit", BigDecimal.valueOf(5.31),
                "nunc mi ipsum faucibus vitae aliquet nec ullamcorper sit amet risus nullam eget felis eget nunc lobortis mattis aliquam faucibus purus in massa tempor" +
                        " nec feugiat nisl pretium fusce id velit ut tortor pretium viverra suspendisse potenti nullam ac tortor vitae purus faucibus ornare suspendisse sed nisi" +
                        " lacus sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus est pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat" +
                        " vivamus at augue eget arcu dictum varius duis at consectetur lorem donec massa sapien faucibus et molestie ac feugiat sed lectus vestibulum mattis ullamcorper" +
                        " velit sed ullamcorper morbi tincidunt ornare massa eget egestas",

                "sumario", 99, LocalDate.of(2005, 10, 1));
        livroRequestJson = mapper.writeValueAsString(livroRequestSemValidacao);

       var mvcResult = mockMvc.perform(post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        List<String> errosEsperados = Stream.of("Campo inválido: 'numeroPaginas'. Causa: 'O número mínimo de páginas é 100'.",
                "Campo inválido: 'preco'. Causa: 'O preço mínimo é de 20'.",
                "Campo inválido: 'resumo'. Causa: 'O resumo deve ter no máximo 500 caracteres'.",
                "Campo inválido: 'dataPublicacao'. Causa: 'A data de publicação deve estar no futuro'.").sorted().toList();
       List<String> errosCapturados = Arrays.stream(mvcResult.getResponse().getContentAsString().split(",")).map(String::trim).sorted().toList();

        for (int i = 0; i < errosEsperados.size(); i++) {
            assertEquals(errosEsperados.get(i), errosCapturados.get(i));
        }
    }

    @Test
    @DisplayName("Deve receber mensagem de campo invalido por campos vazios - Teste Intgracao")
    void deveRecebermensagemdeCampoInvalidoPorCcamposVazios() throws Exception {

        LivroRequest livroRequestSemValidacao = new LivroRequest();
        livroRequestJson = mapper.writeValueAsString(livroRequestSemValidacao);

        var mvcResult = mockMvc.perform(post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        List<String> errosEsperados = Stream.of( "Campo inválido: 'preco'. Causa: 'O preço é obrigatório'.",
                "Campo inválido: 'titulo'. Causa: 'O título é obrigatório'.",
                "Campo inválido: 'resumo'. Causa: 'O resumo é obrigatório'.",
                "Campo inválido: 'numeroPaginas'. Causa: 'O número de páginas é obrigatório'.").sorted().toList();
        List<String> errosCapturados = Arrays.stream(mvcResult.getResponse().getContentAsString().split(",")).map(String::trim).sorted().toList();
        for (int i = 0; i < errosEsperados.size(); i++) {
            assertEquals(errosEsperados.get(i), errosCapturados.get(i));
        }
    }


}
