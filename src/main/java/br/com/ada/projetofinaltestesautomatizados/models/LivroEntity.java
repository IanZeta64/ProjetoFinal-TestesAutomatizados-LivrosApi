package br.com.ada.projetofinaltestesautomatizados.models;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "livro_tb")
public class LivroEntity implements Livro{

    public LivroEntity(LivroRequest livroRequest) {
        this.titulo = livroRequest.titulo();
        this.resumo = livroRequest.resumo();
        this.sumario = livroRequest.resumo();
        this.numeroPaginas = livroRequest.numeroPaginas();
        this.dataPublicacao = LocalDate.parse(livroRequest.dataPublicacao(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "livro_isbn", nullable = false)
    private UUID isbn;

    @Column(name = "titulo")
    private String titulo;
    @Lob
    @Column(name = "resumo_clob", columnDefinition = "LONGVARCHAR")
    private String resumo;
    @Lob
    @Column(name = "sumario_clob", columnDefinition = "LONGVARCHAR")
    private String sumario;
    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    public LivroResponse toResponse() {
        return new LivroResponse(this.isbn, this.titulo, this.resumo, this.dataPublicacao);
    }

}
//    Um título
//    Um resumo do que vai ser encontrado no livro
//        Um sumário de tamanho livre.
//        Preço do livro
//        Número de páginas
//        Isbn(identificador do livro)
//        Data que ele deve entrar no ar(de publicação)
