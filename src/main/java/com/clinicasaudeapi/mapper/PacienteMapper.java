package com.clinicasaudeapi.mapper;

import com.clinicasaudeapi.dto.PacienteRequestDTO;
import com.clinicasaudeapi.dto.PacienteResponseDTO;
import com.clinicasaudeapi.model.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    Paciente toEntity(PacienteRequestDTO dto);

    PacienteResponseDTO toResponseDTO(Paciente entity);
}
