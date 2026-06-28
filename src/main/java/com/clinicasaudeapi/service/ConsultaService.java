package com.clinicasaudeapi.service;

import com.clinicasaudeapi.dto.ConsultaRequestDTO;
import com.clinicasaudeapi.dto.ConsultaResponseDTO;
import com.clinicasaudeapi.exception.ResourceNotFoundException;
import com.clinicasaudeapi.mapper.ConsultaMapper;
import com.clinicasaudeapi.model.Consulta;
import com.clinicasaudeapi.repository.ConsultaRepository;
import com.clinicasaudeapi.repository.PacienteRepository;
import com.clinicasaudeapi.model.Paciente;
import com.clinicasaudeapi.repository.MedicoRepository;
import com.clinicasaudeapi.model.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private ConsultaMapper mapper;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<ConsultaResponseDTO> listar(String observacoes, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (observacoes != null && !observacoes.isBlank()) {
            return repository.findByObservacoesContainingIgnoreCase(observacoes, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ConsultaResponseDTO buscar(Long id) {
        Consulta entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public ConsultaResponseDTO criar(ConsultaRequestDTO dto) {
        Consulta entity = mapper.toEntity(dto);
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com id: " + dto.getPacienteId()));
        entity.setPaciente(paciente);
        Medico medico = medicoRepository.findById(dto.getMedicoId())
            .orElseThrow(() -> new ResourceNotFoundException("Medico não encontrado com id: " + dto.getMedicoId()));
        entity.setMedico(medico);
        Consulta salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public ConsultaResponseDTO atualizar(Long id, ConsultaRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Consulta não encontrado com id: " + id);
        }
        Consulta entity = mapper.toEntity(dto);
        entity.setId(id);
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com id: " + dto.getPacienteId()));
        entity.setPaciente(paciente);
        Medico medico = medicoRepository.findById(dto.getMedicoId())
            .orElseThrow(() -> new ResourceNotFoundException("Medico não encontrado com id: " + dto.getMedicoId()));
        entity.setMedico(medico);
        Consulta salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Consulta não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
