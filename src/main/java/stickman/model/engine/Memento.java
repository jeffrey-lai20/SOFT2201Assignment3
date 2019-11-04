package stickman.model.engine;

import stickman.model.level.Level;

/**
 * Saves the level's current state.
 */
public class Memento {
    private Level state;

    public Memento (Level state) {
        this.state = state;
    }

    /**
     * Returns the current saved level's state and all it's saved attributes.
     * @return
     */
    public Level getState() {
        Level currentLevel = Originator.initState(state);
        return currentLevel;
    }
}
