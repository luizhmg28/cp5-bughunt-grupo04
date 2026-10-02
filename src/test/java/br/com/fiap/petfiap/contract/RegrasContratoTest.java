package br.com.fiap.petfiap.contract;

import br.com.fiap.petfiap.model.Banho;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegrasContratoTest {

    private static final LocalDateTime DATA = LocalDateTime.of(2026, 12, 1, 10, 0);

    @Test
    public void deveCalcularPrecoDoBanhoPorPorte() {
        assertEquals(60.0, new Banho(1, "Rex", "PEQUENO", "Ana", DATA).calcularPreco(), 0.001);
        assertEquals(80.0, new Banho(2, "Mimi", "MEDIO", "Bruno", DATA).calcularPreco(), 0.001);
        assertEquals(100.0, new Banho(3, "Thor", "GRANDE", "Carla", DATA).calcularPreco(), 0.001);
    }
}
