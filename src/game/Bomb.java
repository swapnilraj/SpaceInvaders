package game;

import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.ALIEN_SPEED;
import static game.Constants.BOMB_IMAGE;

public class Bomb {
  private PImage bomb;
  private PVector position;
  private PVector velocity;
  private Space parent;

  Bomb(Space parent, PVector position) {
    this.bomb = parent.loadImage(BOMB_IMAGE);
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
