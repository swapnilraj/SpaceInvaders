package game;

import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.MARGIN;
import static game.Constants.PLAYER_IMAGE;

/**
 * Created by thawne on 07/02/17.
 */
public class Player {

  Space parent;
  PImage playerImg;
  PVector position;

  Player(Space parent) {
    this.parent = parent;
    this.playerImg = parent.loadImage(PLAYER_IMAGE);
    this.position = new PVector(parent.mouseX, parent.height - MARGIN);
  }

  public void draw() {
    parent.image(playerImg, parent.mouseX, position.y);
  }
}
