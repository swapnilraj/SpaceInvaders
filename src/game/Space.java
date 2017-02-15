package game;

import game.util.Observable;
import game.util.Observer;
import java.util.ArrayList;
import processing.core.PApplet;

import static game.Constants.BACKGROUND_COLOR;
import static game.Constants.DEFAULT_GROUP_SIZE;
import static game.Constants.MARGIN;
import static game.Constants.MAX_SHIELD_COUNT;

public class Space extends PApplet implements Observable {

  private AlienGroup group1;
  private AlienGroup group2;
  private Player player;
  private int bulletCount;
  private ArrayList<Shield> shields;
  private ArrayList<Observer> observers;

  public void settings() {
    fullScreen();
  }

  public void setup() {
    noStroke();
    noCursor();
    imageMode(CENTER);
    this.observers = new ArrayList<>();
    this.shields = new ArrayList<>();
    this.bulletCount = 0;
    this.group1 = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN, player);
    this.group2 = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN, player);
    for (int index = 0; index < MAX_SHIELD_COUNT; ++index) {
      shields.add(new Shield(this, index, MAX_SHIELD_COUNT));
    }
    this.player = new Player(this, group1);
  }

  public void draw() {
    background(BACKGROUND_COLOR.getRGB());
    group1.draw();
    player.draw();
    player.collide();
    for (Shield shield : shields) {
      shield.draw();
    }
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