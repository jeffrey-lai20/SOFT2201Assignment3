package stickman.model.engine;

import stickman.model.level.Level;
import stickman.model.level.LevelImpl;

public class Originator {

    private Level state;

    public void setState(Level state) {
        Level newState = new LevelImpl(state.getProvider());
        this.state = newState;
    }

    public Level getState() {
        return state;
    }

    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }


}
