package com.clinicasaudeapi.service;

import com.clinicasaudeapi.dto.MedicoRequestDTO;
import com.clinicasaudeapi.dto.MedicoResponseDTO;
import com.clinicasaudeapi.exception.ResourceNotFoundException;
import com.clinicasaudeapi.exception.BusinessException;
import com.clinicasaudeapi.mapper.MedicoMapper;
import com.clinicasaudeapi.model.Medico;
import com.clinicasaudeapi.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private MedicoMapper mapper;

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<MedicoResponseDTO> listar(String nome, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by("id").descending());
        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(mapper::toResponseDTO);
        }
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public MedicoResponseDTO buscar(Long id) {
        Medico entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Medico não encontrado com id: " + id));
        return mapper.toResponseDTO(entity);
    }

    public MedicoResponseDTO criar(MedicoRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + dto.getEmail());
        }
        Medico entity = mapper.toEntity(dto);
        Medico salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Medico não encontrado com id: " + id);
        }
        if (repository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new BusinessException("Email já cadastrado em outro registro: " + dto.getEmail());
        }
        Medico entity = mapper.toEntity(dto);
        entity.setId(id);
        Medico salvo = repository.save(entity);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Medico não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
