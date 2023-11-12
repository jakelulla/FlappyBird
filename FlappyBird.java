import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//Honors Computer Science - Mr. Uhl
//Program description: A Template for Creating a Panel

public class FlappyBird extends JPanel implements KeyListener, MouseListener
{
//Variables for the class
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 800;
   public static final int PREF_H = 600;
   private Timer timer;
   private boolean playing, gameOver;
   private int score;
   
   private ArrayList <Pipe> bottomPipe;
   private ArrayList <Pipe> topPipe;
   private Bird bird;
   
   private Clip fallSound;
   private Clip wingSound;
   private Clip pointSound;
   private Clip dieSound;
   
   //this variable is used to check if image should be switched from jumping bird to normal bird
   private double currentLocation;
   
   private Image background;
   //this variable was added because a point was added the first time the game was started, so I had to fix this problem
   private boolean score1;

   
  
//Class constructor
   public FlappyBird()
   {     
      background = new ImageIcon(this.getClass().getResource("flapBackground.png")).getImage();
      gameOver = false;
      
      
      score1 = true;
      
      //Initializing the array list for both the top and bottom pipes
      

      currentLocation = -3.0;
      topPipe = new ArrayList <Pipe>();
      bottomPipe = new ArrayList <Pipe>();
      
      for(int i = 0; i < 1; i++) {
         Pipe p1 = new Pipe();
         p1.setX(PREF_W/2);
         p1.setY(0);
         p1.setW(40);
         p1.setH(PREF_H/2);
         p1.setColor(new Color(0, 224, 0));
         p1.setDx(1);
         p1.setxMax(PREF_W);
         topPipe.add(p1);
         topPipe.get(i).setPassedBird(false);
         
         Pipe p2 = new Pipe();
         p2.setX(PREF_W/2);
         p2.setY(PREF_H/2 + 170);
         p2.setW(40);
         p2.setH(PREF_H);
         p2.setColor(new Color(0, 224, 0));
         p2.setDx(1);
         p2.setxMax(PREF_W);
         bottomPipe.add(p2);
      }

      
      
     

      //Initializing bird
      bird = new Bird(50, 50, 70, 70, 1, 0, PREF_H, Color.BLUE);
      Image image = new ImageIcon(this.getClass().getResource("bird.png")).getImage();
      bird.setImage(image);
      
      
      
      
      
      playing = false;
      score = 0;
      
      this.setFocusable(true);
      this.setBackground(Color.WHITE);
      this.addKeyListener(this);  
      this.addMouseListener(this);


         
      try {
         //Open an audio input stream
         URL url = this.getClass().getClassLoader().getResource("punch_vtoclN1.wav");
         AudioInputStream audio = AudioSystem.getAudioInputStream(url);
         Clip fall = AudioSystem.getClip(); //initialize a sound clip object
         fall.open(audio); //direct the clip to play the audio defined above
         fallSound = fall;
      } catch (Exception e) {e.printStackTrace();}
      try {
         //Open an audio input stream
         URL url = this.getClass().getClassLoader().getResource("sfx_wing.wav");
         AudioInputStream audio = AudioSystem.getAudioInputStream(url);
         Clip wing = AudioSystem.getClip(); //initialize a sound clip object
         wing.open(audio); //direct the clip to play the audio defined above
         wingSound = wing;
      } catch (Exception e) {e.printStackTrace();}
      try {
         //Open an audio input stream
         URL url = this.getClass().getClassLoader().getResource("sfx_point.wav");
         AudioInputStream audio = AudioSystem.getAudioInputStream(url);
         Clip point = AudioSystem.getClip(); //initialize a sound clip object
         point.open(audio); //direct the clip to play the audio defined above
         pointSound = point;
      } catch (Exception e) {e.printStackTrace();}
      try {
         //Open an audio input stream
         URL url = this.getClass().getClassLoader().getResource("sfx_die.wav");
         AudioInputStream audio = AudioSystem.getAudioInputStream(url);
         Clip die = AudioSystem.getClip(); //initialize a sound clip object
         die.open(audio); //direct the clip to play the audio defined above
         dieSound = die;
      } catch (Exception e) {e.printStackTrace();}
      timer = new Timer(10, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            repaint();
            
            
            if(playing) {
               
               if (bird.getY() - 36 > currentLocation && currentLocation > 0) {
                  Image image = new ImageIcon(this.getClass().getResource("bird.png")).getImage();
                  bird.setImage(image);
               }
               //move pipes
                for(int i = 0; i < topPipe.size(); i++) {
                   topPipe.get(i).update();
                   bottomPipe.get(i).update();
                   repaint();
                }
                bird.updateForFlappyBird();
                
                
                //collision
                for(int i = 0; i < topPipe.size(); i++) {
                   if(bird.collision(topPipe.get(i).getBounds())) {
                      if(playing == true && !gameOver) {
                      fallSound.setMicrosecondPosition(0);
                      fallSound.start();
                      }
                      bird.setDy(10);
                      gameOver = true;
                      Image image1 = new ImageIcon(this.getClass().getResource("fallenBird.png")).getImage();
                      bird.setImage(image1);

                   }
                   if(bird.collision(bottomPipe.get(i).getBounds())) {
                      if(playing == true && !gameOver) {
                      fallSound.setMicrosecondPosition(0);
                      fallSound.start();
                      }
                      bird.setDy(10);
                      gameOver = true;
                      Image image1 = new ImageIcon(this.getClass().getResource("fallenbird.png")).getImage();
                      bird.setImage(image1);
                   }
                   //collision with cap of pipe
                   Rectangle r1 = new Rectangle (topPipe.get(i).getX()-9, topPipe.get(i).getY() + topPipe.get(i).getH(), topPipe.get(i).getW() + 18, 17);
                   if(bird.collision(r1)) {
                      if(playing == true && !gameOver) {
                      fallSound.setMicrosecondPosition(0);
                      fallSound.start();
                      }
                      bird.setDy(10);
                      gameOver = true;
                      Image image1 = new ImageIcon(this.getClass().getResource("fallenbird.png")).getImage();
                      bird.setImage(image1);

                   }
                   Rectangle r2 = new Rectangle (bottomPipe.get(i).getX()-9, bottomPipe.get(i).getY(), bottomPipe.get(i).getW() + 18, 17);
                   if(bird.collision(r2)) {
                      if(playing == true && !gameOver) {
                      fallSound.setMicrosecondPosition(0);
                      fallSound.start();
                      }
                      bird.setDy(10);
                      gameOver = true;
                      Image image1 = new ImageIcon(this.getClass().getResource("fallenbird.png")).getImage();
                      bird.setImage(image1);

                   }
                   
                }
                repaint(); 
                
                //Bird is off screen, game reset
                if(bird.isOffScreen() || bird.getY() > PREF_H - 80) {
                   if(playing == true && !gameOver) {
                   fallSound.setMicrosecondPosition(0);
                   fallSound.start();
                   }
                   boolean playSound = false;
                   while(!playSound) {
                      if(fallSound.getMicrosecondPosition() >= fallSound.getMicrosecondLength()) {
                         dieSound.setMicrosecondPosition(0);
                         dieSound.start();
                         playSound = true;
                      }
                   }
                   if(gameOver = true) {
  
                      playing = false;
                      bird.setDy(0.0);
                      for(int i = 0; i < topPipe.size(); i++) {
                         topPipe.get(i).reset();
                         bottomPipe.get(i).reset();
                      }
                      
                   }
                  }
                
                // New pipe needed?
                for(int i = 0; i < topPipe.size(); i++) {
                   if(topPipe.get(i).getX() - 3 < bird.getX() && topPipe.get(i).addNewPipe() && topPipe.get(i).isNewPipe() == false) {
                      Pipe p1 = new Pipe();
                      p1.setX(PREF_W + 32);
                      p1.setY(0);
                      p1.setW(40);
                      int h = (int)(Math.random() * (PREF_H/2 + 70)) + 30;
                      p1.setH(h);
                      p1.setColor(new Color(0, 224, 0));
                      p1.setDx(1);
                      p1.setxMax(PREF_W);
                      topPipe.add(p1);
                      
                      Pipe p2 = new Pipe();
                      p2.setX(PREF_W  + 32);
                      p2.setY(h + 170);
                      p2.setW(40);
                      p2.setH(PREF_H);
                      p2.setColor(new Color(0, 224, 0));
                      p2.setDx(1);
                      p2.setxMax(PREF_W);
                      bottomPipe.add(p2);
                      
                      topPipe.get(i).setNewPipe(true);
                   }                   
                }

                for(int i = 0; i < topPipe.size(); i++) {
                   if(topPipe.get(i).isOffScreen()) {
                      topPipe.remove(i);
                      bottomPipe.remove(i);
                   }
                }
                for(int i = 0; i < topPipe.size(); i++) {
                   if(topPipe.get(i).isPassedBird(bird) == true && topPipe.get(i).isScore() == false) {
                      if((score1 == true)) {
                         score--;
                         score1 = false;
                      }
                      topPipe.get(i).setScore(true);
                      score += 1;
                      if(score > 0) {
                         pointSound.setMicrosecondPosition(0);
                         pointSound.start();                        
                      }
                   }
                }

              }
              }
                     
         
         
      });
      timer.start();
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
      g2.setFont(new Font("Arial", Font.PLAIN, 40));  
      g2.setColor(Color.BLACK);
      g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
      g2.drawString("Score: " + score, PREF_W * 3/4, 43);
      g2.setColor(Color.BLACK);
      
      for(int i = 0; i < topPipe.size(); i++) {
         topPipe.get(i).draw(g2);
         bottomPipe.get(i).draw(g2);
         g2.setColor(Color.BLACK);
         g2.draw(topPipe.get(i).getBounds());
         g2.draw(bottomPipe.get(i).getBounds());
         g2.setColor(Color.RED);

//         cap of pipe
         g2.setColor(new Color(135, 253, 0));
         g2.fillRect(topPipe.get(i).getX()-9, topPipe.get(i).getY() + topPipe.get(i).getH(), topPipe.get(i).getW() + 18, 17);
         g2.fillRect(bottomPipe.get(i).getX()-9, bottomPipe.get(i).getY(), bottomPipe.get(i).getW() + 18, 17); 
         g2.setColor(Color.BLACK);
         g2.drawRect(bottomPipe.get(i).getX()-9, bottomPipe.get(i).getY(), bottomPipe.get(i).getW() + 18, 17); 
         g2.drawRect(topPipe.get(i).getX()-9, topPipe.get(i).getY() + topPipe.get(i).getH(), topPipe.get(i).getW() + 18, 17);
         g2.setColor(Color.YELLOW);
      }
      g2.setColor(Color.RED);
      String message = "Press <Space> to Start";
      if(playing == true)
         message = "";
      g2.drawString(message, 17, 43);
      bird.draw(g2);
      

   }
   /** ******* METHODS FOR INITIALLY CREATING THE JFRAME AND JPANEL *********/
   public Dimension getPreferredSize()
   {
      return new Dimension(PREF_W, PREF_H);
   }

   public static void createAndShowGUI()
   {
      JFrame frame = new JFrame("Panel Title");
      JPanel gamePanel = new FlappyBird();
      frame.getContentPane().add(gamePanel);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
   public static void main(String[] args)
   {
      SwingUtilities.invokeLater(new Runnable()
      {
         public void run()
         {
            createAndShowGUI();
         }
      });
   }
   @Override
   public void keyTyped(KeyEvent e)
   {
    
   } 
   @Override
   public void keyPressed(KeyEvent e)
   {
      int key = e.getKeyCode();
      //start game
      if(key == KeyEvent.VK_SPACE && !gameOver) {
            playing = true;  
            Image image = new ImageIcon(this.getClass().getResource("bird.png")).getImage();
            bird.setImage(image);
      }  
      //reset game
      if(key == KeyEvent.VK_SPACE && gameOver && playing == false) {
         bird.setDy(1);
         playing = true;
         gameOver = false;
         score = 0;
         bird.resetForFlappyBird();
         Image image = new ImageIcon(this.getClass().getResource("bird.png")).getImage();
         bird.setImage(image);
      }

         

      
   }
   @Override
   public void keyReleased(KeyEvent e)
   {
   }
   @Override
   public void mouseClicked(MouseEvent e)
   {
      // jump with bird
      if(playing && gameOver == false && bird.getY()-97 > 0) {
         bird.setDy(0.05);
         bird.setY(bird.getY() - 97);
         Image image = new ImageIcon(this.getClass().getResource("jumpingFlappyBird.png")).getImage();
         bird.setImage(image); 
         currentLocation = bird.getY();
         wingSound.setMicrosecondPosition(0);
         wingSound.start();
         repaint();
      }
   }
   @Override
   public void mousePressed(MouseEvent e)
   {
   }
   @Override
   public void mouseReleased(MouseEvent e)
   {
   }
   @Override
   public void mouseEntered(MouseEvent e)
   {
   }
   @Override
   public void mouseExited(MouseEvent e)
   {
   }


}