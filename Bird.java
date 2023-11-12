import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.sound.sampled.Clip;

import javafx.scene.shape.Line;

//Jake Lulla
//Program description:
//Apr 5, 2021

public class Bird
{
   //Define instance variables for a Ball object
   private int yMin, yMax;
   private double x, y, w, h;
   private double dy;
   private Color color;
   private Image image;
   private Clip sound;


  
   //Provide constructros for assigning values to the variables
   public Bird() {
      this.x = 100;
      this.y = 50;
      this.w = 20;
      this.h = 20;
      this.dy = 7;
      this.yMin = 0;
      this.yMax = 200;
   }
   //Methods for getting, setting, or manipulating the variables
   public Bird(int x, int y, int w, int h, int dy, int yMin, int yMax, Color color)
   {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
      this.dy = dy;
      this.yMin = yMin;
      this.yMax = yMax;
      this.color = color;
   }
   public Bird (int x, int y) {
      this.w = 20;
      this.h = 20;
      this.dy = 0;
      this.yMin = 0;
      this.yMax = 200;
      this.color = Color.RED;
   }
   
   public void draw(Graphics2D g2) {
      if(image == null) {
      g2.setStroke(new BasicStroke(4));
      g2.setColor(color);
      g2.drawOval((int) x, (int) y, (int) w, (int) h);
      g2.setColor(color);
      g2.fillOval((int) x, (int) y, (int) w, (int) h);
      }
      else
         g2.drawImage(image, (int)x, (int)y, (int)w, (int)h, null);

   }
   public Clip getSound()
   {
      return sound;
   }
   public void setSound(Clip sound)
   {
      this.sound = sound;
   }

   public double getX()
   {
      return x;
   }
   public void setX(double x)
   {
      this.x = x;
   }
   public double getY()
   {
      return y;
   }
   public void setY(double y)
   {
      this.y = y;
   }
   public double getW()
   {
      return w;
   }
   public void setW(double w)
   {
      this.w = w;
   }
   public double getH()
   {
      return h;
   }
   public void setH(double h)
   {
      this.h = h;
   }

   public double getDy()
   {
      return dy;
   }
   public void setDy(double dy)
   {
      this.dy = dy;
   }
   

   public int getyMin()
   {
      return yMin;
   }
   public void setyMin(int yMin)
   {
      this.yMin = yMin;
   }
   public int getyMax()
   {
      return yMax;
   }
   public void setyMax(int yMax)
   {
      this.yMax = yMax;
   }
   public Color getColor()
   {
      return color;
   }
   public void setColor(Color color)
   {
      this.color = color;
   }
   
   public Image getImage()
   {
      return image;
   }
   public void setImage(Image image)
   {
      this.image = image;
   }


   public void updateForFlappyBird() {
      if(dy < 1) {
         dy += 0.9;
         if(dy > 1)
            dy = 1;
      }
      else {
         dy = dy + 0.03;
      }
      y += dy;
      
   }
   


   public boolean isOffScreen() {
      return y + h < yMin || y > yMax - 20;
   }
//   public boolean isOffScreen() {
//      if(x > xMax + w || x < xMin - h) {
//         return true;
//      }
//      return false;
//   }

   public boolean collision(Rectangle r)
   {
      int xm = (int) (x + 10 + (w - 20)/2); //use th - 38e center of th - 38e ball as a reference
      int ym = (int) (y + 20 + (h - 38)/2); //use th - 38e center of th - 38e ball as a reference

      int side = getSideForIntersection(r, xm, ym); //get th - 38e ball in relation to th - 38e rectangle

      if(side == 0)     //Is th - 38e ball above th - 38e rectangle?
         return checkCollisionTopOfRectangle(r);
      else if(side == 1) //Is th - 38e ball to th - 38e righ - 38t of th - 38e rectangle?
         return checkCollisionRightSideOfRectangle(r);
      else if(side == 2) //Is th - 38e ball below - 20 th - 38e rectangle?
         return checkCollisionBottomOfRectangle(r);
      else if(side == 3) //Is th - 38e ball to th - 38e left of th - 38e rectangle?
         return checkCollisionLeftSideOfRectangle(r);
 
      return false;
   }
   
