package org.carincho.test.springboot.app.controllers;

import static org.springframework.http.HttpStatus.*;
import org.carincho.test.springboot.app.models.Cuenta;
import org.carincho.test.springboot.app.models.TransaccionDto;
import org.carincho.test.springboot.app.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta cuentaDetalle(@PathVariable Long id) {

        return cuentaService.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?>transferir(@RequestBody TransaccionDto dto) {
        cuentaService.transferir(dto.getCuentaOrigenId(),
                dto.getCuentaDestinoId(),
                dto.getMonto(), dto.getBandcoId());
        Map<String, Object>response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "Ok");
        response.put("mensaje", "Transferencia realizada con éxito");
        response.put("transaccion", dto);

        return ResponseEntity.ok(response);

    }

}
