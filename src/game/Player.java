package game;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.MARGIN;
import static game.Constants.PLAYER_IMAGE;

/**
 * Created by thawne on 07/02/17.
 */
public class Player extends PApplet {

  Space parent;
  PImage playerImg;
  PVector position;

  Player(Space parent) {
    this.parent = parent;
    this.playerImg = parent.loadImage(PLAYER_IMAGE);
    this.position = new PVector(mouseX, parent.height - MARGIN);
  }

  public static void main(String[] args) {
    PApplet.main(Alien.class.getName());
  }

  public void draw() {
    parent.image(playerImg, parent.mouseX, position.y);
  }
}
