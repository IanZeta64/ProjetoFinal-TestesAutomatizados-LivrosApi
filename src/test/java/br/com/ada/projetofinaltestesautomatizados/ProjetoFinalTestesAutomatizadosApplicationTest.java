package br.com.ada.projetofinaltestesautomatizados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjetoFinalTestesAutomatizadosApplicationTest {
//teste de commit
    @Test
    void main() {
     assertDoesNotThrow(() -> ProjetoFinalTestesAutomatizadosApplication.main(new String[0]));
    }
}