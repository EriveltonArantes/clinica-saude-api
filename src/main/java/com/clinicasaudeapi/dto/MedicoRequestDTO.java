package com.clinicasaudeapi.dto;

import jakarta.validation.constraints.*;

public class MedicoRequestDTO {

    @NotBlank(message = "nome não pode estar em branco")
    private String nome;
    @NotBlank(message = "crm não pode estar em branco")
    @Size(min = 4, max = 20, message = "crm inválido")
    private String crm;
    @NotBlank(message = "especialidade não pode estar em branco")
    private String especialidade;
    @NotBlank(message = "email não pode estar em branco")
    @Email(message = "email precisa ser um e-mail válido")
    private String email;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
