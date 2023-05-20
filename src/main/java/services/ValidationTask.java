package services;

import interfaces.Observable;
import interfaces.Observer;
import interfaces.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class ValidationTask implements Observable, Observer {

    private final Logger log = LogManager.getLogger("ValidationTask");
    private final Set<Validator> validators;
    private final String machineCode;

    public ValidationTask(Set<Validator> validators, String machineCode) {
        this.validators = validators;
        this.machineCode = machineCode;
        this.addAsObserver();
    }

    public void processRequest(String userName) {
        for (Validator validator : validators) {
            validator.validate(userName, this.machineCode);
        }
    }

    private void addAsObserver() {
        for (Validator validator : this.validators) {
                validator.addObserver(this);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void notifyObservers(Boolean result) {
        for (Observer observer : observers) {
            observer.update(result);
        }
    }

    @Override
    public void update() {
        boolean result = true;
        for (Validator validator : this.validators) {
            if (!validator.getResult()) {
                log.info("Falló el validador: {}", validator.getClass().getName());
                result = false;
                break; // Detener la iteración si hay un fallo
            }
            log.info("Pasó el validador: {}", validator.getClass().getName());
        }
        this.notifyObservers(result);
        log.info("\u001B[31mResultado de la validación es: {}\u001B[0m", result);
    }

    @Override
    public void update(Boolean result) {
    }

    public Set<Validator> getValidators(){
        return this.validators;
    }
}
