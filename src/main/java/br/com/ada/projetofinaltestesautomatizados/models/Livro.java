package br.com.ada.projetofinaltestesautomatizados.models;

import br.com.ada.projetofinaltestesautomatizados.response.LivroResponse;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;


public interface Livro {


    public String getResumo();
    public void setResumo(String resumo);

    public String getTitulo();
    public void setTitulo(String titulo);

    public String getSumario();

    public void setSumario(String sumario);
    public LocalDate getDataPublicacao();

    public void setDataPublicacao(LocalDate localDate);

    public LivroResponse toResponse();
}
