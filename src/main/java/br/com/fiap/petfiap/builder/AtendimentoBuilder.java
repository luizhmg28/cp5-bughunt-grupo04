package br.com.fiap.petfiap.builder;

import br.com.fiap.petfiap.factory.AtendimentoFactory;
import br.com.fiap.petfiap.model.Atendimento;

import java.time.LocalDateTime;

public class AtendimentoBuilder {

    private String tipo;
    private String petNome;
    private String petPorte;
    private String tutorNome;
    private LocalDateTime dataHora;

    public AtendimentoBuilder comTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public AtendimentoBuilder comPet(String petNome, String petPorte) {
        this.petNome = petNome;
        this.petPorte = petPorte;
        return this;
    }

    public AtendimentoBuilder comTutor(String tutorNome) {
        this.tutorNome = tutorNome;
        return this;
    }

    public AtendimentoBuilder comDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
        return this;
    }

    public Atendimento construir(int protocolo) {
        if (petNome == null || petNome.isBlank()) {
            throw new IllegalArgumentException("Nome do pet e obrigatorio");
        }
        if (petPorte == null || petPorte.isBlank()) {
            throw new IllegalArgumentException("Porte do pet e obrigatorio");
        }
        return AtendimentoFactory.criar(protocolo, tipo, petNome, petPorte, tutorNome, dataHora);
    }
}
