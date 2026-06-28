package com.clinicasaudeapi.controller;

import com.clinicasaudeapi.dto.ConsultaRequestDTO;
import com.clinicasaudeapi.dto.ConsultaResponseDTO;
import com.clinicasaudeapi.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Consulta", description = "Gerenciamento de consultas")
@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @Operation(summary = "Listar todos os Consulta")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<ConsultaResponseDTO>> listar(@RequestParam(required = false) String observacoes, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) Long pacienteId, @RequestParam(required = false) Long medicoId) {
        org.springframework.data.domain.Page<ConsultaResponseDTO> resultado = service.listar(observacoes, page, size);
        if (pacienteId != null) {
            java.util.List<ConsultaResponseDTO> filtrado = resultado.getContent().stream()
                .filter(item -> pacienteId.equals(item.getPacienteId()))
                .collect(java.util.stream.Collectors.toList());
            resultado = new org.springframework.data.domain.PageImpl<>(
                filtrado, org.springframework.data.domain.PageRequest.of(page, size), filtrado.size());
        }
        if (medicoId != null) {
            java.util.List<ConsultaResponseDTO> filtrado = resultado.getContent().stream()
                .filter(item -> medicoId.equals(item.getMedicoId()))
                .collect(java.util.stream.Collectors.toList());
            resultado = new org.springframework.data.domain.PageImpl<>(
                filtrado, org.springframework.data.domain.PageRequest.of(page, size), filtrado.size());
        }
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Buscar Consulta por ID")
    @GetMapping("/{id}")
    public ConsultaResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar Consulta")
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> criar(@Valid @RequestBody ConsultaRequestDTO consulta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(consulta));
    }

    @Operation(summary = "Atualizar Consulta")
    @PutMapping("/{id}")
    public ConsultaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaRequestDTO consulta) {
        return service.atualizar(id, consulta);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Consulta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
