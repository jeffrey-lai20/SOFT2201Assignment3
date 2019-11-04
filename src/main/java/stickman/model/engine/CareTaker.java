package stickman.model.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Caretaker class for saving and loading the game.
 */
public class CareTaker {

    private List<Memento> mementos = new ArrayList<>();

    /**
     * Adds a memento to the list of saved mementos.
     * @param state
     */
    public void add(Memento state) {
        mementos.add(state);
    }

    /**
     * Returns the desired saved memento.
     * @param i
     * @return
     */
    public Memento get(int i) {
        return mementos.get(i);
    }

    /**
     * Returns the size of the list of current mementos.
     * @return
     */
    public int size() {
        return mementos.size();
    }
}
