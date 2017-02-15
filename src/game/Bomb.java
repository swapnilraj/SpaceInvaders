package game;

import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.ALIEN_SPEED;

public class Bomb {
  public PImage bomb;
  public PVector position;

  private Space parent;
  private boolean toDrop;

  Bomb() {
  }

  Bomb(Space parent, float positionX, float positionY, String imageLocation) {
    this.bomb = parent.loadImage(imageLocation);
    this.position = new PVector(positionX, positionY);
    this.parent = parent;
    this.toDrop = false;
  }

  public void draw() {
    parent.image(bomb, this.position.x, this.position.y);
  }

  public void move() {
    this.position.y += ALIEN_SPEED;
  }

  public boolean offscreen() {
    return position.y > parent.height;
  }

  public void setDrop() {
    this.toDrop = true;
  }

  public void unSetDrop() {
    this.toDrop = false;
  }

  public boolean getDropState() {
    return toDrop;
  }
}
