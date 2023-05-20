package core;

import services.ValidationTask;
import services.ValidatorFactory;
import services.ValidatorFinder;

import javax.xml.bind.ValidationException;
import java.io.FileNotFoundException;

public class CoreFitech {

    private final ValidationTask validationTask;

    public CoreFitech(String path, String machineCode) throws ValidationException {
        ValidatorFactory validatorFactory = new ValidatorFactory(path, machineCode);
        this.validationTask = validatorFactory.buildValidationTask();
    }

    public ValidationTask getValidationTask() {
        return this.getValidationTask();
    }
}