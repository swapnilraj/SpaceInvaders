package game;

import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.MARGIN;
import static game.Constants.SHIELD_IMAGE;

public class Shield {

  private Space parent;
  private PVector position;
  private PImage shieldImage;

  Shield(Space parent, int index, int maxShieldCount) {
    this.parent = parent;
    position = new PVector((index + 1) * parent.width / maxShieldCount, parent.height - 8 * MARGIN);
    this.shieldImage = parent.loadImage(SHIELD_IMAGE);
  }

  public void draw() {
    parent.image(shieldImage, position.x, position.y);
  }
}
