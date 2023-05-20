package core;

//java
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ValidatorFactory;
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
    public void CA1(){
        log.warn("se ejecuta test de CA1 - UBICACION INEXISTENTE");
        validatorFinder = new ValidatorFinder("");
        assertThrows(FileNotFoundException.class, () -> {
            validatorFinder.findValidators();
        });
    }


    @Test
    public void CA2() {
        log.warn("se ejecuta test de CA2 - UBICACION INVALIDA");
        validatorFinder = new ValidatorFinder("\\videos");
        assertThrows(IllegalArgumentException.class, () -> {
            validatorFinder.findValidators();
        });

    }

    @Test
    public void CA3() throws FileNotFoundException {
        log.warn("se ejecuta test de CA3 - CARPETA VACIA");
        String path = new File("testValidators\\emptyFolder").getAbsolutePath();
        validatorFinder = new ValidatorFinder(path);
        int size = validatorFinder.findValidators().size();
        assertEquals(0, size);
    }

    @Test
    public void CA4() throws FileNotFoundException {
        log.warn("se ejecuta test de CA4 - NO ES VALIDACION");
        String path = new File("testValidators\\noEsValidacion").getAbsolutePath();
        validatorFinder = new ValidatorFinder(path);
        int size = validatorFinder.findValidators().size();
        assertEquals(0, size);
    }

    @Test
    public void CA5() throws FileNotFoundException {
        log.warn("se ejecuta test de CA5 - VALIDACION SIMPLE");
        String path = new File("validacionSimple").getAbsolutePath();
        validatorFinder = new ValidatorFinder(path);
        int size = validatorFinder.findValidators().size();
        assertEquals(1, size);
    }

    @Test
    public void CA6() throws FileNotFoundException {
        log.warn("se ejecuta test de CA6 - VALIDACIONES MULTIPLES");
        String path = new File("validators").getAbsolutePath();
        validatorFinder = new ValidatorFinder(path);
        int size = validatorFinder.findValidators().size();
        assertEquals(2, size);
    }
}