package game;

import processing.core.PApplet;

import static game.Constants.*;

public class Space extends PApplet {

  private AlienGroup group;


  public void settings() {
    fullScreen();
  }

  public void setup() {
    noStroke();
    noCursor();
    imageMode(CENTER);
    group = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN);
  }

  public void draw() {
    background(BACKGROUND_COLOR.getRGB());
    group.draw();
  }

  public static void main(String[] args) {
    PApplet.main(Space.class.getName());
  }
}