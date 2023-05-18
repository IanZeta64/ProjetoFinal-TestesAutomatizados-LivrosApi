package br.com.ada.projetofinaltestesautomatizados.controllers;

import br.com.ada.projetofinaltestesautomatizados.exceptions.ExceptionHandlerControllerAdvice;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import br.com.ada.projetofinaltestesautomatizados.services.LivroServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.web.servlet.function.ServerResponse.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class LivroControllerImplTest {

    @InjectMocks
    private LivroControllerImpl controller;
    @Mock
    private LivroServiceImpl service;

    private MockMvc mockMvc;
    private LivroRequest livroRequest;
    private LivroResponse livroResponse;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.livroRequest = new LivroRequest("O Hobbit", BigDecimal.valueOf(77.31), "resumo", "sumario", 202, LocalDate.of(2025,10,1));
        this.livroResponse = this.livroRequest.toEntity().toResponse();
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
    @Test
    @DisplayName("Deve retornar bad request - Mock Mvc")
    void deveRetornarBadRequestMockMvc() throws Exception {
        LivroRequest livroRequestSemValidacao = new LivroRequest("O Hobbit", BigDecimal.valueOf(17.31),
                "nunc mi ipsum faucibus vitae aliquet nec ullamcorper sit amet risus nullam eget felis eget nunc lobortis mattis aliquam faucibus purus in massa tempor" +
                        " nec feugiat nisl pretium fusce id velit ut tortor pretium viverra suspendisse potenti nullam ac tortor vitae purus faucibus ornare suspendisse sed nisi" +
                        " lacus sed viverra tellus in hac habitasse platea dictumst vestibulum rhoncus est pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat" +
                        " vivamus at augue eget arcu dictum varius duis at consectetur lorem donec massa sapien faucibus et molestie ac feugiat sed lectus vestibulum mattis ullamcorper" +
                        " velit sed ullamcorper morbi tincidunt ornare massa eget egestas",
                "sumario", 99, LocalDate.of(2005, 10, 1));


        String livroRequestJson = mapper.writeValueAsString(livroRequestSemValidacao);


        this.mockMvc.perform(post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON).content(livroRequestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @DisplayName("Deve salvar Livro - Mock Mvc")
    void deveSalvarLivroMockMvc() throws Exception {

        ArgumentCaptor<LivroRequest> requestCaptor = ArgumentCaptor.forClass(LivroRequest.class); //Usando para ignorar o atributo gerado automatico de isbn
        doReturn(livroResponse).when(service).salvar(requestCaptor.capture());

        String livroRequestJson =  mapper.writeValueAsString(livroRequest);
        String livroResponseJson = mapper.writeValueAsString(livroResponse);

       this.mockMvc.perform(post("/api/v1/livros")
                .contentType(MediaType.APPLICATION_JSON).content(livroRequestJson))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(livroResponseJson))
                .andExpect(jsonPath("$.isbn").value(Matchers.hasLength(36)));

        verify(service).salvar(eq(requestCaptor.getValue()));


    }

    @Test
    @DisplayName("Deve buscar Livro pelo ISBN - Mock Mvc")
    void deveBuscarLivroPeloIsbn() throws Exception {
        String isbn = livroResponse.isbn().toString();
        String livroResponseJson = mapper.writeValueAsString(livroResponse);

        doReturn(livroResponse).when(service).buscarPorIsbn(isbn);

        this.mockMvc.perform(get("/api/v1/livros/{isbn}", isbn))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(livroResponseJson))
                .andExpect(jsonPath("$.isbn").value(isbn));

        verify(service).buscarPorIsbn(isbn);
    }

    @Test
    @DisplayName("Deve buscar Livro por Titulo - Mock Mvc")
    void deveBuscarLivroPorTitulo() throws Exception {
        String titulo = livroResponse.titulo();
        String livroResponseJson = mapper.writeValueAsString(List.of(livroResponse));

        doReturn(List.of(livroResponse)).when(service).buscarPorTitulo(titulo);

        this.mockMvc.perform(get("/api/v1/livros").param("titulo", titulo))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(livroResponseJson))
                .andExpect(jsonPath("$.[0].titulo").value(titulo));

        verify(service).buscarPorTitulo(titulo);
    }

    @Test
    @DisplayName("Deve buscar Todos os Livros - Mock Mvc")
    void buscarTodos() throws Exception {
        String livroResponseJson = mapper.writeValueAsString(List.of(livroResponse));

        doReturn(List.of(livroResponse)).when(service).buscarTodos();

        this.mockMvc.perform(get("/api/v1/livros"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(livroResponseJson))
                .andExpect(jsonPath("$").isNotEmpty());

        verify(service).buscarTodos();
    }

    @Test
    @DisplayName("Deve atualizar livro - Mock Mvc")
    void atualizar() throws Exception {

        LivroRequest livroRequestAtualizar = new LivroRequest("O Cortico", BigDecimal.valueOf(22.22), "resumo2", "sumario2", 2022, LocalDate.of(2029,10,1));
        LivroResponse livroResponseAtualizar = livroRequestAtualizar.toEntity().toResponse();
        String isbn = livroResponseAtualizar.isbn().toString();
        String livroRequestJson =  mapper.writeValueAsString(livroRequestAtualizar);

        ArgumentCaptor<LivroRequest> requestCaptor = ArgumentCaptor.forClass(LivroRequest.class); //Usando para ignorar o atributo gerado automatico de isbn
        doReturn(livroResponseAtualizar).when(service).atualizar(any(), requestCaptor.capture());

        this.mockMvc.perform(put("/api/v1/livros/{isbn}", isbn)
                        .contentType(MediaType.APPLICATION_JSON).content(livroRequestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.titulo").value("O Cortico"))
                .andExpect(jsonPath("$.preco").value(22.22))
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.resumo").value("resumo2"))
                .andExpect(jsonPath("$.sumario").value("sumario2"))
                .andExpect(jsonPath("$.numeroPaginas").value(2022))
                .andExpect(jsonPath("$.dataPublicacao").value("01/10/2029"));


        verify(service).atualizar(isbn, requestCaptor.getValue());
    }


    @Test
    void deletar() {
    }
}