package br.com.fiap.petfiap.contract;

import br.com.fiap.petfiap.exception.StatusInvalidoException;
import br.com.fiap.petfiap.model.Banho;
import br.com.fiap.petfiap.model.Tosa;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegrasContratoTest {

    private static final LocalDateTime DATA = LocalDateTime.of(2026, 12, 1, 10, 0);

    @Test
    public void deveCalcularPrecoDoBanhoPorPorte() {
        assertEquals(60.0, new Banho(1, "Rex", "PEQUENO", "Ana", DATA).calcularPreco(), 0.001);
        assertEquals(80.0, new Banho(2, "Mimi", "MEDIO", "Bruno", DATA).calcularPreco(), 0.001);
        assertEquals(100.0, new Banho(3, "Thor", "GRANDE", "Carla", DATA).calcularPreco(), 0.001);
    }

    @Test
    public void deveDurar60MinutosNaTosa() {
        assertEquals(60, new Tosa(4, "Rex", "PEQUENO", "Ana", DATA).getDuracaoMinutos());
    }

    @Test
    public void deveCancelarAtendimentoAgendado() {
        Banho banho = new Banho(5, "Rex", "PEQUENO", "Ana", DATA);

        banho.cancelar();

        assertEquals("CANCELADO", banho.getStatus());
    }

    @Test
    public void deveRecusarCancelamentoDeAtendimentoConcluido() {
        Banho banho = new Banho(6, "Rex", "PEQUENO", "Ana", DATA);
        banho.concluir();

        assertThrows(StatusInvalidoException.class, banho::cancelar);
    }
}
