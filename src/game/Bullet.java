package game;

import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.BULLET_IMAGE;
import static game.Constants.BULLET_SPEED;

public class Bullet {

  private PImage bulletImg;
  private Space parent;
  private PVector position;

  Bullet(Space parent, PVector position) {
    this.parent = parent;
    this.position = position;
    this.bulletImg = this.parent.loadImage(BULLET_IMAGE);
  }

  public void draw() {
    parent.image(bulletImg, position.x, position.y);
  }

  public void move() {
    position.y -= BULLET_SPEED;
  }
}
