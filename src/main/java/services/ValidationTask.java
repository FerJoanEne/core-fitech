package services;

import interfaces.Validator;

import java.util.Set;

public class ValidationTask {
    Set<Validator> validators;
    private String machineCode;

    public ValidationTask(Set<Validator> validators, String machineCode){
        this.validators = validators;
        this.machineCode = machineCode;
    }

    public boolean validate(String userName){
        for(Validator validator : validators){
            validator.validate(userName, machineCode);
        }
        return true;
    }

    public Set<Validator> getValidators() {
        return validators;
    }
}
