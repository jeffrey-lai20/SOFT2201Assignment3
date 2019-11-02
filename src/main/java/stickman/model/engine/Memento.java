package stickman.model.engine;

import stickman.model.level.Level;

public class Memento {
    private Level state;

    public Memento (Level state) {
        this.state = state;
    }

    public Level getState() {

        Level currentLevel = Originator.initState(state);
        return currentLevel;
    }
}
