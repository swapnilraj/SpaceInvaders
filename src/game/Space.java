package game;

import processing.core.PApplet;

import static game.Constants.*;

public class Space extends PApplet {

  private AlienGroup group1;
  private AlienGroup group2;
  private Player player;

  public void settings() {
    fullScreen();
  }

  public void setup() {
    noStroke();
    noCursor();
    imageMode(CENTER);
    group1 = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN);
    group2 = new AlienGroup(this, DEFAULT_GROUP_SIZE, MARGIN * 3);
    player = new Player(this);
  }

  public void draw() {
    background(BACKGROUND_COLOR.getRGB());
    group1.draw();
    group2.draw();
    player.draw();
  }

  public void mousePressed() {
    System.out.println("TEST");
  }

  public static void main(String[] args) {
    PApplet.main(Space.class.getName());
  }
}