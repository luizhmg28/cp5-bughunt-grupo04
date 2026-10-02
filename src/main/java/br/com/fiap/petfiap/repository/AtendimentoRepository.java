package br.com.fiap.petfiap.repository;

import br.com.fiap.petfiap.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByPetNome(String petNome);
}
