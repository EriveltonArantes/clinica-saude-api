package com.clinicasaudeapi.service;

import com.clinicasaudeapi.dto.PrescricaoRequestDTO;
import com.clinicasaudeapi.dto.PrescricaoResponseDTO;
import com.clinicasaudeapi.exception.ResourceNotFoundException;
import com.clinicasaudeapi.mapper.PrescricaoMapper;
import com.clinicasaudeapi.model.Prescricao;
import com.clinicasaudeapi.repository.PrescricaoRepository;
import com.clinicasaudeapi.repository.ConsultaRepository;
import com.clinicasaudeapi.model.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrescricaoService {

    @Autowired
    private PrescricaoRepository repository;

    @Autowired
    private PrescricaoMapper mapper;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<PrescricaoResponseDTO> listar(String medicamento, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (medicamento != null && !medicamento.isBlank()) {
            return repository.findByMedicamentoContainingIgnoreCase(medicamento, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public PrescricaoResponseDTO buscar(Long id) {
        Prescricao entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prescricao não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public PrescricaoResponseDTO criar(PrescricaoRequestDTO dto) {
        Prescricao entity = mapper.toEntity(dto);
        Consulta consulta = consultaRepository.findById(dto.getConsultaId())
            .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrado com id: " + dto.getConsultaId()));
        entity.setConsulta(consulta);
        Prescricao salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public PrescricaoResponseDTO atualizar(Long id, PrescricaoRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Prescricao não encontrado com id: " + id);
        }
        Prescricao entity = mapper.toEntity(dto);
        entity.setId(id);
        Consulta consulta = consultaRepository.findById(dto.getConsultaId())
            .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrado com id: " + dto.getConsultaId()));
        entity.setConsulta(consulta);
        Prescricao salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Prescricao não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
