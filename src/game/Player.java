package game;

import java.util.ArrayList;
import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.MARGIN;
import static game.Constants.PLAYER_IMAGE;

/**
 * Created by thawne on 07/02/17.
 */
public class Player implements game.Observer {

  private Space parent;
  private PImage playerImg;
  private PVector position;
  private ArrayList<Bullet> bullets;

  @Override
  public void update(int index) {
    PVector bulletPosition = new PVector(parent.mouseX, position.y);
    bullets.add(new Bullet(parent, bulletPosition));
  }

  Player(Space parent) {
    this.parent = parent;
    this.playerImg = parent.loadImage(PLAYER_IMAGE);
    this.position = new PVector(parent.mouseX, parent.height - MARGIN);
    this.bullets = new ArrayList<>();
  }

  public void draw() {
    parent.image(playerImg, parent.mouseX, position.y);
    for (Bullet bullet : bullets) {
      bullet.draw();
      bullet.move();
    }
  }
}
