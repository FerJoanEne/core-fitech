package core;

import interfaces.Observable;
import interfaces.Observer;
import interfaces.Validator;
import services.ValidationTask;
import services.ValidatorFactory;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class CoreFitech  implements Observable, Observer{
    private final ValidationTask validationTask;
    private final Set<Observer> observers = new HashSet<>();
    private Boolean isValid;

    public CoreFitech(String path, String machineCode) throws FileNotFoundException {
        System.out.println("\u001B[31mCuando se inicia el Core isValid es: "+isValid+"\u001B[0m");
        this.validationTask = new ValidatorFactory().create(path, machineCode);
        this.addObserver();
    }

    private void addObserver(){
        for(Validator validator : validationTask.getValidators()){
            if(Observable.class.isAssignableFrom(validator.getClass())){
                validator.addObserver(this);
            }
        }
    }

    public void processRequest(String userName){
        isValid = null;
        validationTask.validate(userName);
    }

    public boolean getResult(){
        return this.isValid;
    }

    @Override
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update();
        }
    }

    public Set<Validator> getValidators(){
        return validationTask.getValidators();
    }

    @Override
    public void update() {
        Set<Validator> validators = validationTask.getValidators();
        boolean result = true;
        for(Validator validator : validators){
            if(!validator.getResult()){
                System.out.println("Falló el " + validator.getClass().getName());
                result = false;
            }else{
                System.out.println("Pasó el " + validator.getClass().getName());
            }
        }
        System.out.println("\u001B[31mEl resultado de Core era "+isValid + "y ahora es "+result+"\u001B[0m");
        if(isValid == null || isValid != result){
            isValid = result;
            System.out.println("\u001B[31mAsi que notifico a la UI\u001B[0m");
            notifyObservers();
        }

    }
}