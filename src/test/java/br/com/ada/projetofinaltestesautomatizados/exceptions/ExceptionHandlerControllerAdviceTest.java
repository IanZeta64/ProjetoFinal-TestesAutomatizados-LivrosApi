package br.com.ada.projetofinaltestesautomatizados.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerControllerAdviceTest {

    private ExceptionHandlerControllerAdvice handler;

    @BeforeEach
    void setUp() {
        this.handler = new ExceptionHandlerControllerAdvice();
    }

    @Test
    void handleLivroNaoEncontradoException() {
        LivroNaoEncontradoException exception = new LivroNaoEncontradoException("Livro não encontrado");
        ResponseEntity<Object> response = handler.handleLivroNaoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Livro não encontrado", response.getBody());
    }

    @Test
    void handleLivroDuplicadoException() {
        LivroDuplicadoException exception = new LivroDuplicadoException("Livro duplicado");
        ResponseEntity<Object> response = handler.handleLivroDuplicadoException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Livro duplicado", response.getBody());
    }

    @Test
    void handleMethodArgumentNotValid(){
        WebRequest request = null;
        HttpHeaders headers = new HttpHeaders();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(null, "livroRequest");
        FieldError fieldError1 = new FieldError("livroRequest", "titulo", "O título é obrigatório");
        FieldError fieldError2 = new FieldError("livroRequest", "preco", "O preço é obrigatório");
        bindingResult.addError(fieldError1);
        bindingResult.addError(fieldError2);

        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(
                new MethodArgumentNotValidException((MethodParameter) null, bindingResult), headers, HttpStatus.BAD_REQUEST, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Campo inválido: 'titulo'. Causa: 'O título é obrigatório'., Campo inválido: 'preco'. Causa: 'O preço é obrigatório'.",
                response.getBody());
    }
}