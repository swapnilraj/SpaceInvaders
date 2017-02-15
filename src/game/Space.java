package game;

import game.util.Observable;
import game.util.Observer;
import java.util.ArrayList;
import processing.core.PApplet;

import static game.Constants.ALIEN_DEATH;
import static game.Constants.BACKGROUND_COLOR;
import static game.Constants.DEFAULT_GROUP_SIZE;
import static game.Constants.LOSE_MESSAGE;
import static game.Constants.MARGIN;
import static game.Constants.MAX_SHIELD_COUNT;
import static game.Constants.PLAYER_DEATH;
import static game.Constants.WIN_MESSAGE;

public class Space extends PApplet implements Observable, Observer {

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
    textSize(32);
    this.observers = new ArrayList<>();
    this.shields = new ArrayList<>();
    this.bulletCount = 0;
    for (int index = 0; index < MAX_SHIELD_COUNT; ++index) {
      shields.add(new Shield(this, index, MAX_SHIELD_COUNT));
    }
    this.group1 = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN, shields);
    group1.addObserver(this);
    //this.group2 = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN);
    this.player = new Player(this, group1, shields);
    player.addObserver(this);
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

  public void alienDeath() {
    background(0);
    textAlign(CENTER, CENTER);
    text(WIN_MESSAGE, width / 2, height / 2);
    noLoop();
  }

  public void playerDeath() {
    background(0);
    textAlign(CENTER, CENTER);
    text(LOSE_MESSAGE, width / 2, height / 2);
    noLoop();
  }

  public void mousePressed() {
    notifyObserver(0);
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
  public void notifyObserver(int value) {
    for (Observer observer : observers) {
      observer.update(bulletCount++);
    }
  }

  @Override public void update(int index) {
    switch (index) {
      case ALIEN_DEATH:
        alienDeath();
        break;
      case PLAYER_DEATH:
        playerDeath();
        break;
    }
  }
}