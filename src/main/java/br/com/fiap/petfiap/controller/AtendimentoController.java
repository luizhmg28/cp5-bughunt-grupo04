package br.com.fiap.petfiap.controller;

import br.com.fiap.petfiap.exception.AtendimentoNaoEncontradoException;
import br.com.fiap.petfiap.exception.HorarioOcupadoException;
import br.com.fiap.petfiap.exception.StatusInvalidoException;
import br.com.fiap.petfiap.builder.AtendimentoBuilder;
import br.com.fiap.petfiap.model.Atendimento;
import br.com.fiap.petfiap.model.GeradorProtocolo;
import br.com.fiap.petfiap.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    @Autowired
    private AgendaService service;

    @PostMapping
    public ResponseEntity<Atendimento> agendar(
            @RequestParam String tipo,
            @RequestParam String petNome,
            @RequestParam String porte,
            @RequestParam String tutorNome,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHora) {
        try {
            int protocolo = GeradorProtocolo.getInstancia().proximo();
            Atendimento atendimento = new AtendimentoBuilder()
                    .comTipo(tipo)
                    .comPet(petNome, porte)
                    .comTutor(tutorNome)
                    .comDataHora(dataHora)
                    .construir(protocolo);
            return ResponseEntity.status(201).body(service.agendar(atendimento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (HorarioOcupadoException e) {
            return ResponseEntity.status(409).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atendimento> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (AtendimentoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pet/{nome}")
    public List<Atendimento> buscarPorPet(@PathVariable String nome) {
        return service.buscarPorPet(nome);
    }

    @GetMapping("/{id}/resumo")
    public ResponseEntity<Map<String, Object>> resumo(@PathVariable Long id) {
        try {
            Atendimento atendimento = service.buscarPorId(id);
            return ResponseEntity.ok(Map.of(
                    "tipo", atendimento.getTipo(),
                    "preco", atendimento.calcularPreco(),
                    "pontosFidelidade", atendimento.calcularPontosFidelidade(),
                    "duracaoMinutos", atendimento.getDuracaoMinutos()));
        } catch (AtendimentoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/conclusao")
    public ResponseEntity<Atendimento> concluir(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.concluir(id));
        } catch (AtendimentoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (StatusInvalidoException e) {
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/{id}/cancelamento")
    public ResponseEntity<Atendimento> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.cancelar(id));
        } catch (AtendimentoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (StatusInvalidoException e) {
            return ResponseEntity.status(409).build();
        }
    }

}
