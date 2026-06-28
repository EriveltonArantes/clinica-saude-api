package com.clinicasaudeapi.dto;

public class PrescricaoResponseDTO {

    private Long id;
    private java.time.LocalDateTime criadoEm;
    private java.time.LocalDateTime atualizadoEm;
    private Long consultaId;
    private String medicamento;
    private String dosagem;
    private String instrucoes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public java.time.LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(java.time.LocalDateTime v) { this.criadoEm = v; }
    public java.time.LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(java.time.LocalDateTime v) { this.atualizadoEm = v; }
    public Long getConsultaId() { return consultaId; }
    public void setConsultaId(Long consultaId) { this.consultaId = consultaId; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    public String getDosagem() { return dosagem; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }
    public String getInstrucoes() { return instrucoes; }
    public void setInstrucoes(String instrucoes) { this.instrucoes = instrucoes; }
}
