package com.clinicasaudeapi.mapper;

import com.clinicasaudeapi.dto.ConsultaRequestDTO;
import com.clinicasaudeapi.dto.ConsultaResponseDTO;
import com.clinicasaudeapi.model.Consulta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medico", ignore = true)
    Consulta toEntity(ConsultaRequestDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "medicoId", source = "medico.id")
    ConsultaResponseDTO toResponseDTO(Consulta entity);
}
