package br.com.ada.projetofinaltestesautomatizados;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ProjetoFinalTestesAutomatizadosApplicationTest {

    @Test
    void main() {
     assertDoesNotThrow(() -> ProjetoFinalTestesAutomatizadosApplication.main(new String[0]));
    }
}