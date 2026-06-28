package com.clinicasaudeapi.repository;

import com.clinicasaudeapi.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    org.springframework.data.domain.Page<Medico> findByNomeContainingIgnoreCase(String nome, org.springframework.data.domain.Pageable pageable);
}
