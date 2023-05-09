package br.com.ada.projetofinaltestesautomatizados.request;
import java.time.LocalDate;


public record LivroRequest(String titulo, String resumo, String sumario, Integer numeroPaginas, String dataPublicacao) {}