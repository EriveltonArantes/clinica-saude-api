package com.clinicasaudeapi.dto;

import jakarta.validation.constraints.*;

public class PrescricaoRequestDTO {

    @NotNull(message = "ConsultaId é obrigatório")
    @Positive(message = "ConsultaId deve ser um ID válido (positivo)")
    private Long consultaId;
    @NotBlank(message = "medicamento não pode estar em branco")
    private String medicamento;
    @NotBlank(message = "dosagem não pode estar em branco")
    private String dosagem;
    @NotBlank(message = "instrucoes não pode estar em branco")
    private String instrucoes;

    public Long getConsultaId() { return consultaId; }
    public void setConsultaId(Long consultaId) { this.consultaId = consultaId; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    public String getDosagem() { return dosagem; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }
    public String getInstrucoes() { return instrucoes; }
    public void setInstrucoes(String instrucoes) { this.instrucoes = instrucoes; }
}
