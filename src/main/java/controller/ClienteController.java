package controller;

import entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<String> criarCliente(@RequestBody Cliente cliente) {
        // Processar o cliente e criar um novo cliente
        clienteService.criarCliente(cliente);
        return ResponseEntity.ok("Cliente criado com sucesso.");
    }
}