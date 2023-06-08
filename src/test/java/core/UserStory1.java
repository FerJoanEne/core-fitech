package core;

import interfaces.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ValidationTask;
import services.ValidatorFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserStory1 {

    private final Logger log = LogManager.getLogger("UserStory1");
    private Validator validator;
    private ValidationTask validationTask;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        String path = new File("simpleValidator").getAbsolutePath();
        String machineCode = "Bicicleta1";
        log.info("Path: {}", path);
        log.info("MachineCode: {}", machineCode);

        this.validationTask = new ValidatorFactory(path, machineCode).buildValidationTask();
        Set<Validator> validators = this.validationTask.getValidators();
        validator = validators.stream().findFirst().get();

        validationTask = new ValidationTask(validators, "Bicicleta1");
    }

    @Test
    public void CA1_UsoValido() throws InterruptedException {
        log.warn("Se ejecuta test de CA1 con dato de entrada: 'Tahiel'");
        validator.validate("Tahiel", "Bicicleta1");
        Thread.sleep(3000);
        assertTrue(validator.getResult());
    }

    @Test
    void CA2_MaquinaNoAdmitida() throws InterruptedException {
        log.warn("Se ejecuta test de CA2 - con dato de entrada: 'Evelyn'");
        validator.validate("Evelyn", "Bicicleta1");
        Thread.sleep(3000);
        assertFalse(validator.getResult());
    }

    @Test
    void CA3_IdentificacionNoValida() throws InterruptedException {
        log.warn("Se ejecuta test de CA3 - con dato de entrada: '' ");
        validator.validate("", "Bicicleta1");
        Thread.sleep(3000);
        assertFalse(validator.getResult());
    }

    @Test
    void CA4_SinRutina() throws InterruptedException {
        log.warn("Se ejecuta test de CA4 - con dato de entrada: 'Joan' ");
        validator.validate("Joan", "Bicicleta1");
        Thread.sleep(3000);
        assertFalse(validator.getResult());
    }
}