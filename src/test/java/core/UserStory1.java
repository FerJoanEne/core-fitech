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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserStory1 {

    private final Logger log = LogManager.getLogger("UserStory1");
    private Validator routineValidator;
    private ValidationTask validationTask;

    @BeforeEach
    public void setUp() throws  FileNotFoundException {
        String path = new File("validator").getAbsolutePath();
        String machineCode = "Bicicleta1";
        log.info("path: {}", path);
        log.info("machineCode: {}", machineCode);

        this.validationTask = new ValidatorFactory(path, machineCode).buildValidationTask();
        Set<Validator> validators = this.validationTask.getValidators();
        routineValidator = validators.stream().findFirst().get();

        validationTask = new ValidationTask(validators, "Bicicleta1");
    }

    @Test
    public void CA1() throws InterruptedException {
        log.warn("se ejecuta test de CA1 con dato de entrada: Tahiel");
        routineValidator.validate("Tahiel", "Bicicleta1");
        Thread.sleep(4000);
        assertTrue(routineValidator.getResult());
    }

    @Test
    void CA2() throws InterruptedException {
        log.warn("se ejecuta test de CA2 - con dato de entrada: Evelyn");
        routineValidator.validate("Evelyn", "Bicicleta1");
        Thread.sleep(4000);
        assertFalse(routineValidator.getResult());
    }

    @Test
    void CA3() throws InterruptedException {
        log.warn("se ejecuta test de CA3 - con dato de entrada: '' ");
        routineValidator.validate("", "Bicicleta1");
        Thread.sleep(4000);
        assertFalse(routineValidator.getResult());
    }

}