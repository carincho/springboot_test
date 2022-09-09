package org.carincho.test.springboot.app.services;

import org.carincho.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;

public interface CuentaService {

    Cuenta findById(Long id);
    int revisarTotalTransferencias(Long idBanco);
    BigDecimal revisarSaldo(Long idCuenta);
    void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);
}
