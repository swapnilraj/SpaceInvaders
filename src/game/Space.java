package game;

import game.util.Observable;
import game.util.Observer;
import java.util.ArrayList;
import processing.core.PApplet;

import static game.Constants.*;

public class Space extends PApplet implements Observable {

  private AlienGroup group1;
  private AlienGroup group2;
  private Player player;
  private int bulletCount;
  private ArrayList<Observer> observers;

  public void settings() {
    fullScreen();
  }

  public void setup() {
    noStroke();
    noCursor();
    imageMode(CENTER);
    this.observers = new ArrayList<>();
    this.bulletCount = 0;
    this.group1 = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN, player);
    this.group2 = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN * 3, player);
    this.player = new Player(this, group1);
  }

  public void draw() {
    background(BACKGROUND_COLOR.getRGB());
    group1.draw();
    player.draw();
    player.collide();
  }

  public void mousePressed() {
    notifyObserver();
  }

  public static void main(String[] args) {
    PApplet.main(Space.class.getName());
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
      observer.update(bulletCount++);
    }
  }
}