package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.EmpresaService;

import java.math.BigDecimal;
@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping("/{empresaId}/deposito")
    public ResponseEntity<Void> realizarDeposito(@PathVariable Long empresaId, @RequestBody BigDecimal valorDeposito) {
        empresaService.realizarDeposito(empresaId, valorDeposito);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{empresaId}/saque")
    public ResponseEntity<Void> realizarSaque(@PathVariable Long empresaId, @RequestBody BigDecimal valorSaque) {
        empresaService.realizarSaque(empresaId, valorSaque);
        return ResponseEntity.ok().build();
    }
}