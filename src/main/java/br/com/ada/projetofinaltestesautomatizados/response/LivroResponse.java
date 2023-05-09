package br.com.ada.projetofinaltestesautomatizados.response;

import java.time.LocalDate;
import java.util.UUID;

public record LivroResponse(UUID isbn, String titulo, String resumo, LocalDate dataPublicacao) {
}
