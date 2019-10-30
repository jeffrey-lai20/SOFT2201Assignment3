package stickman.model.engine;

import stickman.model.level.Level;
import stickman.model.level.LevelImpl;

public class Memento {
    private Level state;

    public Memento (Level state) {
        this.state = state;
    }

    public Level getState() {
        Level currentLevel = new LevelImpl(state.getProvider());
        return currentLevel;
    }
}
