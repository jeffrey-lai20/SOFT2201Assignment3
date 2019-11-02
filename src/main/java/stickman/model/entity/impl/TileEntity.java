package stickman.model.entity.impl;

import stickman.model.entity.strategy.EnemyStrategy;

/** The tile/platform Entity type. */
public class TileEntity extends AbstractEntity {

  public TileEntity(
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
