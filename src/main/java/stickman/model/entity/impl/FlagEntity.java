package stickman.model.entity.impl;

import stickman.model.entity.strategy.EnemyStrategy;

/** The flag Entity type. */
public class FlagEntity extends AbstractEntity {

  public FlagEntity(
      String imagePath, double xPos, double yPos, double width, double height, Layer layer) {
    super(imagePath, xPos, yPos, width, height, layer);
  }

  @Override
  public EnemyStrategy getStrategy() {
    return null;
  }
  @Override
  public double getVelocity() {
    return Double.NaN;
  }
}
