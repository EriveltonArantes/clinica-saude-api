package com.clinicasaudeapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "prescricoes")
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private java.time.LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private java.time.LocalDateTime atualizadoEm;

    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;
    @Column(nullable = false)
    private String medicamento;
    @Column(nullable = false)
    private String dosagem;
    @Column(nullable = false)
    private String instrucoes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public java.time.LocalDateTime getCriadoEm() { return criadoEm; }
    public java.time.LocalDateTime getAtualizadoEm() { return atualizadoEm; }

    public Consulta getConsulta() { return consulta; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    public String getDosagem() { return dosagem; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }
    public String getInstrucoes() { return instrucoes; }
    public void setInstrucoes(String instrucoes) { this.instrucoes = instrucoes; }
}
