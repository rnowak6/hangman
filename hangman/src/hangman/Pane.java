package hangman;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;



import javax.swing.JPanel;
/** class to display a JFrame object using border layout */


public class Pane extends JFrame implements ActionListener {
	public static void main(String[] args) {
		Pane a = new Pane();
		a.setVisible(true);
	}
 private JPanel centerPanel;
 private JPanel southPanel;
 private JTextField textField;
 private LinePanel line;
 private String [] wordList = {"computer","java","activity","alaska","appearance","article",
   "automobile","basket","birthday","canada","central","character","chicken","chosen",
   "cutting","daily","darkness","diagram","disappear","driving","effort","establish","exact",
   "establishment","fifteen","football","foreign","frequently","frighten","function","gradually",
   "hurried","identity","importance","impossible","invented","italian","journey","lincoln",
   "london","massage","minerals","outer","paint","particles","personal","physical","progress",
   "quarter","recognise","replace","rhythm","situation","slightly","steady","stepped",
   "strike","successful","sudden","terrible","traffic","unusual","volume","yesterday" };

 public ArrayList<String> usedLetters = new ArrayList(); // list of used letter by user
 public ArrayList<String> correctLetters = new ArrayList();
 public String userInput = "";

 private int numLives = 6; // number of lives
 public String theWord; // the wrong which is chosen
 JButton exitButton;
 JButton playAgain;

 // no-argument constructor
 public Pane() { 
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  theWord = pickWord();
  correctLetters = new ArrayList(theWord.length());
  

  setSize(600,500);
  setLocation(100, 100);
  setTitle("Hangman Game");
  setLayout(new BorderLayout());

  centerPanel = new JPanel();
  line = new LinePanel(15,theWord,usedLetters);
  centerPanel.setSize(500,500);
  centerPanel.setBackground(Color.WHITE);
  centerPanel.add(line);
  add(centerPanel, BorderLayout.CENTER);

  textField = new JTextField(20);
  textField.addActionListener(this);

  playAgain = new JButton("Play Again");
  exitButton = new JButton("Exit");
  exitButton.addActionListener(this);
  playAgain.addActionListener(this);

  southPanel = new JPanel();
  southPanel.setBackground(Color.BLACK);
  southPanel.setLayout(new  GridLayout(0,3));
  southPanel.add(playAgain);
  southPanel.add(textField);
  southPanel.add(exitButton);
  add(southPanel, BorderLayout.SOUTH);

 }

 // Picks a word, latter it will be picked randomly.
 private String pickWord(){
  return wordList[0];
 }

 // This method check wither the input is valid
 // i.e. its in the alphabet.
 private boolean checkInput(String s){
  String [] alphabet = {"a","b","c","d","e","f",
    "g","h","i","j","k","l","m","n","o","p",
    "q","r","s","t","u","v","w","x","y","z"};

  for (int i = 0; i < alphabet.length; i++){
   if (s.equals(alphabet[i]) && s.length() <= 1){
    return true;
   }
  }
  return false;
 }

 /**
  * 
  */
 public void Play(){


  if (numLives > 0){

   if (userInput.length() == 1 && usedLetters.contains(userInput) == false &&
     checkInput(userInput) == true){
    usedLetters.add(userInput);

    if (theWord.contains(userInput) == true){
     correctLetters.add(userInput);
     centerPanel.removeAll();

     line = new LinePanel(20,theWord,correctLetters);
     centerPanel.add(line);
     centerPanel.revalidate();
    }

    else{
     numLives = numLives - 1;

     centerPanel.removeAll();
     line = new LinePanel(numLives,theWord,correctLetters);

     centerPanel.add(line);
     centerPanel.revalidate();
    }
   }

   else if (userInput.length() > 1)
    System.out.println("Enter a valid letter");

   else if (userInput.length() == 1 && checkInput(userInput) == true && theWord.contains(userInput)){
    correctLetters.add(userInput);
   }

   centerPanel.removeAll();

   line = new LinePanel(20,theWord,usedLetters);
   centerPanel.add(line);
   centerPanel.revalidate();
  }  
}
 // return true if the word and the correctly used letters list match
 public boolean checkWord(String s, ArrayList<String> t){
  String temp = "";

  for (int i = 0; i < s.length(); i++){
   if ( t.contains(s.substring(i, i+1)) == true){
    temp += s.substring(i, i+1);
   }
  }

  if (s.equals(temp)){
   return true;
  }

  return false;
 }

