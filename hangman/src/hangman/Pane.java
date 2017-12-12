package hangman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Pane extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1153943849832065878L;

	public static void main(String[] args) {
        new Pane();
    }
    public static String word = "";
    public static String genre = "";
    public static String[][] answers= {
    	 {"tomato", "apple", "kiwi", "orange", "asparagus", "radish", "carrot", "banana", "peach", "zucchini"},
 	     {"dog", "kangaroo", "elephant", "giraffe", "panda", "crocodile", "hamster", "lemur", "jaguar", "aardvark"},
         {"illinois", "michigan", "mississippi", "montana", "kentucky", "arkansas", "alaska", "indiana", "nebraska", "california"} };
   
    private JLabel commandTitle = new JLabel("Press 'Enter' to continue");
    private JLabel wordtoUse = new JLabel(word);
    private JLabel wGuesses = new JLabel();
    private JTextField textBox = new JTextField(20);
    private BufferedImage hangman = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
    private JPanel hangmanPanel;
    private JPanel inputPanel;
    private String wordToGuess = null;
    private int numWrong= 0;
    private String wrongGuessesS = "Wrong Guesses: ";
    private String Knownword = "";

    public Pane() {
    	pickWord(answers);
        setLayout(new BorderLayout());
        drawHangmanStand(hangman);

 
        inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.gridy = 1;
        inputPanel.add(commandTitle, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        inputPanel.add(textBox, gridBagConstraints);

        add(inputPanel);

        textBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                if (wordToGuess == null) {
                    wordToGuess = word.toLowerCase();
             
                    textBox.setText("");
                    
                commandTitle.setText("Guess a letter");
                    String displayWord = "";
                    for (int i = 0; i < wordToGuess.length(); i++) {
                        Knownword += "_";
                        displayWord += " _ ";
                    }
                    wordtoUse = new JLabel(displayWord);
                    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
                    gridBagConstraints1.gridx = 1;
                    gridBagConstraints1.gridy = 2;
                    gridBagConstraints1.gridwidth = 2;
                    inputPanel.add(wordtoUse, gridBagConstraints1);
                    gridBagConstraints1 = new GridBagConstraints();
                    gridBagConstraints1.gridx = 1;
                    gridBagConstraints1.gridy = 3;
                    gridBagConstraints1.gridwidth = 2;
                    inputPanel.add(wGuesses, gridBagConstraints1);

                    return;
                }

                if (wordToGuess.indexOf(textBox.getText()) >= 0) {
                    guessRight();
                } else {
                    guessWrong();
                    textBox.setText("");
                }


            }
        });

        setSize(1200, 600);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        showChangedHangman();
        revalidate();

    }
    
    private static void pickWord(String [][] a){
   	 int column = (int) (Math.random() * 3)  ;
   		int row = (int)(Math.random()* 9);
   		word = a[column][row];
   		word = word.toLowerCase();
   		if(column==0) {
   			genre = "Fruits/Vegetables";
   		}
   		else if(column==1) {
   			genre = "Animals";
   		} 
   		else {
   			genre = "States";
   		}
   	}

  
    private void guessWrong() {
        numWrong++;
        wrongGuessesS += textBox.getText() + ", ";
        wGuesses.setText(wrongGuessesS);
        Graphics2D g = (Graphics2D) hangman.getGraphics();
        int x = 250, y = 200;
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.ORANGE);

        
        if (numWrong  == 1){
        	 g.drawOval(-20 + x, y, 40, 40);
        }
        else if (numWrong == 2){
        	 g.drawLine(x, y + 40, x, y + 40 + 80);
        }
        else if (numWrong == 3) {
        	 g.drawLine(x, y + 40 + 20, x + 20, y + 40 + 60);
        }
        else if (numWrong == 4) {
        	g.drawLine(x, y + 40 + 20, x - 20, y + 40 + 60);
        }
        else if (numWrong == 5) {
        	g.drawLine(x, y + 40 + 80, x + 20, y + 40 + 80 + 40);
        }
        else if (numWrong == 6) {
        	g.drawLine(x, y + 40 + 80, x - 20, y + 40 + 80 + 40);
        }
        
        else {
        	 JOptionPane.showMessageDialog(this, "You Lose!");
        }
        
        g.dispose();
        showChangedHangman();
        revalidate();
    }

    private void showChangedHangman() {
        if (hangmanPanel != null)
            remove(hangmanPanel);

        hangmanPanel = new JPanel();
        hangmanPanel.add(new JLabel(new ImageIcon(hangman)));
        add(hangmanPanel, BorderLayout.EAST);
        revalidate();

    }

    private static void drawHangmanStand(BufferedImage image) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLUE);


        g.drawLine(10, 475, 250, 475);
        g.drawLine(100, 475, 100, 100);
        g.drawLine(100, 100, 250, 100);
        g.drawLine(250, 100, 250, 200);

        g.dispose();
    }
    
    private void guessRight() {
        String guess = textBox.getText().toLowerCase();
        addGuessToKnownWord(guess);
        String displayString = "";
        for (int i = 0; i < Knownword.length(); i++) {
            displayString += Knownword.substring(i, i + 1) + " ";
        }
        wordtoUse.setText(displayString);

        if (Knownword.indexOf("_") < 0) {
            JOptionPane.showMessageDialog(this, "You Win!");
        }
        textBox.setText("");
    }

    private void addGuessToKnownWord(String guess) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int index = wordToGuess.indexOf(guess);
             index >= 0;
             index = wordToGuess.indexOf(guess, index + 1)) {
            indexes.add(index);
        }
        for (int i = 0; i < indexes.size(); i++) {
            int index = indexes.get(i);
            StringBuilder stringBuilder = new StringBuilder(Knownword);
            stringBuilder.replace(index, index + guess.length(), guess);
            Knownword = stringBuilder.toString();
        }
    }
}