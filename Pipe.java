import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

//Jake Lulla
//Program description:
//May 21, 2021

public class Pipe
{
   //variables
   private int w, h, x, y, dx, xMin, xMax;
   private Image image;
   private Color color;
   private boolean passedBird, newPipe, score;
   
   
   
   //constructors
   public Pipe() {
      this.w = 50;
      this.h = 50;
      this.x = 50;
      this.y = 50;
      this.dx = 1;
      this.xMin = 0;
      this.xMax = 800;
   }
   public Pipe(int w, int h, int x, int y, int dx, int xMin, int xMax) {
      this.w = w;
      this.h = h;
      this.x = x;
      this.y = y;
      this.dx = dx;
      this.xMin = xMin;
      this.xMax = xMax;
   }
   public Pipe(int w, int h) {
      this.w = w;
      this.h = h;
      this.x = 50;
      this.y = 600;
      this.dx = 3;
      this.xMin = 0;
      this.xMax = 800;
   }
   public Pipe(int x, int y, int w, int h) {
      this.w = w;
      this.h = h;
      this.x = x;
      this.y = y;
      this.dx = 3;
      this.xMin = 0;
      this.xMax = 800;
   }
   
   //getters and setters
   public int getW()
   {
      return w;
   }
   public void setW(int w)
   {
      this.w = w;
   }
   public int getH()
   {
      return h;
   }
   public void setH(int h)
   {
      this.h = h;
   }
   public int getX()
   {
      return x;
   }
   public void setX(int x)
   {
      this.x = x;
   }
   public int getY()
   {
      return y;
   }
   public void setY(int y)
   {
      this.y = y;
   }
   public int getDx()
   {
      return dx;
   }
   public void setDx(int dx)
   {
      this.dx = dx;
   }
   public int getxMin()
   {
      return xMin;
   }
   public void setxMin(int xMin)
   {
      this.xMin = xMin;
   }
   public int getxMax()
   {
      return xMax;
   }
   public void setxMax(int xMax)
   {
      this.xMax = xMax;
   }
   public Image getImage()
   {
      return image;
   }
   public void setImage(Image image)
   {
      this.image = image;
   }
   public boolean isNewPipe()
   {
      return newPipe;
   }
   public void setNewPipe(boolean newPipe)
   {
      this.newPipe = newPipe;
   }
   public Color getColor()
   {
      return color;
   }
   public boolean isPassedBird()
   {
      return passedBird;
   }
   public Color color()
   {
      return color;
   }
   public void setColor(Color color)
   {
      this.color = color;
   }
   
   
   public boolean isPassedBird(Bird bird)
   {
      return x + w > bird.getX() - bird.getW();
   }
   public void setPassedBird(boolean passedBird)
   {
      this.passedBird = passedBird;
   }
   
   public boolean isScore()
   {
      return score;
   }
   public void setScore(boolean score)
   {
      this.score = score;
   }
   //methods
   public void draw(Graphics2D g2) {
      if(image == null) {
         g2.setColor(Color.BLACK);
         g2.drawRect(x, y, w, h);
         g2.setColor(color);
         g2.fillRect(x, y, w, h);
      }
      else
         g2.drawImage(image, x, y, w, h, null);
   }
   
   public Rectangle getBounds() {
      return new Rectangle(x, y, w, h);
   }
   public void update() {
      x -= dx;
   }
   
   public boolean addNewPipe() {
      return x < xMin + 30;
   }
   public boolean isOffScreen() {
      return x < xMin;
   }
   
   public int getSideForIntersection(int x1, int y1)
   {
      double slopeMajor = (double) h / w; //major diagonal slope
      double slopeMinor = (double) -h / w; //minor diagonal slope
      double bMajor = y - slopeMajor * x;  //major diagonal y-intercept
      double bMinor = y - slopeMinor * (x + w); //minor diagonal y-intercept
   
      boolean aboveMajor = y1 < slopeMajor * x1 + bMajor; //Is the given point above the major diagonal
      boolean aboveMinor = y1 < slopeMinor * x1 + bMinor; //Is the given point above the minor diagonal
      
      if(aboveMajor && aboveMinor) return 0;   //The point is above the paddle
      if(aboveMajor && !aboveMinor) return 1;  //The point is to the right of the paddle
      if(!aboveMajor && !aboveMinor) return 2; //The point is below the paddle
      if(!aboveMajor && aboveMinor) return 3;  //The point is to the left of the paddle

      return -1;  //Should never get here since "not above" is below OR ON a diagonal
   }
   
   public void reset()
   {
      x = xMax + w;
      
   }
   
   
   

   
   
   

}
