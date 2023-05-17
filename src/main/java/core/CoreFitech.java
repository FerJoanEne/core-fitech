package core;

import interfaces.Observer;
import services.ValidationTask;
import services.ValidatorFactory;

import java.io.FileNotFoundException;

public class CoreFitech {

    private final ValidationTask validationTask;

    public CoreFitech(String path, String machineCode) throws FileNotFoundException {
        //System.out.println("\u001B[31mCuando se inicia el Core isValid es: "+isValid+"\u001B[0m");
        ValidatorFactory validatorFactory = new ValidatorFactory(path, machineCode);
        this.validationTask = validatorFactory.getValidationTask();
    }

    public ValidationTask subscribe(Observer observer) {
        this.validationTask.addObserver(observer);
        return this.validationTask;
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 2) {
            System.out.println("Debe proporcionar el path al directorio y el código de la máquina");
            System.exit(0);
        }
        String path = args[0];
        String machineCode = args[1];
        CoreFitech coreFitech = new CoreFitech(path, machineCode);
        coreFitech.validationTask.processRequest("Tahiel");
    }
}