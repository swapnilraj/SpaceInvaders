package game;

import processing.core.PImage;

public class Bullet {

  private PImage bulletImg;
  private Space parent;

  public void draw(Space parent) {
    this.parent = parent;
  }

  public static void main(String[] args) {

  }
}
