package br.com.ada.projetofinaltestesautomatizados.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record LivroResponse(UUID isbn, String titulo, BigDecimal preco, String resumo, String sumario, String dataPublicacao) {}
