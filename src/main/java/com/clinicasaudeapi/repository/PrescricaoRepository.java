package com.clinicasaudeapi.repository;

import com.clinicasaudeapi.model.Prescricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Long> {

    org.springframework.data.domain.Page<Prescricao> findByMedicamentoContainingIgnoreCase(String medicamento, org.springframework.data.domain.Pageable pageable);
}
