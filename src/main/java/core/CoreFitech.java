package core;

import interfaces.Observer;
import services.ValidationTask;
import services.ValidatorFactory;

import java.io.FileNotFoundException;

public class CoreFitech {

    private final ValidationTask validationTask;

    public CoreFitech(String path, String machineCode) throws FileNotFoundException {
        ValidatorFactory factory = new ValidatorFactory();
        this.validationTask =  factory.buildValidationTask(path, machineCode);
    }

    public ValidationTask getValidationTask() {
        return validationTask;
    }
}