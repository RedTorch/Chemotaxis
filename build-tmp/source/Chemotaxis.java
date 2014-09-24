import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Chemotaxis extends PApplet {

Bacteria [] colony;
int playX = 300;
int playY = 300;
int playHP = 100;
int wepRange = 100;
int ammo = 200;
int bactRange = 130;
int playKill = 0;
int bacNum = 400;
boolean released = true;
boolean gameover = false;
 public void setup()   
 {     
   frameRate(30);
   size(600,600);
   colony = new Bacteria[bacNum];
    for(int i=0; i<colony.length; i++)
    {
      colony[i] = new Bacteria((int)(Math.random()*600),(int)(Math.random()*600));
    }
    noCursor();
 }   
 public void draw()   
 { 
   background(0);
   playMove();
   stroke(255);
   rect(playX-5, playY-5, 10,10);
   for(int i = 0; i < colony.length; i++)
   {
     if(colony[i].dead == false)
     {
       colony[i].show();
       colony[i].move();
       if(colony[i].myX == playX && colony[i].myY == playY)
       {
         playHP--;
         if(playHP <=0)
        {
        }
          gameover = true;
       }
     }
   }
   if(mousePressed && (mouseButton == LEFT))
   {
     firing();
   }
   fill(255);
   text("HP <" + playHP + ">",10,10);
   text("SWARMERS LEFT <" + (bacNum-playKill) + ">",10,30);
   if(gameover == true)
   {
    background(0);
    fill(255);
    if(playKill == bacNum)
    {
      text("Congratulations, the swarm has been exterminated.",230,300);
    }
    else
    {
      text("Game Over, Noob",250,300);
    }
    text("Click to try again.",250,320);
    if(mousePressed && (mouseButton == LEFT))
    {
      gameover = false;
      playX = 300;
      playY = 300;
      playHP = 10;
      playKill = 0;
      for(int i=0; i<colony.length; i++)
      {
        colony[i] = new Bacteria((int)(Math.random()*600),(int)(Math.random()*600));
      }
    }
   }
   else if(playKill == bacNum)
   {
    gameover = true;
   }
   noFill();
   stroke(255,255,255,150);
   ellipse(mouseX,mouseY,20,20);
   point(mouseX,mouseY);
 }  
 class Bacteria
 {     
   int myX, myY, clr;
   int headX = 0;
   int headY = 0;
   boolean dead;
   Bacteria(int x,int y)
   {
     myX = x;
     myY = y;
     clr = 250;
   }
   public void move()
   {
     myX = myX + (int)(Math.random()*5-2.5f);
     myY = myY + (int)(Math.random()*5-2.5f);
     if(dist(myX, myY, playX, playY)<bactRange)
     {
       if(myX < playX)
         myX++;
       else if(myX > playX)
         myX--;
       if(myY < playY)
         myY++;
       else if(myY > playY)
         myY--;
     }
     else
     {
       myX--;
       myY++;
       if(myX<0)
       {
        myX = 600;
       }
       if(myY>600)
       {
        myY = 0;
       }
     }
   }
   public void show()
   {
     noFill();
     stroke(0,150,clr);
        rect(myX-5,myY-5,10,10);
   }
 }
 public void playMove()
 {
   if(playX < mouseX)
     playX = playX + 2;
   else if(playX > mouseX)
     playX = playX - 2;
   if(playY < mouseY)
     playY = playY + 2;
   else if(playY > mouseY)
     playY = playY - 2;
 }    
 public void firing()
 {
     int minDist = 600;
     int minBac = 0;
     for(int i = 0; i < colony.length; i++)
     {
       if(dist(colony[i].myX, colony[i].myY, playX, playY)<minDist && colony[i].dead == false)
       {
         minDist = (int)(dist(colony[i].myX, colony[i].myY, playX, playY));
         minBac = i;
       }
     }
     if(dist(colony[minBac].myX, colony[minBac].myY, playX, playY)<wepRange && released == true)
     {
       colony[minBac].dead = true;
       playKill++;
       stroke(255);
       ellipse(colony[minBac].myX, colony[minBac].myY,8,8);
       line(colony[minBac].myX, colony[minBac].myY, playX, playY);
       released = false;
     }
 }
 public void mouseReleased()
 {
   released = true;
 }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Chemotaxis" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
