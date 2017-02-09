package game;

import game.util.Observer;
import java.util.ArrayList;
import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.MARGIN;
import static game.Constants.PLAYER_IMAGE;

/**
 * Created by thawne on 07/02/17.
 */
public class Player implements Observer {

  private Space parent;
  public PImage playerImg;
  public PVector position;
  private ArrayList<Bullet> bullets;
  private AlienGroup group;
  private boolean hasPoweredUp;

  @Override
  public void update(int index) {
    PVector bulletPosition = new PVector(parent.mouseX, position.y);
    bullets.add(new Bullet(parent, bulletPosition));
    if (hasPoweredUp) {
      bulletPosition.add(20, 0);
      bullets.add(new Bullet(parent, bulletPosition));
    }
    this.draw();
  }

  Player(Space parent, AlienGroup group) {
    this.group = group;
    this.parent = parent;
    this.playerImg = parent.loadImage(PLAYER_IMAGE);
    this.position = new PVector(parent.mouseX, parent.height - MARGIN);
    this.bullets = new ArrayList<>();
    this.parent.addObserver(this);
    this.hasPoweredUp = false;
  }

  public void draw() {
    position.x = parent.mouseX;
    parent.image(playerImg, position.x, position.y);
    for (int index = 0; index < bullets.size(); ++index) {
      bullets.get(index).draw();
      bullets.get(index).move();
      bullets.get(index).collide(group);
    }
  }

  public void collide() {
    ArrayList<Alien> aliens = group.aliens;
    for (Alien alien : aliens) {
      if (position.x >= alien.position.x - alien.powerUpImage.width / 2
          && position.x <= alien.position.x + alien.powerUpImage.width / 2
          && position.y >= alien.position.y - alien.powerUpImage.height / 2
          && position.y <= alien.position.y + alien.powerUpImage.height / 2) {
        this.hasPoweredUp = true;
      }
    }
  }
}