 public void actionPerformed(ActionEvent evt) {
  String temp = textField.getText();
  if (temp.length() == 1){
   userInput = temp;
  }
  textField.selectAll();
  if (checkWord(theWord, correctLetters) != true){
   Play();
  }

  if (evt.getSource() == exitButton){
   System.exit(0);
  }

//  if (evt.getSource() == playAgain){
//   removeAll();
//   repaint();
//   
//  }

 }



 class LinePanel extends JPanel {

 int x = 5;
 String theWord = "";
 ArrayList<String> letterList;

 public LinePanel(int num, String t, ArrayList<String> s) {
  super(); // 300,350
  setPreferredSize(new Dimension(400,400));
  setBackground(Color.RED);
  this.x = num;
  this.theWord = t;
  letterList = cloneList(s);
 }

 private ArrayList<String> cloneList(ArrayList<String> aList) {
  ArrayList<String> clonedList = new ArrayList<String>(aList.size());
  for (String letter : aList) 
   clonedList.add(letter);
  return clonedList;
 }



 public int getX() {
  return x;
 }

 public void setX(int x) {
  this.x = x;
 }

 public void paintComponent(Graphics g) {
  super.paintComponent(g);
  Graphics2D g2 = (Graphics2D) g;
  if (x == 15 || x != 15){
   Line2D line = new Line2D.Double(0, 250, 80, 250); // Creates base
   Line2D line2 = new Line2D.Double(40, 50, 40, 250); // Creates vertical line
   Line2D line3 = new Line2D.Double(40, 50, 150, 50); // Creates horizontal line
   Line2D line4 = new Line2D.Double(150, 50, 150, 80); // Creates small line to hang the man
   g2.setStroke(new BasicStroke(5.0f)); // Line thickness
   g2.setColor(Color.BLACK); // Line colour

   //draw shape of line
   g2.draw(line);
   g2.draw(line2);
   g2.draw(line3);
   g2.draw(line4);

   int x1 = 0; int y = 320;
   for (int i = 0; i < theWord.length();i++){
    g2.drawLine(x1, y, x1 + 20, y);
    x1 += 50;
   }
  }

  // head
  if (x == 5 || x < 5){
   g2.setStroke(new BasicStroke(5.0f));
   g2.drawOval(135, 85, 35, 35);
  }

  // body
  if (x == 4 || x < 4){
   g2.drawLine(150, 120, 150, 190);
  }

  // left arm
  if (x == 3 || x < 3){
   g2.drawLine(150, 140, 125, 155); 
  }

  // right arm
  if (x == 2 || x < 2){
   g2.drawLine(150, 140, 175, 155); 
  }

  // left leg and foot
  if (x == 1 || x < 1){
   g2.drawLine(150, 190, 125, 200); // leg
   g2.drawLine(125, 200, 120, 190); // foot
  }

  // right leg and foot
  if (x == 0){
   g2.drawLine(150, 190, 175, 200); // leg
   g2.drawLine(175, 200, 180, 190); // foot
  }

  // Show whole word on screen
  if (x == 20){

   int x1 = 3; int y = 317;

   String temp = "";

   for (int i = 0; i < theWord.length(); i++){
    if ( letterList.contains(theWord.substring(i, i+1)) == true){
     temp += theWord.substring(i, i+1);
    }
    else{
     temp += " ";
    }
   }

   for (int i = 0; i < temp.length() ;i++){
    g2.setColor(Color.BLACK);
    Font font = new Font("Arial", Font.PLAIN, 25);
    g2.setFont(font);
    g2.drawString(temp.substring(i, i+1), x1, y);
    x1 += 50;
   }


   x1 = 3;  y = 370;
   for (int i = 0; i < letterList.size() ;i++){

    if (theWord.contains(letterList.get(i)) == false){
     g2.drawString(letterList.get(i), x1, y );
     x1 += 50;
    }

   }
  }
 }
}



class PlayHangman {

 /**
  * @param args
  */
}
}