package br.com.ada.projetofinaltestesautomatizados.controllerTest;

import br.com.ada.projetofinaltestesautomatizados.ProjetoFinalTestesAutomatizadosApplication;
import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest(classes = ProjetoFinalTestesAutomatizadosApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LivroControllerIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;
    private LivroRequest livroRequest;
    private LivroEntity livroEntity;
    private LivroResponse livroResponse;

    @BeforeEach
    void setUp() {
        livroRequest = new LivroRequest("O Cortico", BigDecimal.valueOf(23.34), "resumo", "sumario", 101, LocalDate.of(2023, 06, 05));
        livroEntity = this.livroRequest.toEntity();
        livroResponse = this.livroEntity.toResponse();
    }

    @Test
    @DisplayName("Deve adicionar um livro - Teste Integração")
    void deveAdicionarUmLivroMockMvc() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String livroRequestJson = mapper.writeValueAsString(livroRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())

                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("O Cortico"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/livros"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Deve remover um livro - Teste Integração")
    void deveRemoverUmLivroMockMvc() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String livroRequestJson = mapper.writeValueAsString(livroRequest);

        var mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/livros")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(livroRequestJson))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.titulo")
                                .value("O Cortico"))
                        .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/livros"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());

        mvcResult.getResponse().getContentAsString();
        var livroResponseRecuperado = mapper.readValue(mvcResult.getResponse().getContentAsString(), LivroResponse.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/livros/{isbn}", livroResponseRecuperado.isbn().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroRequestJson))
                .andDo(print())

                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/livros"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}
