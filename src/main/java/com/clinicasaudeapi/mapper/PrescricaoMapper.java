package com.clinicasaudeapi.mapper;

import com.clinicasaudeapi.dto.PrescricaoRequestDTO;
import com.clinicasaudeapi.dto.PrescricaoResponseDTO;
import com.clinicasaudeapi.model.Prescricao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrescricaoMapper {

    @Mapping(target = "consulta", ignore = true)
    Prescricao toEntity(PrescricaoRequestDTO dto);

    @Mapping(target = "consultaId", source = "consulta.id")
    PrescricaoResponseDTO toResponseDTO(Prescricao entity);
}
