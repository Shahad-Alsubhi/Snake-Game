 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel  {

// Set the dimensions of the game window
static Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
static int remainder =(int)(screenSize.getWidth()*0.482)%20;

private static  final int  WINDOW_WIDTH  = (int)(screenSize.getWidth()*0.482)-remainder;
private static  final int WINDOW_HEIGHT = (int)(screenSize.getWidth()*0.482)-remainder;

//Set the size of each cell in the 2D grid
private static final int CELL_SIZE = 20;
//Calculate the number of rows and columns in the grid
private static final int NUM_ROWS = WINDOW_HEIGHT / CELL_SIZE;
private static final int NUM_COLS = WINDOW_WIDTH / CELL_SIZE;
//Set the initial length of the snake and the score for eating food
private static final int INITIAL_SNAKE_LENGTH = 3;
private static final int FOOD_SCORE = 10;
//Set the game speed (in milliseconds)
private static final int GAME_SPEED = 100;
//arrow key code
private static final int LEFT = 37;
private static final int UP = 38;
private static final int RIGHT = 39;
private static final int DOWN = 40;
//Declare variables to store the game state
private static int[][] grid;  // 2D array to represent the game board
private static ArrayList<Point> snake;  // ArrayList to store the coordinates of the snake's segments
private static Point food;// Point to store the coordinates of the food
private static int score;// Integer variable to store the player's score
private static int direction;// Integer variable to store the direction the snake is currently moving
private static boolean gameRunning = true;  // Boolean variable to check if the game is currently running
private static Timer timer;  // Timer object to control the game loop


public static JPanel ContainerGamePanel(){
	
	JPanel ContainerPanel =new JPanel(new BorderLayout());

	JPanel ButtonPanel =new JPanel();
	JButton home=new JButton();
	JButton reStart=new JButton();
	  home.setIcon(new ImageIcon("home.png"));

	    home.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {
	           ContainerPanel.setVisible(false);  
	           FirstGUI.HomePageLabel.setVisible(true); 
	           gameRunning=true;
	        }
	    });

	    
	    reStart.addActionListener(new ActionListener(){
	        @Override
	        public void actionPerformed(ActionEvent e) 
	        {

	        	ButtonPanel.setVisible(false);	         	
	        	resetGame(); // Reset the game state
	            timer.start();
	            ButtonPanel.setVisible(true);

	        }
	   });

	    reStart.setIcon(new ImageIcon("restart.png"));
	    home.setBackground(Color.decode("#697dd3"));
	    reStart.setBackground(Color.decode("#697dd3"));
	   JPanel gamePanel=new SnakeGame();
	 ContainerPanel.add(gamePanel,BorderLayout.SOUTH);
	    ButtonPanel.add(home);
	    ButtonPanel.add(reStart); 
	    ButtonPanel.setBackground(Color.decode("#697dd3"));
	 ContainerPanel.add(ButtonPanel);
	   

	    return ContainerPanel;

}



public SnakeGame() {

	// Set the size of the game window and the background color of the panel
    setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); 
    setBackground(Color.decode("#f3e3fa"));
    
    grid = new int[NUM_ROWS][NUM_COLS];// Initialize the game board,snake,score, direction,
    snake = new ArrayList<>();
    score = 0;
    direction = RIGHT;
  
    initializeSnake(0);
    addFood();
    
    // Add a KeyListener to the panel so the user can control the snake,(receiving keyword event)
    addKeyListener(new KeyListener1());  
    setFocusable(true);
    
    timer = new Timer(GAME_SPEED, new ActionListener1());  // Start the game loop by creating a Timer object and adding an ActionListener to it
    timer.start();

    
    
     
   
    
}
                                                                                                    ///method with primitive type + recursion  
private static void initializeSnake(int i) {  
     
    int x = NUM_COLS / 2;
    int y = NUM_ROWS / 2;
     snake.add(new Point(x-i , y));
        grid[y][x-i] = 1;
        i++;
       
    while (snake.size() <= INITIAL_SNAKE_LENGTH) {
    	
    	initializeSnake(i);
    	
       
    }
}

private static void addFood() {  // Method to add a new food item to the game board
    Random rand = new Random();
    int x, y;
    do {
        x = rand.nextInt(NUM_COLS);
        y = rand.nextInt(NUM_ROWS);
    }
    while (grid[y][x] == 1);{
    food = new Point(x, y);
    grid[y][x] = 2;}
    
    
}

private void moveSnake() { 
    try {
        Point head = snake.get(0);
        int newX = head.x, newY = head.y;
        switch (direction) {
            case LEFT:
                newX--;
                break;
            case UP:
                newY--;
                break;
            case RIGHT:
                newX++;
                break;
            case DOWN:
                newY++;
                break;
        }
        if (newX < 0 || newX >= NUM_COLS || newY < 0 || newY >= NUM_ROWS || grid[newY][newX] == 1) {
            throw new Exception("Game Over");
        }
        else {
            snake.add(0, new Point(newX, newY));
            if (newX == food.x && newY == food.y) 
            {
                score += FOOD_SCORE;
                addFood();
            } 
         else {
        
                Point tail = snake.remove(snake.size() - 1);
                grid[tail.y][tail.x] = 0;
            }
            grid[newY][newX] = 1;
           
        }
    } 
    catch (Exception e) {
        gameRunning = false;
        timer.stop();

    }
}


private static void resetGame() {
    score = 0;
    direction = RIGHT;
    snake.clear();
    grid = new int[NUM_ROWS][NUM_COLS];
    initializeSnake(0);
    addFood();
    gameRunning = true;
	
}

@Override
public void paintComponent(Graphics g) 
{
    super.paintComponent(g);
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("Score: " + score, 10, 30);
    
    for (int row = 0; row < NUM_ROWS; row++) {
        for (int col = 0; col < NUM_COLS; col++) {
            if (grid[row][col] == 1) {  
            	 g.setColor(Color.WHITE);
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
               
            } else if (grid[row][col] == 2) {
                g.setColor(Color.decode("#697dd3"));
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
              
            }
        }
    }
    // Draw the game over message if the game is over
    if (!gameRunning) {  
    	
   g.setColor(Color.decode("#697dd3"));  
       g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", WINDOW_WIDTH / 2 - 150, WINDOW_HEIGHT / 2 - 25);
    }
}
///////////////////////////////////////////////////////////////
public class ActionListener1 implements  ActionListener{
@Override
public void actionPerformed(ActionEvent e) {  // Method to handle the game loop
    moveSnake();
    repaint();
}
}

public class KeyListener1 implements KeyListener{

public void keyPressed(KeyEvent e) { // Method to handle user input from the arrow keys
    int key = e.getKeyCode();
    if (((key == LEFT || key == RIGHT || key == UP || key == DOWN) && Math.abs(direction - key) != 2)) {
        direction = key;
    }
}

@Override
public void keyReleased(KeyEvent e) {  
}

@Override
public void keyTyped(KeyEvent e) {
}
}

}