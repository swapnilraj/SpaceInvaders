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
  private PImage playerImg;
  private PVector position;
  private ArrayList<Bullet> bullets;
  private AlienGroup group;

  @Override
  public void update(int index) {
    PVector bulletPosition = new PVector(parent.mouseX, position.y);
    bullets.add(new Bullet(parent, bulletPosition));
    this.draw();
  }

  Player(Space parent, AlienGroup group) {
    this.group = group;
    this.parent = parent;
    this.playerImg = parent.loadImage(PLAYER_IMAGE);
    this.position = new PVector(parent.mouseX, parent.height - MARGIN);
    this.bullets = new ArrayList<>();
    this.parent.addObserver(this);
  }

  public void draw() {
    parent.image(playerImg, parent.mouseX, position.y);
    for (int index = 0; index< bullets.size(); ++index) {
      bullets.get(index).draw();
      bullets.get(index).move();
      bullets.get(index).collide(group);
    }
  }
}
