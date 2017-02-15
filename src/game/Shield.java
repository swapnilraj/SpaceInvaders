package game;

import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.MARGIN;
import static game.Constants.SHIELD_IMAGE;

public class Shield {

  private Space parent;
  private PVector position;
  private PImage shieldImage;
  private int hitCount;

  Shield(Space parent, int index, int maxShieldCount) {
    this.parent = parent;
    this.shieldImage = parent.loadImage(SHIELD_IMAGE);
    this.position = new PVector((index * parent.width / maxShieldCount) + shieldImage.width, parent.height - 8 * MARGIN);
    this.hitCount = 0;
  }

  public void draw() {
    parent.image(shieldImage, position.x, position.y);
  }

  public void hit() {
    ++this.hitCount;
  }
}
