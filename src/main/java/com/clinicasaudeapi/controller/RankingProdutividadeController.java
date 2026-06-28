package com.clinicasaudeapi.controller;

import com.clinicasaudeapi.model.Consulta;
import com.clinicasaudeapi.model.Medico;
import com.clinicasaudeapi.repository.ConsultaRepository;
import com.clinicasaudeapi.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/relatorio")
public class RankingProdutividadeController {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping("/ranking-produtividade")
    public List<Map<String, Object>> ranking() {
        List<Consulta> todos = consultaRepository.findAll();
        Map<Long, List<Consulta>> porProfissional = todos.stream()
            .filter(r -> r.getMedico() != null)
            .collect(Collectors.groupingBy(r -> r.getMedico().getId()));
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<Long, List<Consulta>> e : porProfissional.entrySet()) {
            long total = e.getValue().size();
            long concluidos = e.getValue().stream()
                .filter(r -> r.getStatus() != null && r.getStatus().toLowerCase().matches(".*conclu.*|.*finaliz.*|.*entreg.*"))
                .count();
            Map<String, Object> linha = new LinkedHashMap<>();
            linha.put("medicoId", e.getKey());
            linha.put("total", total);
            linha.put("concluidos", concluidos);
            linha.put("percentualConcluido", total == 0 ? 0 : (concluidos * 100.0 / total));
            resultado.add(linha);
        }
        resultado.sort((a, b) -> Long.compare((long) b.get("total"), (long) a.get("total")));
        return resultado;
    }
}
