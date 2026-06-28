package com.clinicasaudeapi.mapper;

import com.clinicasaudeapi.dto.MedicoRequestDTO;
import com.clinicasaudeapi.dto.MedicoResponseDTO;
import com.clinicasaudeapi.model.Medico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicoMapper {

    Medico toEntity(MedicoRequestDTO dto);

    MedicoResponseDTO toResponseDTO(Medico entity);
}
