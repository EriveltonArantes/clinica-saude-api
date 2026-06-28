package com.clinicasaudeapi.service;

import com.clinicasaudeapi.dto.PacienteRequestDTO;
import com.clinicasaudeapi.dto.PacienteResponseDTO;
import com.clinicasaudeapi.exception.ResourceNotFoundException;
import com.clinicasaudeapi.exception.BusinessException;
import com.clinicasaudeapi.mapper.PacienteMapper;
import com.clinicasaudeapi.model.Paciente;
import com.clinicasaudeapi.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private PacienteMapper mapper;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<PacienteResponseDTO> listar(String nome, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public PacienteResponseDTO buscar(Long id) {
        Paciente entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public PacienteResponseDTO criar(PacienteRequestDTO dto) {
        if (repository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("Cpf já cadastrado: " + dto.getCpf());
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + dto.getEmail());
        }
        Paciente entity = mapper.toEntity(dto);
        Paciente salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente não encontrado com id: " + id);
        }
        if (repository.existsByCpfAndIdNot(dto.getCpf(), id)) {
            throw new BusinessException("Cpf já cadastrado em outro registro: " + dto.getCpf());
        }
        if (repository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new BusinessException("Email já cadastrado em outro registro: " + dto.getEmail());
        }
        Paciente entity = mapper.toEntity(dto);
        entity.setId(id);
        Paciente salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
