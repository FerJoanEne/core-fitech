package services;

import interfaces.Validator;

import java.io.FileNotFoundException;
import java.util.Set;

public class ValidatorFactory {

    public ValidatorFactory() {
    }

    public ValidationTask buildValidationTask(String path, String machineCode) throws FileNotFoundException {
        ValidatorFinder validatorFinder = new ValidatorFinder();
        Set<Validator> validators = validatorFinder.findValidators(path);
        return new ValidationTask(validators, machineCode);
    }
}
