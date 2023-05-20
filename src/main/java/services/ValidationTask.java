package services;

import interfaces.Observable;
import interfaces.Observer;
import interfaces.Validator;

import java.util.HashSet;
import java.util.Set;

public class ValidationTask implements Observable, Observer {
    private final Set<Validator> validators;
    private final String machineCode;
    private final Set<Observer> observers = new HashSet<>();;

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
                System.out.println("Falló el " + validator.getClass().getName());
                result = false;
                break; // Detener la iteración si hay un fallo
            }
            System.out.println("Pasó el " + validator.getClass().getName());
        }
        this.notifyObservers(result);
        //System.out.println("\u001B[31mEl resultado de Core era "+result + "y ahora es "+result+"\u001B[0m");
        System.out.println("\u001B[31mResultado es " + result + "\u001B[0m");
        /*if(isValid == null || isValid != result){
            isValid = result;
            System.out.println("\u001B[31mAsi que notifico a la UI\u001B[0m");
            notifyObservers();
        }*/
    }

    @Override
    public void update(Boolean result) {
    }

    public Set<Validator> getValidators(){
        return this.validators;
    }
}
