package org.carincho.test.springboot.app.services;

import org.carincho.test.springboot.app.models.Banco;
import org.carincho.test.springboot.app.models.Cuenta;
import org.carincho.test.springboot.app.repositories.BancoRepository;
import org.carincho.test.springboot.app.repositories.CuentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {

        return cuentaRepository.findById(id).orElseThrow();
    }

    @Override
    public int revisarTotalTransferencias(Long idBanco) {

        Banco banco = bancoRepository.findById(idBanco);


        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long idCuenta) {

        Cuenta cuenta = cuentaRepository.findById(idCuenta).orElseThrow();
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen).orElseThrow();
        cuentaOrigen.debito(monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino).orElseThrow();
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();

        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.update(banco);


    }
}



