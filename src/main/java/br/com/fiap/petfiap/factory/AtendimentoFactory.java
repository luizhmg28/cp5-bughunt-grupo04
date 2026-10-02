package br.com.fiap.petfiap.factory;

import br.com.fiap.petfiap.model.Atendimento;
import br.com.fiap.petfiap.model.Banho;
import br.com.fiap.petfiap.model.ConsultaVeterinaria;
import br.com.fiap.petfiap.model.Tosa;

import java.time.LocalDateTime;

public class AtendimentoFactory {

    public static Atendimento criar(int protocolo, String tipo, String petNome, String petPorte, String tutorNome,
                                    LocalDateTime dataHora) {
        return switch (tipo) {
            case "BANHO" -> new Banho(protocolo, petNome, petPorte, tutorNome, dataHora);
            case "TOSA" -> new Tosa(protocolo, petNome, petPorte, tutorNome, dataHora);
            case "CONSULTA" -> new ConsultaVeterinaria(protocolo, petNome, petPorte, tutorNome, dataHora);
            default -> throw new IllegalArgumentException("Tipo invalido: " + tipo);
        };
    }
}
