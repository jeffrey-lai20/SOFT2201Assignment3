package stickman.model.entity.impl;

/** The tile/platform Entity type. */
public class TileEntity extends AbstractEntity {

  public TileEntity(
      String imagePath, double xPos, double yPos, double width, double height, Layer layer) {
    super(imagePath, xPos, yPos, width, height, layer);
  }
}
