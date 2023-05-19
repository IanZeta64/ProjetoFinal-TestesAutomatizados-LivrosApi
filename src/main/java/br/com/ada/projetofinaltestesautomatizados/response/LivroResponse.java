package br.com.ada.projetofinaltestesautomatizados.response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public record LivroResponse(UUID isbn, String titulo, BigDecimal preco, String resumo, String sumario, Integer numeroPaginas, String dataPublicacao) {
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof LivroResponse that)) return false;
//        return titulo.equals(that.titulo) && dataPublicacao.equals(that.dataPublicacao);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(titulo, dataPublicacao);
//    }
}
