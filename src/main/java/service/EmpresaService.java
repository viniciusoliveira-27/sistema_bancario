package service;

import entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.EmpresaRepository;

import java.math.BigDecimal;
import java.util.List;
@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa findById(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void delete(Long id) {
        empresaRepository.deleteById(id);
    }

    @Transactional
    public void atualizarSaldo(Long empresaId, BigDecimal valorDeposito, BigDecimal valorSaque) {
        // Busca a empresa pelo ID
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        // Calcula a taxa de administração (exemplo: 1% do valor da transação)
        BigDecimal taxaAdministracao = valorDeposito.multiply(new BigDecimal("0.01"));

        // Calcula o novo saldo da empresa
        BigDecimal novoSaldo = empresa.getSaldo().add(valorDeposito).subtract(valorSaque).subtract(taxaAdministracao);

        // Atualiza o saldo da empresa
        empresa.setSaldo(novoSaldo);

        // Salva a empresa atualizada no banco de dados
        empresaRepository.save(empresa);
    }

    public void realizarDeposito(Long empresaId, BigDecimal valorDeposito) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        // Verifica se o depósito pode ser aceito (exemplo: depósito mínimo)
        if (valorDeposito.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo");
        }

        // Atualiza o saldo da empresa com o valor do depósito
        empresa.setSaldo(empresa.getSaldo().add(valorDeposito));
        empresaRepository.save(empresa);
    }

    public void realizarSaque(Long empresaId, BigDecimal valorSaque) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        // Verifica se a empresa tem saldo suficiente para o saque
        if (empresa.getSaldo().compareTo(valorSaque) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para o saque");
        }

        // Atualiza o saldo da empresa subtraindo o valor do saque
        empresa.setSaldo(empresa.getSaldo().subtract(valorSaque));
        empresaRepository.save(empresa);
    }

}