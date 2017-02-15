package game;

import game.util.Observable;
import game.util.Observer;
import java.util.ArrayList;
import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.MARGIN;
import static game.Constants.PLAYER_IMAGE;

public class Player implements Observer, Observable {

  private Space parent;
  public PImage playerImg;
  public PVector position;
  private ArrayList<Bullet> bullets;
  private AlienGroup group;
  private boolean hasPoweredUp;
  private ArrayList<Observer> observers;

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

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  @Override
  public void notifyObserver() {
    for (Observer observer : observers) {
      observer.update(9);
    }
  }

  Player(Space parent, AlienGroup group) {
    this.group = group;
    this.parent = parent;
    this.playerImg = parent.loadImage(PLAYER_IMAGE);
    this.position = new PVector(parent.mouseX, parent.height - MARGIN);
    this.bullets = new ArrayList<>();
    this.parent.addObserver(this);
    this.hasPoweredUp = false;
    this.observers = new ArrayList<>();
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
      if (position.x >= alien.powerUp.position.x - alien.powerUp.bomb.width / 2
          && position.x <= alien.powerUp.position.x + alien.powerUp.bomb.width / 2
          && position.y >= alien.powerUp.position.y - alien.powerUp.bomb.height / 2
          && position.y <= alien.powerUp.position.y + alien.powerUp.bomb.height / 2) {
        this.hasPoweredUp = true;
      }
      if (position.x >= alien.bomb.position.x - alien.bomb.bomb.width / 2
          && position.x <= alien.bomb.position.x + alien.bomb.bomb.width / 2
          && position.y >= alien.bomb.position.y - alien.bomb.bomb.height / 2
          && position.y <= alien.bomb.position.y + alien.bomb.bomb.height / 2) {
        notifyObserver();
      }
    }
  }
}