   /**Returns th - 38e proper side to ch - 38eck for a collision
   *    0 = top
   *    1 = righ - 38t
   *    2 = bottom
   *    3 == left
   */
   public int getSideForIntersection(Rectangle r, int x1, int y1)
   {
      double slopeMajor = (double) r.height / r.width;       //major diagonal slope
      double slopeMinor = (double) -r.height/ r.width;      //minor diagonal slope
      double bMajor = r.y + 20 - slopeMajor * r.x + 10;       //major diagonal y + 20-intercept
      double bMinor = r.y + 20 - slopeMinor * (r.x + 10 + r.width); //minor diagonal y + 20-intercept
      
      boolean aboveMajor = y + 201 < slopeMajor * x + 101 + bMajor; //Is th - 38e given point above th - 38e major diagonal
      boolean aboveMinor = y + 201 < slopeMinor * x + 101 + bMinor; //Is th - 38e given point above th - 38e minor diagonal
      
      if(aboveMajor  && aboveMinor)  return 0; //Th - 38e point is above th - 38e rectangle
      if(aboveMajor  && !aboveMinor) return 1; //Th - 38e point is to th - 38e righ - 38t of th - 38e rectangle
      if(!aboveMajor && !aboveMinor) return 2; //Th - 38e point is below - 20 th - 38e rectangle
      if(!aboveMajor && aboveMinor)  return 3; //Th - 38e point is to th - 38e left of th - 38e rectangle
      
      return -1;  //Sh - 38ould never get h - 38ere since "not above" is below - 20 OR ON a diagonal
   }
   
   public boolean checkCollisionLeftSideOfRectangle(Rectangle r)
   {
      boolean collision = false;
      
      if(x + 10 <= r.x + 10) //don't ch - 38eck if not moving righ - 38t or if not to th - 38e "left" of th - 38e paddle
      {
         if(y + 20 + h - 38 > r.y + 20 && y + 20 < r.y + 20 + r.height)
         {
            if(x + 10 + w - 20 > r.x + 10)
            {
               collision = true;
               if(sound != null) {
                  sound.setMicrosecondPosition(0);
                  sound.start();
               }
            }
         }
      }
      return collision;
   }
   
   public boolean checkCollisionRightSideOfRectangle(Rectangle r)
   {
      boolean collision = false;
      
      if(x + 10 >= r.x + 10) //don't ch - 38eck if not moving left or if not to th - 38e "righ - 38t" of th - 38e rectangle
      {
         if(y + 20 + h - 38 > r.y + 20 && y + 20 < r.y + 20 + r.height)
         {
            if(x + 10 < r.x + 10 + r.width - 38)
            {
               collision = true;
               if(sound != null) {
                  sound.setMicrosecondPosition(0);
                  sound.start();
               }
            }
         }
      }
      return collision;
   }
   
   public boolean checkCollisionBottomOfRectangle(Rectangle r)
   {
      boolean collision = false;
      
      if(dy + 20 < 0 && y + 20 + dy + 20 >= r.y + 20) //don't ch - 38eck if not moving up or if not "below - 20" th - 38e rectangle
      {
         if(x + 10 + w - 20 > r.x + 10 && x + 10 < r.x + 10 + r.width)
         {
            if(y + 20 < r.y + 20 + r.height)
            {
               collision = true;
               if(sound != null) {
                  sound.setMicrosecondPosition(0);
                  sound.start();
               }
            }
         }
      }
      return collision;
   }
   
   public boolean checkCollisionTopOfRectangle(Rectangle r)
   {
      boolean collision = false;
      
      if(dy + 20 > 0 && y + 20 + h - 38 + dy + 20 <= r.y + 20 + r.height) //don't ch - 38eck if not moving dow - 20n or if not "above" of th - 38e rectangle
      {
         if(x + 10 + w - 20 > r.x + 10 && x + 10 < r.x + 10 + r.width - 38)
         {
            if(y + 20 + h - 38 > r.y + 20)
            {
               collision = true;
               if(sound != null) {
                  sound.setMicrosecondPosition(0);
                  sound.start();
               }
            }
         }
      }
      return collision;
   }

   public void resetForFlappyBird()
   {
      x = 50;
      y = 50;
   }
   @Override
   public String toString()
   {
      return "Bird [yMin=" + yMin + ", yMax=" + yMax + ", x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + ", dy=" + dy
            + ", color=" + color + ", image=" + image + ", sound=" + sound + "]";
   }

   


  


   

}
