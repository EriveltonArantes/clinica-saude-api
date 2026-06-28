package com.clinicasaudeapi.controller;

import com.clinicasaudeapi.dto.PrescricaoRequestDTO;
import com.clinicasaudeapi.dto.PrescricaoResponseDTO;
import com.clinicasaudeapi.service.PrescricaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Prescricao", description = "Gerenciamento de prescricaos")
@RestController
@RequestMapping("/api/prescricaos")
public class PrescricaoController {

    @Autowired
    private PrescricaoService service;

    @Operation(summary = "Listar todos os Prescricao")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<PrescricaoResponseDTO>> listar(@RequestParam(required = false) String medicamento, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) Long consultaId) {
        org.springframework.data.domain.Page<PrescricaoResponseDTO> resultado = service.listar(medicamento, page, size);
        if (consultaId != null) {
            java.util.List<PrescricaoResponseDTO> filtrado = resultado.getContent().stream()
                .filter(item -> consultaId.equals(item.getConsultaId()))
                .collect(java.util.stream.Collectors.toList());
            resultado = new org.springframework.data.domain.PageImpl<>(
                filtrado, org.springframework.data.domain.PageRequest.of(page, size), filtrado.size());
        }
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Buscar Prescricao por ID")
    @GetMapping("/{id}")
    public PrescricaoResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar Prescricao")
    @PostMapping
    public ResponseEntity<PrescricaoResponseDTO> criar(@Valid @RequestBody PrescricaoRequestDTO prescricao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(prescricao));
    }

    @Operation(summary = "Atualizar Prescricao")
    @PutMapping("/{id}")
    public PrescricaoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody PrescricaoRequestDTO prescricao) {
        return service.atualizar(id, prescricao);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Prescricao")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
