package org.carincho.test.springboot.app;

import org.carincho.test.springboot.app.models.Cuenta;
import org.carincho.test.springboot.app.repositories.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @DataJpaTest importante para las pruebas de integracion con jpa habilita persistencia bd en memoria
 * habilita repositorios de spring
 * Metodo test modifica info de una tabla despues realiza un roll back al finalizar la pureba unitaria
 *
 */
@DataJpaTest
public class IntegracionJpaTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    void testFindById() {

        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);

        assertTrue(cuenta.isPresent());
        assertEquals("Carincho", cuenta.orElseThrow().getPersona());

    }

    @Test
    void testFindByPersona() {

        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Carincho");

        assertTrue(cuenta.isPresent());
        assertEquals("Carincho", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());

    }

    @Test
    void testFindByPersonaThrowException() {


        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("carincho");
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertFalse(cuenta.isPresent());


    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());

        assertEquals(2, cuentas.size());

    }

    @Test
    void testSave() {

        //Given

        Cuenta cuentaFulanito = new Cuenta(null, "Fulanito", new BigDecimal("3000"));


        //when

        Cuenta cuenta = cuentaRepository.save(cuentaFulanito);

//        Cuenta cuenta = cuentaRepository.findByPersona("Fulanito").orElseThrow();
//        Cuenta cuenta = cuentaRepository.findById(cuentaSave.getId()).orElseThrow();

        //then

        assertEquals("Fulanito", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
//        assertEquals(3, cuenta.getId());

    }

    @Test
    void testUpdate() {

        //Given

        Cuenta cuentaFulanito = new Cuenta(null, "Fulanito", new BigDecimal("3000"));


        //when

        Cuenta cuenta = cuentaRepository.save(cuentaFulanito);

//        Cuenta cuenta = cuentaRepository.findByPersona("Fulanito").orElseThrow();
//        Cuenta cuenta = cuentaRepository.findById(cuentaSave.getId()).orElseThrow();

        //then

        assertEquals("Fulanito", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
//        assertEquals(3, cuenta.getId());

        //when
        cuenta.setSaldo(new BigDecimal("3800"));
        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        //then
        assertEquals("Fulanito", cuentaActualizada.getPersona());
        assertEquals("3800", cuentaActualizada.getSaldo().toPlainString());
    }

    @Test
    void testDelete() {

        //Given
        Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow();
        assertEquals("Kincho", cuenta.getPersona());

        cuentaRepository.delete(cuenta);

        assertThrows(NoSuchElementException.class, () -> {
           //cuentaRepository.findByPersona("Kincho").orElseThrow();
           cuentaRepository.findById(2L).orElseThrow();
        });
        assertEquals(1, cuentaRepository.findAll().size());

    }
}
