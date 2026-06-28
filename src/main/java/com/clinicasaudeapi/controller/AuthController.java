package com.clinicasaudeapi.controller;

import com.clinicasaudeapi.model.Usuario;
import com.clinicasaudeapi.repository.UsuarioRepository;
import com.clinicasaudeapi.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Tag(name = "Autenticação", description = "Registro e login JWT")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Registrar novo usuário")
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrar(@RequestBody Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getUsername().isBlank()
                || usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", "username e password são obrigatórios"));
        }
        if (repository.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("erro", "Usuário já existe"));
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        if (usuario.getRole() == null || usuario.getRole().isBlank()) {
            usuario.setRole("USER");
        }
        repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("mensagem", "Usuário registrado com sucesso"));
    }

    @Operation(summary = "Login — retorna token JWT")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Usuario login) {
        Optional<Usuario> existente = repository.findByUsername(login.getUsername());
        if (existente.isEmpty() || !passwordEncoder.matches(login.getPassword(), existente.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "Usuário ou senha inválidos"));
        }
        String role = existente.get().getRole();
        String resolvedRole = role == null ? "USER" : role;
        String token = jwtUtil.gerarToken(login.getUsername(), resolvedRole);
        return ResponseEntity.ok(Map.of(
            "token", token,
            "username", login.getUsername(),
            "role", resolvedRole
        ));
    }
}
