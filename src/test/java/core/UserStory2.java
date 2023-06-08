package core;

//java

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ValidatorFinder;

//custom imports
import java.io.File;
import java.io.FileNotFoundException;


public class UserStory2 {

    private final Logger log = LogManager.getLogger("UserStory2");

    private ValidatorFinder validatorFinder;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void CA1_UbicacionInexistente() {
        log.warn("se ejecuta test de CA1 - UBICACION INEXISTENTE");
        validatorFinder = new ValidatorFinder("");
        assertThrows(FileNotFoundException.class, () -> {
            validatorFinder.findValidators();
        });
    }


    @Test
    public void CA2_UbicacionInvalida() {
        log.warn("se ejecuta test de CA2 - UBICACION INVALIDA");
        validatorFinder = new ValidatorFinder("\\invalid-path");
        assertThrows(IllegalArgumentException.class, () -> {
            validatorFinder.findValidators();
        });
    }

    @Test
    public void CA3_CarpetaVacia() throws FileNotFoundException {
        log.warn("se ejecuta test de CA3 - CARPETA VACIA");
        String currentDirectory = System.getProperty("user.dir");
        String path = currentDirectory + File.separator + "noValidators";
        validatorFinder = new ValidatorFinder(path);
        assertTrue(validatorFinder.findValidators().isEmpty());
    }

    @Test
    public void CA4_NoEsValidacion() throws FileNotFoundException {
        log.warn("se ejecuta test de CA4 - NO ES VALIDACION");
        String path = new File("invalidValidator").getAbsolutePath();
        validatorFinder = new ValidatorFinder(path);
        assertTrue(validatorFinder.findValidators().isEmpty());
    }

    @Test
    public void CA5_ValidacionSimple() throws FileNotFoundException {
        log.warn("se ejecuta test de CA5 - VALIDACION SIMPLE");
        String path = new File("simpleValidator").getAbsolutePath();
        validatorFinder = new ValidatorFinder(path);
        int size = validatorFinder.findValidators().size();
        assertEquals(1, size);
        log.warn("cantidad de validators: {}", size);
    }

    @Test
    public void CA6_ValidacionesMultiples() throws FileNotFoundException {
        log.warn("se ejecuta test de CA6 - VALIDACIONES MULTIPLES");
        String path = new File("multipleValidators").getAbsolutePath();
        validatorFinder = new ValidatorFinder(path);
        int size = validatorFinder.findValidators().size();
        assertEquals(2, size);
        log.warn("cantidad de validators: {}", size);
    }
}