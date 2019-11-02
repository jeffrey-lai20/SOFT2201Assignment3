package stickman.model.entity.impl;

import stickman.model.entity.Damageable;
import stickman.model.entity.Entity;
import stickman.model.entity.strategy.EnemyStrategy;
import stickman.model.level.Level;
import stickman.model.level.collision.CollisionDirection;

/** The enemy Entity type. */
public class EnemyEntity extends AbstractEntity implements Damageable {

  private EnemyStrategy strategy;
  private int lives;

  public EnemyEntity(
      String imagePath,
      double xPos,
      double yPos,
      double width,
      double height,
      Layer layer,
      EnemyStrategy strategy) {
    super(imagePath, xPos, yPos, width, height, layer);
    this.strategy = strategy;
    this.lives = 1;
  }

  @Override
  public void move(Level level) {
    strategy.think(level, this);
  }

  @Override
  public void die(Level level) {
    level.getEntities().remove(this);
    level.killedEnemy();
  }

  @Override
  public void takeDamage(Level level) {

    int newLives = lives - 1;

    if (newLives <= 0) {
      die(level);
    } else {
      this.lives = newLives;
    }
  }

  @Override
  public int getLives() {
    return lives;
  }

  @Override
  public boolean handleCollision(Entity other, CollisionDirection direction, Level level) {

    if (other instanceof HeroEntity) {

      if (!direction.equals(CollisionDirection.BOTTOM)) {
        return false;
      }

      takeDamage(level);
      return true;
    }

    return false;
  }

  // exposed for testing
  @Override
  public EnemyStrategy getStrategy() {
    return strategy;
  }
  @Override
  public double getVelocity() {
    return Double.NaN;
  }
}
