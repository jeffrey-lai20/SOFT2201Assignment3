package stickman.model.engine;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {

    private List<Memento> mementos = new ArrayList<>();

    public void add(Memento state) {
        mementos.add(state);
    }

    public Memento get(int i) {
        return mementos.get(i);
    }

    public int size() {
        return mementos.size();
    }
}
