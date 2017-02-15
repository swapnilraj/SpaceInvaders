package game;

import game.util.Observable;
import game.util.Observer;
import java.util.ArrayList;
import java.util.Locale;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.ALIEN_DEATH;
import static game.Constants.ALIEN_EXPLODE_IMAGE;
import static game.Constants.ALIEN_IMAGE;
import static game.Constants.ALIEN_SPEED;
import static game.Constants.BOMB_IMAGE;
import static game.Constants.POWER_IMAGE;
import static game.Constants.SCALING_SINE;

import static processing.core.PApplet.map;
import static processing.core.PApplet.sin;

public class Alien implements Observable {

  public PImage alienImage;
  private Space parent;
  public PVector position;
  private PVector velocity;
  private int index;
  private boolean hasExploded;
  private boolean isSinusoidal;
  public PowerUp powerUp;
  public Bomb bomb;

  private ArrayList<Observer> observers = new ArrayList<Observer>();

  Alien(Space parent, int index, int positionY) {
    this.alienImage =
        parent.loadImage(String.format(Locale.ENGLISH, ALIEN_IMAGE, (int) parent.random(-1, 3)));
    this.index = index;
    this.parent = parent;
    this.position = new PVector(parent.width - (index + 1) * alienImage.width, positionY);
    this.velocity = new PVector(-ALIEN_SPEED, 0);
    this.isSinusoidal = index % 3 == 0;
    this.powerUp = new PowerUp(this.parent, position.x, position.y, POWER_IMAGE);
    this.bomb = new Bomb(this.parent, position.x, position.y, BOMB_IMAGE);
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
  public void notifyObserver(int value) {
    for (Observer observer : observers) {
      observer.update(value);
    }
  }

  public void updateY(int positionYchange) {
    int current = 0;
    while (current <= positionYchange) {
      position.add(0, 1);
      ++current;
    }
  }

  public void changeDirection() {
    velocity.x = -velocity.x;
  }

  public boolean getStatus() {
    return hasExploded;
  }

  public void draw() {
    if (!hasExploded) {
      if (!bomb.getDropState() && (int) parent.random(2000) == 2) {
        bomb.setDrop();
      }
      parent.image(alienImage, position.x,
          (isSinusoidal) ? getYPosition(position.x) + position.y : position.y);
    }
    if (powerUp.getDropState()) {
      powerUp.draw();
      if (powerUp.offscreen()) {
        powerUp.unSetDrop();
      }
    }
    if (bomb.getDropState()) {
      bomb.draw();
      if (bomb.offscreen()) {
        bomb.unSetDrop();
      }
    }
  }

  public void speedUp(int speedFactor) {
    velocity.x += speedFactor;
  }

  private float getYPosition(float positionX) {
    float temp = map(positionX, 0, parent.width + 1, 0, 6.28f);
    return SCALING_SINE * sin(temp);
  }

  public void move() {
    if (!hasExploded) {
      if (position.x + alienImage.width / 2 <= alienImage.width
          || position.x + alienImage.width / 2 >= parent.width) {
        notifyObserver(index);
      }
      this.position.x += velocity.x;
    }
    if (powerUp.getDropState()) {
      powerUp.move();
    }
    if (bomb.getDropState()) {
      bomb.position.y += 2;
    }
  }

  public void explode() {
    hasExploded = true;
    this.alienImage = parent.loadImage(ALIEN_EXPLODE_IMAGE);
    powerUp.setDrop();
    draw();
    notifyObserver(ALIEN_DEATH);
  }

  public PVector getPosition() {
    return position;
  }

  public static void main(String[] args) {
    PApplet.main(Alien.class.getName());
  }
}