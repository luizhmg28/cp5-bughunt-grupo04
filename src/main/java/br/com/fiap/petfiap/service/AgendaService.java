package br.com.fiap.petfiap.service;

import br.com.fiap.petfiap.exception.AtendimentoNaoEncontradoException;
import br.com.fiap.petfiap.exception.HorarioOcupadoException;
import br.com.fiap.petfiap.model.Atendimento;
import br.com.fiap.petfiap.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaService {

    @Autowired
    private AtendimentoRepository repository;

    public Atendimento agendar(Atendimento novo) {
        if (novo.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data/hora do atendimento nao pode estar no passado");
        }
        List<Atendimento> doPet = repository.findByPetNome(novo.getPetNome());
        for (Atendimento a : doPet) {
            if (a.getPetNome().equals(novo.getPetNome()) && a.getDataHora().equals(novo.getDataHora())
                    && "AGENDADO".equals(a.getStatus())) {
                throw new HorarioOcupadoException(
                        "Pet " + novo.getPetNome() + " ja possui atendimento agendado nesse horario");
            }
        }
        return repository.save(novo);
    }

    public Atendimento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AtendimentoNaoEncontradoException("Atendimento nao encontrado: " + id));
    }

    public Atendimento concluir(Long id) {
        Atendimento atendimento = buscarPorId(id);
        atendimento.concluir();
        return repository.save(atendimento);
    }

    public Atendimento cancelar(Long id) {
        Atendimento atendimento = buscarPorId(id);
        atendimento.cancelar();
        return repository.save(atendimento);
    }

    public List<Atendimento> buscarPorPet(String petNome) {
        return repository.findByPetNome(petNome);
    }
}
