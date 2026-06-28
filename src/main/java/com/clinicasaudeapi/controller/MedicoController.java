package com.clinicasaudeapi.controller;

import com.clinicasaudeapi.dto.MedicoRequestDTO;
import com.clinicasaudeapi.dto.MedicoResponseDTO;
import com.clinicasaudeapi.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Medico", description = "Gerenciamento de medicos")
@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @Operation(summary = "Listar todos os Medico")
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<MedicoResponseDTO>> listar(@RequestParam(required = false) String nome, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.listar(nome, page, size));
    }

    @Operation(summary = "Buscar Medico por ID")
    @GetMapping("/{id}")
    public MedicoResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

    @Operation(summary = "Criar Medico")
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> criar(@Valid @RequestBody MedicoRequestDTO medico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(medico));
    }

    @Operation(summary = "Atualizar Medico")
    @PutMapping("/{id}")
    public MedicoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequestDTO medico) {
        return service.atualizar(id, medico);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Medico")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
