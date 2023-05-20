package core;

//java
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//custom imports
import java.io.File;
import java.io.FileNotFoundException;



public class UserStory2 {

    private final Logger log = LogManager.getLogger("UserStory2");


    @BeforeEach
    public void setUp() {

    }

    @Test
    public void CA1(){
        log.warn("se ejecuta test de CA1 - UBICACION INEXISTENTE");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new CoreFitech("", "Bicicleta1");
        });

        log.warn("mensaje de error CA1: {} ", illegalArgumentException.getMessage());
    }


    @Test
    public void CA2() {
        log.warn("se ejecuta test de CA2 - UBICACION INVALIDA");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new CoreFitech("\\videos", "Bicicleta1");
        });

        log.warn("mensaje de error CA2: {} ", illegalArgumentException.getMessage());
    }

    @Test
    public void CA3() throws FileNotFoundException {
        log.warn("se ejecuta test de CA3 - CARPETA VACIA");
        CoreFitech coreFitech = new CoreFitech(new File("emptyFolder").getAbsolutePath(), "Bicicleta1");
        int size = coreFitech.getValidationTask().getValidators().size();
        assertEquals(0, size);
        log.warn("cantidad de validators: {}", size);
    }

    @Test
    public void CA4() throws FileNotFoundException {
        log.warn("se ejecuta test de CA4 - NO ES VALIDACION");
        File file = new File("src/test/noEsValidacion");
        CoreFitech coreFitech = new CoreFitech(file.getAbsolutePath(), "Bicicleta1");
        int size = coreFitech.getValidationTask().getValidators().size();
        assertEquals(0, size);
        log.warn("cantidad de validators: {}", size);
    }

    @Test
    public void CA5() throws FileNotFoundException {
        log.warn("se ejecuta test de CA5 - VALIDACION SIMPLE");
        File file = new File("src/test/validacionSimple");
        CoreFitech coreFitech = new CoreFitech(file.getAbsolutePath(), "Bicicleta1");
        int size = coreFitech.getValidationTask().getValidators().size();
        assertEquals(1, size);
        log.warn("cantidad de validators: {}", size);
    }

    @Test
    public void CA6() throws FileNotFoundException {
        log.warn("se ejecuta test de CA6 - VALIDACIONES MULTIPLES");
        File file = new File("src/test/validacionMultiple");
        CoreFitech coreFitech = new CoreFitech(file.getAbsolutePath(), "Bicicleta1");
        int size = coreFitech.getValidationTask().getValidators().size();
        assertEquals(2, size);
        log.warn("cantidad de validators: {}", size);
    }
}