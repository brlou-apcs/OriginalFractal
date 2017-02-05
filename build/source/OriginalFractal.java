import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class OriginalFractal extends PApplet {



/* Increase/decrease scale for number of triangles */
public int scl = 20;

/* Size of terrain */
public int w = 750;
public int h = 1000;

/* Number in grid */
public int cols = w/scl;
public int rows = h/scl;

/* 2d array to store z coodinate of vertex points */
public float[][] terrain = new float[cols][rows];

/* Reduces to give moving effect */
public float flying = 0;

public int y = 0;

public void setup() {
  
  frameRate(60);
}

public void draw() {

  background(0);
  stroke(255);
  fill(0,100,0);
  lights();
  translate(width/2,height/2);
  rotateX(PI/3);
  translate(-w/2,-h/2);

  flying -= 0.1f;
  float yNoise = flying;

  for(int y = 0; y < rows; y++) {

    float xNoise = 0;

    for(int x = 0; x < cols; x++) {
      /* Add z coor after perlin noised to 2d array */
      terrain[x][y] = map(noise(xNoise, yNoise), 0, 1, -150, 150); // Change last two params for height
      xNoise += 0.1f; // change to make terrain more smooth/rough
    }

    yNoise += 0.1f; // change to make terrain more smooth/rough

  }

  for(y = 0; y < rows-1; y++) {
    beginShape(TRIANGLE_STRIP); /* Connects verticies with triangles */
    // recurse(0);
    for(int x = 0; x < cols; x++) {
      vertex(x*scl,y*scl,terrain[x][y]);
      vertex(x*scl,(y+1)*scl,terrain[x][y+1]);
    }
    endShape();
  }
}

// Recursion function doesn't work on web
public void recurse(int x) {
  if(x == cols) {
  } else {
    vertex(x*scl,y*scl,terrain[x][y]);
    vertex(x*scl,(y+1)*scl,terrain[x][y+1]);
    recurse(x+1);
  }
}
  public void settings() {  size(600,600,OPENGL); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "OriginalFractal" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
