package interfaces;

public interface Validator extends Observable {

    boolean validate(String username, String machineCode);

    boolean getResult();

}
