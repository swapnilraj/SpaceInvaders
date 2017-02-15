package game;

import game.util.Observable;
import game.util.Observer;
import java.util.ArrayList;
import java.util.Locale;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static game.Constants.ALIEN_EXPLODE_IMAGE;
import static game.Constants.ALIEN_IMAGE;
import static game.Constants.ALIEN_SPEED;
import static game.Constants.POWER_IMAGE;
import static game.Constants.SCALING_SINE;

public class Alien extends PApplet implements Observable {

  public PImage alienImage;
  public PImage powerUpImage;
  public boolean dropPower;
  private Space parent;
  public PVector position;
  private PVector velocity;
  private int index;
  private boolean hasExploded;
  private boolean isSinusoidal;
  private PowerUp powerUp;

  private ArrayList<Observer> observers = new ArrayList<Observer>();

  public Alien(Space parent, int index, int positionY) {
    this.dropPower = false;
    this.powerUpImage = parent.loadImage(POWER_IMAGE);
    this.alienImage =
        parent.loadImage(String.format(Locale.ENGLISH, ALIEN_IMAGE, (int) random(-1, 3)));
    this.index = index;
    this.parent = parent;
    this.position = new PVector(parent.width - (index + 1) * alienImage.width, positionY);
    this.velocity = new PVector(-ALIEN_SPEED, ALIEN_SPEED);
    this.isSinusoidal = index % 3 == 0;
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
      observer.update(index);
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
      parent.image(alienImage, position.x,
          (isSinusoidal) ? getYPosition(position.x) + position.y : position.y);
    }
    if (dropPower) {
      powerUp.draw();
      //parent.image(powerUpImage, position.x, position.y);
      if (powerUp.offscreen()) {
        dropPower = false;
      }
    }
  }

  public void speedUp(int speedFactor) {
    velocity.x += speedFactor;
  }

  private float getYPosition(float positionX) {
    float temp = map(positionX, 0, width + 1, 0, 6.28f);
    return SCALING_SINE * sin(temp);
  }

  public void move() {
    if (!hasExploded) {
      if (position.x + alienImage.width / 2 <= alienImage.width
          || position.x + alienImage.width / 2 >= parent.width) {
        notifyObserver();
      }
      position.x += velocity.x;
    }
    if (dropPower) {
      //position.add(0, velocity.y);
      powerUp.move();
    }
  }

  public void explode() {
    hasExploded = true;
    this.alienImage = parent.loadImage(ALIEN_EXPLODE_IMAGE);
    this.dropPower = true;
    powerUp = new PowerUp(parent, position, POWER_IMAGE);
    draw();
  }

  public PVector getPosition() {
    return position;
  }

  public static void main(String[] args) {
    PApplet.main(Alien.class.getName());
  }
}