package game;

import java.util.ArrayList;

import static game.Constants.ALIEN_HEIGHT;
import static game.Constants.FRAME_COUNT_EXPLODE;
import static game.Constants.SPEED_UP;

public class AlienGroup implements game.Observer {

  private ArrayList<Alien> aliens;
  private int changeY;
  private Space parent;

  @Override
  public void update(int index) {
    System.out.println(index);
    for (int i = 0; i < aliens.size(); ++i) {
      aliens.get(i).changeDirection();
      aliens.get(i).updateY(changeY);
      aliens.get(i).speedUp(SPEED_UP);
    }
  }

  AlienGroup(Space parent, int size, int positionY) {
    this.parent = parent;
    this.aliens = new ArrayList<>(size);
    this.changeY = ALIEN_HEIGHT;
    for (int i = 0; i < size; ++i) {
      aliens.add(new Alien(parent, i, positionY));
      aliens.get(i).addObserver(this);
    }
  }

  void draw() {
    boolean explodedOne = true;
    for (Alien alien : aliens) {
      alien.draw();
      alien.move();
      if (parent.frameCount % FRAME_COUNT_EXPLODE == 0 && explodedOne) {
        int random = (int) (parent.random(-1, 10));
        if (!aliens.get(random).getStatus()) {
          aliens.get(random).explode();
          explodedOne = false;
        }
      }
    }
  }
}