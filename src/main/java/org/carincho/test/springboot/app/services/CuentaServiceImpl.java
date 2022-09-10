package org.carincho.test.springboot.app.services;

import org.carincho.test.springboot.app.models.Banco;
import org.carincho.test.springboot.app.models.Cuenta;
import org.carincho.test.springboot.app.repositories.BancoRepository;
import org.carincho.test.springboot.app.repositories.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Transactional(readOnly = true)//esta debe ser de spring
    @Override
    public Cuenta findById(Long id) {

        return cuentaRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public int revisarTotalTransferencias(Long idBanco) {

        Banco banco = bancoRepository.findById(idBanco).orElseThrow();


        return banco.getTotalTransferencias();
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal revisarSaldo(Long idCuenta) {

        Cuenta cuenta = cuentaRepository.findById(idCuenta).orElseThrow();
        return cuenta.getSaldo();
    }
    @Transactional
    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen).orElseThrow();
        cuentaOrigen.debito(monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino).orElseThrow();
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId).orElseThrow();
        int totalTransferencias = banco.getTotalTransferencias();

        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.save(banco);


    }
}



