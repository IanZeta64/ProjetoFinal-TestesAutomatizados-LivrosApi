package br.com.ada.projetofinaltestesautomatizados.request;
import br.com.ada.projetofinaltestesautomatizados.models.LivroEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LivroRequest {
    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "20", message = "O preço mínimo é de 20")
    private BigDecimal preco;

    @NotBlank(message = "O resumo é obrigatório")
    @Size(max = 500, message = "O resumo deve ter no máximo 500 caracteres")
    private String resumo;

    private String sumario;

    @NotNull(message = "O número de páginas é obrigatório")
    @Min(value = 100, message = "O número mínimo de páginas é 100")
    private Integer numeroPaginas;

    @Future(message = "A data de publicação deve estar no futuro")
    private LocalDate dataPublicacao;

    public LivroEntity toEntity() {
        return new LivroEntity(this.titulo, this.preco, this.resumo, this.sumario, this.numeroPaginas, this.dataPublicacao);
    }
}


//public record LivroRequest(@NotBlank(message = "O título é obrigatório") String titulo,
//                           @NotNull(message = "O preço é obrigatório")
//                           @DecimalMin(value = "20", message = "O preço mínimo é de 20") BigDecimal preco,
//                           @NotBlank(message = "O resumo é obrigatório")
//                           @Size(max = 500, message = "O resumo deve ter no máximo 500 caracteres")
//                           String resumo,
//                           String sumario,
//                           @NotNull(message = "O número de páginas é obrigatório")
//                           @Min(value = 100, message = "O número mínimo de páginas é 100")Integer numeroPaginas,
//                           @Future(message = "A data de publicação deve estar no futuro") LocalDate dataPublicacao) {}