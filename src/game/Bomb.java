package game;

import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.ALIEN_SPEED;

public class Bomb {
  private PImage bomb;
  private PVector position;
  private PVector velocity;
  private Space parent;

  Bomb() {};

  Bomb(Space parent, PVector position, String imageLocation) {
    this.bomb = parent.loadImage(imageLocation);
    this.velocity = new PVector(ALIEN_SPEED, ALIEN_SPEED);
    this.position = position;
    this.parent = parent;
  }

  public void draw() {
    parent.image(bomb, position.x, position.y);
  }

  public void move() {
    position.add(0, velocity.y);
  }

  public boolean offscreen() {
    return position.y > parent.height;
  }

}
