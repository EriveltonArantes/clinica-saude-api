package com.clinicasaudeapi.repository;

import com.clinicasaudeapi.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    org.springframework.data.domain.Page<Paciente> findByNomeContainingIgnoreCase(String nome, org.springframework.data.domain.Pageable pageable);
}
