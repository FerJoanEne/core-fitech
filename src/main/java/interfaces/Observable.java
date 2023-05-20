package interfaces;

import java.util.HashSet;
import java.util.Set;

public interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

    void notifyObservers(Boolean result);
}
