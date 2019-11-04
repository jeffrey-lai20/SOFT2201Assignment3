package stickman.model.level;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import stickman.config.ConfigurationProvider;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.spawner.EntitySpawner;
import stickman.model.level.collision.CollisionHandler;

/** The Level interface */
public interface Level {

  /**
   * Starts the level running
   *
   * @param provider the ConfigurationProvider
   */
  void start(ConfigurationProvider provider);

  /**
   * Stops and completes the level
   *
   * @param outcome how the player finished the level (e.g. DEATH, FLAG)
   */
  void finish(String outcome);

  /**
   * Get a list of all Entities assigned to the level
   *
   * @return the level's entities
   */
  List<Entity> getEntities();

  /**
   * Get the level's size along the x-axis.
   *
   * @return the level's width
   */
  double getWidth();

  /**
   * Get the level's floor height.
   *
   * @return the level's floor height
   */
  double getFloorHeight();

  /**
   * Get the hero's current x-axis position.
   *
   * @return the hero's x value
   */
  double getHeroX();

  /**
   * Get the hero's current y-axis position.
   *
   * @return the hero's y value
   */
  double getHeroY();

  /**
   * Get the Instant in which start() was called
   *
   * @return the game's start Instant
   */
  Instant getStartTime();

  /**
   * Attempt to make the hero jump.
   *
   * @return True if the hero will jump, else false
   */
  boolean jump();

  /**
   * Attempt to move the hero left.
   *
   * @return True if the hero will move left, else false
   */
  boolean moveLeft();

  /**
   * Attempt to move the hero right.
   *
   * @return True if the hero will move right, else false
   */
  boolean moveRight();

  /**
   * Attempt to stop the hero's movement.
   *
   * @return True if the hero will cease all movement, else false
   */
  boolean stopMoving();

  /** Updates the position of all entities in the level. */
  void tick();

  /**
   * Returns the current time.
   * @param duration
   * @return
   */
  Long prettyTimeFormat(Duration duration);

  /**
   * Returns the number level.
   * @return
   */
  int getLevelNum();

  /**
   * Sets the level's number level.
   * @param levelNum
   */
  void setLevelNum(int levelNum);

  /**
   * Flag showing whether the level is finished or not.
   * @return
   */
  boolean isFinish();

  /**
   * Returns the target time for the level.
   * @return
   */
  double getTarget();

  /**
   * Flag showing whether the hero is dead or not.
   * @return
   */
  boolean getIsDead();

  /**
   * Flag showing whether the hero has killed an enemy or not.
   * @return
   */
  boolean enemyKill();

  /**
   * Sets the flag to determine the hero has killed an enemy.
   */
  void killedEnemy();

  /**
   * Sets the flag to determine the hero has not killed an enemy.
   */
  void noKill();

  /**
   * Returns the level's EntitySpawner.
   * @return
   */
  EntitySpawner getEntitySpawner();

  /**
   * Returns the level's CollisionHandler
   * @return
   */
  CollisionHandler getCollisionHandler();

  /**
   * Returns the level's hero.
   * @return
   */
  Controllable getHero();

  /**
   * Returns the flag determining if the level is finished or not.
   * @return
   */
  boolean getIsFinish();

  /**
   * Returns the flag determining if the hero has killed an enemy or not.
   * @return
   */
  boolean getEnemyKill();

  /**
   * Returns the max number of levels for the game.
   * @return
   */
  double getLevels();
}
