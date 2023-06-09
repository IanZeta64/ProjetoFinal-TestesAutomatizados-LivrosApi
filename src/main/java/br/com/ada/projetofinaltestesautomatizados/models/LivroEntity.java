package br.com.ada.projetofinaltestesautomatizados.models;
import br.com.ada.projetofinaltestesautomatizados.request.LivroRequest;
import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "livro_tb")
public class LivroEntity {
    public LivroEntity(UUID isbn, String titulo, BigDecimal preco, String resumo, String sumario, Integer numeroPaginas,
                       LocalDate dataPublicacao, Instant criacao, Boolean disponivel) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.preco = preco;
        this.resumo = resumo;
        this.sumario = sumario;
        this.numeroPaginas = numeroPaginas;
        this.dataPublicacao = dataPublicacao;
        this.criacao = criacao;
        this.disponivel = disponivel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "isbn")
    private UUID isbn;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "preco")
    private BigDecimal preco;

    @NotBlank(message = "O resumo é obrigatorio.")
    @Size(max = 500, message = "O resumo deve ter no máximo 500 caracteres")
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
    @Column(name = "criacao")
    private Instant criacao;
    @Column(name = "modificacao")
    private Instant modificacao;
    @Column(name = "disponivel")
    private Boolean disponivel;

    public LivroResponse toResponse() {
        return new LivroResponse(this.isbn, this.titulo, this.preco, this.resumo, this.sumario, this.numeroPaginas,
                this.dataPublicacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public LivroEntity update(LivroRequest livroRequest) {
        setTitulo(livroRequest.getTitulo());
        setPreco(livroRequest.getPreco());
        setResumo(livroRequest.getResumo());
        setSumario(livroRequest.getSumario());
        setNumeroPaginas(livroRequest.getNumeroPaginas());
        setDataPublicacao(livroRequest.getDataPublicacao());
        setModificacao(Instant.now());
        return this;
    }
}
