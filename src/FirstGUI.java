import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

public class FirstGUI extends JFrame  {
	
	//get the screen size 
	 Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
	//set the buttons size depends on screenSize 
	private final int ButtonWidth=(int)(screenSize.getWidth()*0.1);
	private final int ButtonHeight=(int)(screenSize.getHeight()*0.07);
	
	//set the window size depends on screenSize 
   	 int remainder =(int)(screenSize.getWidth()*0.49)%20;
   	//make sure the width size ......
   	private  final int windowWidth=(int)(screenSize.getWidth()*0.49)- remainder;
   	private  final int windowHight=(int)(screenSize.getHeight()*0.95);
  
    public static JLabel HomePageLabel;
    JLabel AboutLabel;
    
 
   
  
  FirstGUI() {
   
   //default setting 
   this.setTitle("Snake Game");
   this.setSize(windowWidth,windowHight);
   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
   //Create home Label
   HomePageLabel = HomePageLabel() ;
   this.getContentPane().add(HomePageLabel);
   
  }
  
     private  JLabel HomePageLabel()  {
   
   //create label contains image as background and tow buttons 
   JLabel Label=new JLabel ();
   //set the image as background
   ImageIcon Backgroundimg=new ImageIcon("backgroundimg.jpg");
   //make the image expand according to the size of the label 
   Image scaledImage = Backgroundimg.getImage().getScaledInstance(windowWidth, windowHight, Image.SCALE_SMOOTH);
   Label.setIcon(new ImageIcon(scaledImage)); 
   
   Label.setLayout(new GridBagLayout());
   
    //create playButton,specify its size and location, and finally add it to the label 
   
   JButton playButton = new JButton("");
   //size of the button
   playButton.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
   //set image as background to the button  
  ImageIcon playimg=new ImageIcon("playimg.jpg");
   //make the image expand according to the size of the button
   	Image scaledImage2=playimg.getImage().getScaledInstance(ButtonWidth, ButtonHeight, Image.SCALE_SMOOTH);
   	playButton.setIcon(new ImageIcon(scaledImage2)); 
      
   //constraints for components ,so we can locate the button 
   GridBagConstraints gbc = new GridBagConstraints();
    //locate button
   gbc.insets = new Insets(100, -300, 0, 0);

   //add the button to the label
    Label.add(playButton, gbc);   
   
   
    //create aboutButton,specify its size and location, and finally add it to the label     
   JButton aboutButton = new JButton("");
   //size of the button
   aboutButton.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
   //set image as background to the button
   ImageIcon Aboutimg=new ImageIcon("aboutimag.jpg");
   //make the image expand according to the size of the button
   Image scaledimage3 =Aboutimg.getImage().getScaledInstance(ButtonWidth, ButtonHeight, Image.SCALE_SMOOTH); 	
   aboutButton.setIcon(new ImageIcon(scaledimage3)); 
  
   //constraints for components ,so we can locate the button,set margin
    GridBagConstraints gbc1 = new GridBagConstraints(); 
    gbc1.insets = new Insets(250, -300, 0, 0);
         
   //add the button to the label
    Label.add(aboutButton, gbc1);
      
   //add Action Listener
    playButton.addActionListener(new PlayButtonListener());
    aboutButton.addActionListener(new aboutButtonListener());
         
    return Label;
  }
     
     
     public void AboutLabel() {

    	    String fileName ="AboutInf.txt" ;
        
            String content="";
          try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
          {
             //read from file line by line 
             String line;
             while ((line = br.readLine()) != null) 
             {
                content += line +"\n";
             }
           
             
             AboutLabel=new JLabel();
             AboutLabel.setLayout(new GridLayout());
             JButton back = new JButton("");
             //size of the button
             back.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
             //set image as background to the button  
             ImageIcon Backimg=new ImageIcon("back.jpg");
             //make the image expand according to the size of the button
             Image scaledimage4 = Backimg.getImage().getScaledInstance(ButtonWidth, ButtonHeight, Image.SCALE_SMOOTH);
             back.setIcon(new ImageIcon(scaledimage4));
                
             //add text area contains what was read from the file 
             JTextArea AboutTextArea=new JTextArea(content);
             AboutTextArea.setLayout(new GridBagLayout());
               
             GridBagConstraints gbc = new GridBagConstraints();
                
             gbc.insets = new Insets(300,-50,0, 0);
             //set background color
             AboutTextArea.setBackground(Color.decode("#f3e3fa") );
             //set text color
             AboutTextArea.setForeground(Color.decode("#697dd3"));
             //only read
             AboutTextArea.setEditable(false);
             AboutTextArea.setFont(new Font("Serif", Font.BOLD, 18));
             AboutTextArea.add(back,gbc);
             AboutLabel.add(AboutTextArea);
//             AboutLabel.setVisible(true);
             getContentPane().add(AboutLabel);
       
             //add Action Listener for the back button
             back.addActionListener(new backButtonListener());
                      

          } 
          catch (IOException e) {
        	  
        	  AboutLabel(new ImageIcon("aboutText.jpeg"));
          }

     }
     
                                                                                                //obj as parameter + overloading
     public void AboutLabel(ImageIcon img) {
    	 
    	 AboutLabel=new JLabel();
         AboutLabel.setLayout(new GridBagLayout());
         
         JButton back = new JButton("");
         //size of the button
         back.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
         //set image as background to the button  
         ImageIcon Backimg=new ImageIcon("back.jpg");
         Image scaledimage4 = Backimg.getImage().getScaledInstance(ButtonWidth, ButtonHeight, Image.SCALE_SMOOTH);
         back.setIcon(new ImageIcon(scaledimage4));
          
         //set image as background to the Label 
         ImageIcon abouttextimg = img ;
         Image scaledimage5 = abouttextimg.getImage().getScaledInstance(windowWidth, windowHight, Image.SCALE_SMOOTH);
         AboutLabel.setIcon(new ImageIcon(scaledimage5));

         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(400,-90,0, 0);
       
         AboutLabel.add(back,gbc);  
         getContentPane().add(AboutLabel);
         
          back.addActionListener(new backButtonListener());
        
          
     }
     
     
           public class PlayButtonListener implements ActionListener {


              public void actionPerformed(ActionEvent e) {
               JPanel Game= SnakeGame.ContainerGamePanel();
               getContentPane().add(Game);
               Game.setVisible(true);
               HomePageLabel.setVisible(false);
                
               
               }
            
           }
           
           private class aboutButtonListener implements ActionListener 
           {

               public void actionPerformed(ActionEvent e) 
               {
            	   AboutLabel();
            	   AboutLabel.setVisible(true);
            	   HomePageLabel.setVisible(false);
                   
               }
         
          }
          
           private class backButtonListener implements ActionListener
           {
            public void actionPerformed(ActionEvent e) 
            {
            	HomePageLabel.setVisible(true);
               AboutLabel.setVisible(false);

            }
            
           }
        
           
           
           
           
           
    public static void main(String[] args) throws Exception {
         
         FirstGUI frame =new FirstGUI();
         frame.setVisible(true);
         

        }


      }