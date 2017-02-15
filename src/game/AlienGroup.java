package game;

import game.util.Observable;
import game.util.Observer;
import java.util.ArrayList;

import static game.Constants.ALIEN_DEATH;
import static game.Constants.ALIEN_HEIGHT;
import static game.Constants.SPEED_UP;

public class AlienGroup implements Observer, Observable {

  public ArrayList<Alien> aliens;
  private ArrayList<Observer> observers;
  private ArrayList<Shield> shields;
  private int changeY;
  private Space parent;
  private int deathCount;

  @Override
  public void update(int index) {
    System.out.println(index);

    if (index == ALIEN_DEATH) {
      deathCount++;
      System.out.println(deathCount);
      if (deathCount == aliens.size()) {
        notifyObserver(ALIEN_DEATH);
      }
    } else if (index > 0) {
      for (int i = 0; i < aliens.size(); ++i) {
        aliens.get(i).changeDirection();
        aliens.get(i).updateY(changeY);
        aliens.get(i).speedUp(SPEED_UP);
      }
    }
  }

  @Override public void addObserver(Observer o) {
    observers.add(o);
  }

  @Override public void removeObserver(Observer o) {
    observers.remove(o);
  }

  @Override public void notifyObserver(int index) {
    for (Observer observer : observers) {
      observer.update(index);
    }
  }

  AlienGroup(Space parent, int size, int positionY, ArrayList<Shield> shields) {
    this.deathCount = 0;
    this.parent = parent;
    this.observers = new ArrayList<>();
    this.aliens = new ArrayList<>(size);
    this.changeY = ALIEN_HEIGHT;
    this.shields = shields;
    for (int i = 0; i < size; ++i) {
      aliens.add(new Alien(parent, i, positionY, shields));
      aliens.get(i).addObserver(this);
    }
  }

  void draw() {
    for (Alien alien : aliens) {
      alien.draw();
      alien.move();
    }
  }
}