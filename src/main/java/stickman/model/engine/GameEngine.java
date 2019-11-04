package stickman.model.engine;

import stickman.model.level.Level;

/** The GameEngine interface */
public interface GameEngine {

  /**
   * Returns the currently active level.
   *
   * @return The current level.
   */
  Level getCurrentLevel();

  /** Loads and starts the determined level */
  void startLevel();

  /**
   * Calls the current level's jump method.
   *
   * @return True if the hero will jump, else false.
   */
  boolean jump();

  /**
   * Calls the current level's moveLeft method.
   *
   * @return True if the hero will move left, else false.
   */
  boolean moveLeft();

  /**
   * Calls the current level's moveRight method.
   *
   * @return True if the hero will move right, else false.
   */
  boolean moveRight();

  /**
   * Calls the current level's stopMoving method.
   *
   * @return True if the hero will cease all movement, else false.
   */
  boolean stopMoving();

  /** Calls the current level's tick method. */
  void tick();

  /**
   * Returns a flag to check if the level is finished or not.
   * @return
   */
  boolean isFinish();

  /**
   * Returns a flag to check if the game is won or not.
   * @return
   */
  boolean getWinner();

  /**
   * Returns the game's total score.
   * @return
   */
  double getTotalScore();

  /**
   * Returns the level's current score.
   * @return
   */
  double getLevelScore();

  /**
   * Adds to the level's score for a kill.
   */
  void killScore();

  /**
   * Saves the game's current level and all its elements.
   */
  void saveGame();

  /**
   * Loads the most recent saved level's state.
   */
  void quickLoad();
}
