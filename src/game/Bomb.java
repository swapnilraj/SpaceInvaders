package game;

import java.util.ArrayList;
import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.ALIEN_SPEED;

public class Bomb {
  public PImage bomb;
  public PVector position;

  private Space parent;
  private boolean toDrop;
  private ArrayList<Shield> shields;

  Bomb() {
  }

  Bomb(Space parent, float positionX, float positionY, String imageLocation) {
    this.bomb = parent.loadImage(imageLocation);
    this.position = new PVector(positionX, positionY);
    this.parent = parent;
    this.toDrop = false;
  }

  Bomb(Space parent, float positionX, float positionY, String imageLocation,
      ArrayList<Shield> shields) {
    this.bomb = parent.loadImage(imageLocation);
    this.position = new PVector(positionX, positionY);
    this.parent = parent;
    this.toDrop = false;
    this.shields = shields;
  }

  public void updatePosition(float positionX, float positionY) {
    this.position = new PVector(positionX, positionY);
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

  public void collide() {
    for (int index = 0; index < shields.size(); ++index) {
      Shield shield = shields.get(index);
      if (position.x >= shield.position.x - shield.shieldImage.width / 2
          && position.x <= shield.position.x + shield.shieldImage.width / 2
          && position.y >= shield.position.y - shield.shieldImage.height / 2
          && position.y <= shield.position.y + shield.shieldImage.height / 2) {
        shield.hit();
      }
    }
  }
}
