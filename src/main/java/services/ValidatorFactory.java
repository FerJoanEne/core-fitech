package services;

import interfaces.Validator;

import java.io.FileNotFoundException;
import java.util.Set;

public class ValidatorFactory {

    private ValidationTask validationTask;

    public ValidatorFactory(String path, String machineCode) throws FileNotFoundException {
        Set<Validator> validators = new ValidatorFinder().findValidators(path);
        this.validationTask = new ValidationTask(validators, machineCode);
    }

    public ValidationTask getValidationTask() {
        return validationTask;
    }
}
