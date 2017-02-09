package game;

import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.ALIEN_EXPLODE_IMAGE;
import static game.Constants.BULLET_IMAGE;
import static game.Constants.BULLET_SPEED;
import static game.Constants.DEFAULT_GROUP_SIZE;

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
    if (position.y > 0) {
      parent.image(bulletImg, position.x, position.y);
    }
  }

  public void move() {
    position.y -= BULLET_SPEED;
  }

  public void collide(AlienGroup group) {
    for (int index = 0; index < DEFAULT_GROUP_SIZE; ++index) {
      Alien currentAlien = group.aliens.get(index);
      PVector alienPosition = group.aliens.get(index).getPosition();
      if (position.x >= alienPosition.x - currentAlien.alienImage.width / 2
          && position.x <= alienPosition.x + currentAlien.alienImage.width / 2
          && position.y >= alienPosition.y - currentAlien.alienImage.height / 2
          && position.y <= alienPosition.y + currentAlien.alienImage.height / 2) {
        if (!currentAlien.getStatus()) {
          currentAlien.explode();
        }
      }
    }
  }
}