package com.clinicasaudeapi.dto;

import jakarta.validation.constraints.*;

public class ConsultaRequestDTO {

    @NotNull(message = "PacienteId é obrigatório")
    @Positive(message = "PacienteId deve ser um ID válido (positivo)")
    private Long pacienteId;
    @NotNull(message = "MedicoId é obrigatório")
    @Positive(message = "MedicoId deve ser um ID válido (positivo)")
    private Long medicoId;
    @FutureOrPresent(message = "data hora não pode ser retroativo")
    private java.time.LocalDateTime dataHora;
    @NotBlank(message = "status não pode estar em branco")
    private String status;

    private String observacoes;

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
    public java.time.LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(java.time.LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
