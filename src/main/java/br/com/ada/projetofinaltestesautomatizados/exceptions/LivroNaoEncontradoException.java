package br.com.ada.projetofinaltestesautomatizados.exceptions;

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(String message){
        super(message);
    }
}
