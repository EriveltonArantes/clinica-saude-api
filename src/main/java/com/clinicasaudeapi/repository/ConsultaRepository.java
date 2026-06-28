package com.clinicasaudeapi.repository;

import com.clinicasaudeapi.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    org.springframework.data.domain.Page<Consulta> findByObservacoesContainingIgnoreCase(String observacoes, org.springframework.data.domain.Pageable pageable);
}
