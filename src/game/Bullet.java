package game;

import java.util.ArrayList;
import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.BULLET_IMAGE;
import static game.Constants.BULLET_SPEED;
import static game.Constants.DEFAULT_GROUP_SIZE;

public class Bullet {

  private PImage bulletImg;
  private Space parent;
  private PVector position;
  private ArrayList<Shield> shields;

  Bullet(Space parent, PVector position, ArrayList<Shield> shields) {
    this.parent = parent;
    this.position = position;
    this.bulletImg = this.parent.loadImage(BULLET_IMAGE);
    this.shields = shields;
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
    for (int index = 0; index < shields.size(); ++index) {
      Shield shield = shields.get(index);
      if (position.x >= shield.position.x - shield.shieldImage.width / 2
          && position.x <= shield.position.x + shield.shieldImage.width / 2
          && position.y >= shield.position.y - shield.shieldImage.height / 2
          && position.y <= shield.position.y + shield.shieldImage.height / 2) {
        shield.hit();
        position.y -= shield.shieldImage.height;
      }
    }
  }
}